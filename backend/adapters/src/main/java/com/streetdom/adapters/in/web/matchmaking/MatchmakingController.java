package com.streetdom.adapters.in.web.matchmaking;

import com.streetdom.adapters.in.web.security.SecurityUser;
import com.streetdom.application.matchmaking.command.JoinMatchmakingCommand;
import com.streetdom.application.matchmaking.MatchmakingUseCase;
import com.streetdom.application.matchmaking.result.JoinMatchmakingResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/matchmaking")
@RequiredArgsConstructor
public class MatchmakingController {

    private final MatchmakingUseCase matchmakingUseCase;
    private final MatchmakingWebMapper mapper;

    @PostMapping
    public ResponseEntity<JoinMatchmakingResponse> joinMatchmaking(
            @Valid @RequestBody JoinMatchmakingRequest request,
            @AuthenticationPrincipal SecurityUser securityUser
    ) {
        JoinMatchmakingCommand command = mapper.toCommand(request,securityUser.getId());
        JoinMatchmakingResult result = matchmakingUseCase.joinMatchmaking(command);
        JoinMatchmakingResponse response = mapper.toResponse(result);

        return ResponseEntity.ok(response);
    }
}