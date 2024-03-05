package org.laurens.train.billing.domain.event;

import org.laurens.train.billing.domain.journey.Tap;
import org.laurens.train.billing.domain.user.User;
import org.laurens.train.billing.domain.user.UserInTransit;
import org.laurens.train.billing.domain.user.UserNotInTransit;
import org.laurens.train.billing.domain.user.UserRepository;

public record JourneyStarted(UserNotInTransit user, Tap startingTap) {
    public User apply(UserRepository userRepository) {
        UserInTransit userInTransit =  new UserInTransit(user.userId(), user.journeys(), startingTap);
        userRepository.updateUser(userInTransit.userId(), userInTransit);
        return userInTransit;
    }
}
