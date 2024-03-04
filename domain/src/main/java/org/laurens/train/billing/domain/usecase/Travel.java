package org.laurens.train.billing.domain.usecase;

import org.laurens.train.billing.domain.event.JourneyCompleted;
import org.laurens.train.billing.domain.event.JourneyStarted;
import org.laurens.train.billing.domain.journey.Tap;
import org.laurens.train.billing.domain.user.User;
import org.laurens.train.billing.domain.user.UserInTransit;
import org.laurens.train.billing.domain.user.UserNotInTransit;

public class Travel {
    public User tapCard(User user, Tap tap){
        switch (user) {
            case UserNotInTransit userNotInTransit -> {
                return new JourneyStarted(userNotInTransit, tap).apply();
            }
            case UserInTransit userInTransit -> {
                return new JourneyCompleted(userInTransit, tap).apply();
            }
        }
    }
}
