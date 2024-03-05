package org.laurens.train.billing.domain.usecase;

import org.laurens.train.billing.domain.event.JourneyCompleted;
import org.laurens.train.billing.domain.event.JourneyStarted;
import org.laurens.train.billing.domain.journey.Tap;
import org.laurens.train.billing.domain.user.User;
import org.laurens.train.billing.domain.user.UserInTransit;
import org.laurens.train.billing.domain.user.UserNotInTransit;
import org.laurens.train.billing.domain.user.UserRepository;

public class TravelUseCase {

    private final UserRepository userRepository;

    public TravelUseCase(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User tapCard(Tap tap){
        User user = userRepository.findOrCreateUser(tap.userId());
        switch (user) {
            case UserNotInTransit userNotInTransit -> {
                return new JourneyStarted(userNotInTransit, tap).apply(userRepository);
            }
            case UserInTransit userInTransit -> {
                return new JourneyCompleted(userInTransit, tap).apply(userRepository);
            }
        }
    }
}
