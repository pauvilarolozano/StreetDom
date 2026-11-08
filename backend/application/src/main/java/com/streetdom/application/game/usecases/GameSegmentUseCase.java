package com.streetdom.application.game.usecases;

import com.streetdom.application.game.query.GetGameSegmentsQuery;
import com.streetdom.application.game.result.GameSegmentsResult;

public interface GameSegmentUseCase {
    GameSegmentsResult getGameSegments(GetGameSegmentsQuery query);


}
