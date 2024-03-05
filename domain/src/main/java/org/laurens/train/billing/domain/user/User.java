package org.laurens.train.billing.domain.user;

import org.laurens.train.billing.domain.journey.Journey;

import java.util.Collections;
import java.util.List;

sealed public interface User permits UserNotInTransit, UserInTransit{
    List<Journey> NO_JOURNEY = Collections.emptyList();
    UserId userId();
    List<Journey> journeys();
}



