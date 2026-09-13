package com.streetdom.adapters.in.web.controller;

import com.streetdom.adapters.in.web.mapper.MapMatchingWebMapper;
import com.streetdom.adapters.in.web.request.MapMatchingRequest;
import com.streetdom.adapters.in.web.response.MapMatchingResponse;
import com.streetdom.application.port.in.MapMatchingUseCase;
import com.streetdom.application.port.in.result.MapMatchingResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/map-matching")
@RequiredArgsConstructor
public class MapMatchingController {

    private final MapMatchingUseCase mapMatchingUseCase;
    private final MapMatchingWebMapper mapMatchingWebMapper;

    @PostMapping("/match")
    public ResponseEntity<MapMatchingResponse> match(@RequestBody MapMatchingRequest request) {

        MapMatchingResult result = mapMatchingUseCase.match(mapMatchingWebMapper.toCommand(request));
        MapMatchingResponse response = mapMatchingWebMapper.toResponse(result);

        return ResponseEntity.ok(response);
    }
}
