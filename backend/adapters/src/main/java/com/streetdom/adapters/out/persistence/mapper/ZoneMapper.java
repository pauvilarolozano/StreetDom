package com.streetdom.adapters.out.persistence.mapper;

import com.streetdom.adapters.out.persistence.entity.ZoneEntity;
import com.streetdom.domain.model.Zone;
import org.springframework.stereotype.Component;

@Component
public class ZoneMapper {

    public Zone toDomain(ZoneEntity entity) {
        return Zone.builder()
                .id(entity.getId())
                .h3Index(entity.getH3Index())
                .build();
    }

    public ZoneEntity toEntity(Zone zone) {
        return ZoneEntity.builder()
                .id(zone.getId())
                .h3Index(zone.getH3Index())
                .build();
    }

}
