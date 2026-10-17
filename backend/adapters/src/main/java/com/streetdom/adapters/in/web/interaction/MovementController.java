package com.streetdom.adapters.in.web.interaction;

import com.streetdom.adapters.in.web.security.SecurityUser;
import com.streetdom.application.interaction.ProcessMovementCommand;
import com.streetdom.application.interaction.ProcessMovementUseCase;
import com.streetdom.application.interaction.result.InteractionResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MovementController {

    private final ProcessMovementUseCase processMovementUseCase;
    private final ProcessMovementWebMapper processMovementWebMapper;

    @PostMapping("/api/games/{gameId}/movements")
    public ResponseEntity<MovementUpdateResponse> processMovement(
            @PathVariable UUID gameId,
            @Valid @RequestBody MovementUpdateRequest request,
            @AuthenticationPrincipal SecurityUser user) {

        ProcessMovementCommand command = processMovementWebMapper.toCommand(request, user.getId(), gameId);
        InteractionResult result = processMovementUseCase.process(command);
        MovementUpdateResponse response = processMovementWebMapper.toResponse(result);

        return ResponseEntity.ok(response);
    }
}
