package com.streetdom.adapters.in.web.interaction;

import com.streetdom.adapters.in.web.security.SecurityUser;
import com.streetdom.application.interaction.ProcessMovementCommand;
import com.streetdom.application.interaction.ProcessMovementUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/userLocation")
@RequiredArgsConstructor
public class LocationController {

    private final ProcessMovementUseCase processMovementUseCase;
    private final ProcessMovementWebMapper processMovementWebMapper;

    @PostMapping("/updates")
    public ResponseEntity<String> processMovement(
            @Valid @RequestBody MovementUpdateRequest request,
            Authentication authentication) {

        SecurityUser user = (SecurityUser) authentication.getPrincipal();

        ProcessMovementCommand command = processMovementWebMapper.toCommand(request,user.getId());

        processMovementUseCase.process(command);

        return ResponseEntity.ok("TODO");
    }
}
