package com.streetdom.adapters.out.valhalla;

import com.streetdom.adapters.out.valhalla.dto.ValhallaLocationRequest;
import com.streetdom.adapters.out.valhalla.dto.ValhallaTraceRequest;
import com.streetdom.adapters.out.valhalla.dto.ValhallaTraceResponse;
import com.streetdom.adapters.out.valhalla.mapper.ValhallaMapMatchingMapper;
import com.streetdom.application.port.out.MapMatchingProvider;
import com.streetdom.application.port.out.result.ProviderMapMatchingResult;
import com.streetdom.domain.model.Location;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class ValhallaMapMatchingProvider implements MapMatchingProvider {

    private final RestClient valhallaRestClient;
    private final ValhallaMapMatchingMapper mapper;

    public ValhallaMapMatchingProvider(
            @Qualifier("valhallaRestClient") RestClient valhallaRestClient,
            ValhallaMapMatchingMapper mapper
    ) {
        this.valhallaRestClient = valhallaRestClient;
        this.mapper = mapper;
    }

    @Override
    public ProviderMapMatchingResult match(List<Location> locations) {

        ValhallaTraceRequest request =
                new ValhallaTraceRequest(
                        locations.stream()
                                .map(location ->
                                        new ValhallaLocationRequest(
                                                location.latitude(),
                                                location.longitude()
                                        )
                                )
                                .toList(),
                        "pedestrian",
                        "map_snap"
                );

        ValhallaTraceResponse response =
                valhallaRestClient.post()
                        .uri("/trace_attributes")
                        .body(request)
                        .retrieve()
                        .body(ValhallaTraceResponse.class);

        if (response == null) {
            throw new IllegalStateException(
                    "Valhalla returned an empty response"
            );
        }

        return mapper.toResult(response);
    }
}