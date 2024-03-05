package org.laurens.train.billing.domain.event;

import org.laurens.train.billing.domain.journey.Journey;
import org.laurens.train.billing.domain.journey.Tap;
import org.laurens.train.billing.domain.user.UserInTransit;
import org.laurens.train.billing.domain.user.UserNotInTransit;
import org.laurens.train.billing.domain.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

public record JourneyCompleted(UserInTransit user, Tap endTap) {
    public UserNotInTransit apply(UserRepository userRepository) {
        Journey completedJourney = new Journey(user.startingTap(), endTap);
        List<Journey> updatedJourneyList = new ArrayList<>(user.journeys());
        updatedJourneyList.add(completedJourney);
        UserNotInTransit updatedUser = new UserNotInTransit(user.userId(), List.copyOf(updatedJourneyList));
        userRepository.updateUser(updatedUser.userId(), updatedUser);
        return updatedUser;
    }
}
