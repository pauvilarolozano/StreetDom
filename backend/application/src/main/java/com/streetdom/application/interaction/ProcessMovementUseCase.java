package com.streetdom.application.interaction;

import com.streetdom.domain.model.streetnetwork.StreetSegment;

import java.util.List;

public interface ProcessMovementUseCase {
    List<StreetSegment> process(ProcessMovementCommand command);
}
