package com.streetdom.adapters.out.redis.matchmaking;

import com.streetdom.application.port.out.matchmaking.MatchmakingRepository;
import com.streetdom.domain.model.geography.GeographicCell;
import com.streetdom.domain.model.matchmaking.MatchmakingEntry;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBatch;
import org.redisson.api.RBucket;
import org.redisson.api.RFuture;
import org.redisson.api.RSet;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RedisMatchmakingRepositoryAdapter implements MatchmakingRepository {

    private static final String ENTRY_KEY_PREFIX = "matchmaking:entry:";
    private static final String CELL_KEY_PREFIX = "matchmaking:cell:";
    private static final String USER_LOCK_PREFIX = "matchmaking:lock:user:";
    private static final Duration MATCHMAKING_TTL = Duration.ofMinutes(2);

    private final RedissonClient redissonClient;
    private final RedisMatchmakingMapper mapper;

    //TODO heartbeat para actualizar ttl mientras user busca?
    //TODO @Scheduled cada pocos minutos pata limpieza de users dentro de cells

    @Override
    public Optional<MatchmakingEntry> findByUserId(UUID userId) {
        String entryKey = ENTRY_KEY_PREFIX + userId;
        RBucket<RedisMatchmakingEntry> bucket = redissonClient.getBucket(entryKey);
        RedisMatchmakingEntry redisEntry = bucket.get();

        return Optional.ofNullable(redisEntry)
                .map(mapper::toDomain);
    }

    @Override
    public List<MatchmakingEntry> findByCells(Set<GeographicCell> cells) {

        if (cells.isEmpty()) {
            return List.of();
        }

        Set<UUID> userIds = findUserIdsByCells(cells);

        if (userIds.isEmpty()) {
            return List.of();
        }

        return findEntriesByUserIds(userIds);
    }

    @Override
    public void save(MatchmakingEntry entry) {

        RedisMatchmakingEntry redisEntry = mapper.toRedis(entry);

        RLock lock = redissonClient.getLock(USER_LOCK_PREFIX + redisEntry.userId());
        lock.lock();

        try {

            findByUserId(redisEntry.userId())
                    .filter(existing -> !existing.cell().value().equals(redisEntry.cellValue()))
                    .ifPresent(existing -> removeFromCell(existing.userId(),existing.cell().value()));

            String entryKey = ENTRY_KEY_PREFIX + redisEntry.userId();
            RBucket<RedisMatchmakingEntry> entryBucket = redissonClient.getBucket(entryKey);
            entryBucket.set(redisEntry, MATCHMAKING_TTL);

            String cellKey = CELL_KEY_PREFIX + redisEntry.cellValue();
            RSet<UUID> usersInCell = redissonClient.getSet(cellKey);
            usersInCell.add(redisEntry.userId());

        } finally {
            lock.unlock();
        }
    }

    @Override
    public void delete(UUID userId) {

        RLock lock = redissonClient.getLock(USER_LOCK_PREFIX + userId);
        lock.lock();

        try {
            findByUserId(userId)
                    .ifPresent(existing -> removeFromCell(existing.userId(), existing.cell().value()));

            String entryKey = ENTRY_KEY_PREFIX + userId;
            RBucket<RedisMatchmakingEntry> bucket = redissonClient.getBucket(entryKey);
            bucket.delete();

        } finally {
            lock.unlock();
        }
    }


    private Set<UUID> findUserIdsByCells(Set<GeographicCell> cells) {
        RBatch batch = redissonClient.createBatch();

        List<RFuture<Set<UUID>>> futures = cells.stream()
                .map(cell -> batch
                        .<UUID>getSet(CELL_KEY_PREFIX + cell.value())
                        .readAllAsync())
                .toList();

        batch.execute();

        return futures.stream()
                .map(future -> future.toCompletableFuture().join())
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }


    private List<MatchmakingEntry> findEntriesByUserIds(Set<UUID> userIds) {
        RBatch batch = redissonClient.createBatch();

        List<RFuture<RedisMatchmakingEntry>> futures = userIds.stream()
                .map(userId -> batch
                        .<RedisMatchmakingEntry>getBucket(ENTRY_KEY_PREFIX + userId)
                        .getAsync())
                .toList();

        batch.execute();

        return futures.stream()
                .map(future -> future.toCompletableFuture().join())
                .filter(Objects::nonNull)
                .map(mapper::toDomain)
                .toList();
    }

    private void removeFromCell(UUID userId, String cellValue) {
        String cellKey = CELL_KEY_PREFIX + cellValue;

        RSet<UUID> usersInCell = redissonClient.getSet(cellKey);
        usersInCell.remove(userId);
    }
}
