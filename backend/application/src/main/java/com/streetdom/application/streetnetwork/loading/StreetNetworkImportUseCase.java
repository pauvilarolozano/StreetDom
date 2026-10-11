package com.streetdom.application.streetnetwork.loading;

import com.streetdom.domain.model.geography.GeographicBounds;

public interface StreetNetworkImportUseCase {
    void importArea(GeographicBounds bounds);
}
