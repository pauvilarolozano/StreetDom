package com.streetdom.adapters.out.persistence.mapper;

import com.streetdom.adapters.out.persistence.entity.ZoneEntity;
import com.streetdom.domain.model.Zone;
import org.springframework.stereotype.Component;

@Component
public class ZoneMapper {

    public Zone toDomain(ZoneEntity entity) {
        return Zone.builder()
                .cellIndex(entity.getH3Index())
                .build();
    }

    public ZoneEntity toEntity(Zone zone) {
        return ZoneEntity.builder()
                .h3Index(zone.getCellIndex())
                .build();
    }

}
