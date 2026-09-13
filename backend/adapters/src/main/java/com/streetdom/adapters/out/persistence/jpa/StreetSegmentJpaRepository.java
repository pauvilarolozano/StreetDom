package com.streetdom.adapters.out.persistence.jpa;

import com.streetdom.adapters.out.persistence.entity.StreetSegmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

public interface StreetSegmentJpaRepository extends JpaRepository<StreetSegmentEntity, UUID> {

    @Query(value = """
        SELECT ss.*
        FROM street_segment ss
        WHERE ST_DWithin(
            ss.geometry::geography,
            ST_GeomFromText(:geometryWkt, 4326)::geography,
            :toleranceMeters
        )
        AND (
            ST_Length(
                ST_Intersection(
                    ss.geometry,
                    ST_Buffer(
                        ST_GeomFromText(:geometryWkt, 4326)::geography,
                        :toleranceMeters
                    )::geometry
                )
            )
            /
            NULLIF(ST_Length(ss.geometry), 0)
        ) >= :minimumOverlap
        """, nativeQuery = true)
    List<StreetSegmentEntity> findWithinDistanceOfGeometry(
            @Param("geometryWkt") String geometryWkt,
            @Param("toleranceMeters") double toleranceMeters,
            @Param("minimumOverlap") double minimumOverlap
    );
}
