package com.streetdom.domain.model;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class Zone {

    UUID id;
    String h3Index;
}
