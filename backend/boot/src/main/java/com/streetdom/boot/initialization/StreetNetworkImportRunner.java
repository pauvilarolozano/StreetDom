package com.streetdom.boot.initialization;

import com.streetdom.application.streetnetwork.loading.StreetNetworkImportUseCase;
import com.streetdom.domain.model.geography.GeographicBounds;
import com.streetdom.domain.model.geography.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(
        name = "streetdom.map-import.enabled",
        havingValue = "true"
)
public class StreetNetworkImportRunner implements ApplicationRunner {

    private final StreetNetworkImportUseCase streetNetworkImportUseCase;

    @Override
    public void run(ApplicationArguments args) {

        GeographicBounds barcelonaBounds =
                new GeographicBounds(
                        new Location(41.3161, 2.0517),
                        new Location(41.4686, 2.2291)
                );

        long start = System.currentTimeMillis();
        streetNetworkImportUseCase.importArea(barcelonaBounds);
        long duration = System.currentTimeMillis() - start;

        System.out.println(
                "Barcelona map import finished in "
                        + duration / 1000.0
                        + " seconds"
        );
    }
}
