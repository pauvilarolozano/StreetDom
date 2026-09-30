package com.streetdom.adapters.in.web.controller;

import com.streetdom.adapters.in.web.mapper.ProcessMovementWebMapper;
import com.streetdom.adapters.in.web.request.MovementUpdateRequest;
import com.streetdom.adapters.in.web.security.SecurityUser;
import com.streetdom.application.command.ProcessMovementCommand;
import com.streetdom.application.port.in.ProcessMovementUseCase;
import com.streetdom.application.service.MapMatchingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/location")
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
