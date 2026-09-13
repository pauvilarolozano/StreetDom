package com.streetdom.adapters.out.persistence.jpa;

import com.streetdom.adapters.out.persistence.entity.RoutingEdgeEntity;
import com.streetdom.domain.model.RoutingProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface RoutingEdgeJpaRepository extends JpaRepository<RoutingEdgeEntity, UUID> {

    Optional<RoutingEdgeEntity> findByProviderAndExternalId(
            RoutingProvider provider,
            String externalId
    );

    @Query("""
    select r.externalId
    from RoutingEdgeEntity r
    where r.provider = :provider
      and r.externalId in :externalIds
""")
    Set<String> findExistingExternalIds(
            @Param("provider") RoutingProvider provider,
            @Param("externalIds") Collection<String> externalIds
    );
}
