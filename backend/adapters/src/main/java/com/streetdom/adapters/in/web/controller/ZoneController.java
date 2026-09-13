package com.streetdom.adapters.in.web.controller;

import com.streetdom.application.port.in.ZoneUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/zones")
@RequiredArgsConstructor
public class ZoneController {

    private ZoneUseCase zoneUseCase;


}
