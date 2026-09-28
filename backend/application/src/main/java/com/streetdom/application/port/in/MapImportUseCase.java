package com.streetdom.application.port.in;

import com.streetdom.domain.model.geography.GeographicBounds;

public interface MapImportUseCase {
    void importArea(GeographicBounds bounds);
}
