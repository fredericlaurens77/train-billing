package org.laurens.train.billing.domain.event;

import org.laurens.train.billing.domain.journey.Tap;
import org.laurens.train.billing.domain.user.User;
import org.laurens.train.billing.domain.user.UserInTransit;
import org.laurens.train.billing.domain.user.UserNotInTransit;

public record JourneyStarted(UserNotInTransit user, Tap startingTap) {
    public User apply() {
        return new UserInTransit(user.userId(), user.journeys(), startingTap);
    }
}
