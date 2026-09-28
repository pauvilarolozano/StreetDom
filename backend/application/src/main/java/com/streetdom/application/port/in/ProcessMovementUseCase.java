package com.streetdom.application.port.in;

import com.streetdom.application.command.ProcessMovementCommand;
import com.streetdom.domain.model.geography.StreetSegment;

import java.util.List;

public interface ProcessMovementUseCase {
    List<StreetSegment> process(ProcessMovementCommand command);
}
