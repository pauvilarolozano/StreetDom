package com.streetdom.application.interaction;

import com.streetdom.application.interaction.result.InteractionResult;

public interface ProcessMovementUseCase {
    InteractionResult process(ProcessMovementCommand command);
}
