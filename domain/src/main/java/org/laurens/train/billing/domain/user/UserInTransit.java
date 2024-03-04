package org.laurens.train.billing.domain.user;

import org.laurens.train.billing.domain.journey.Journey;
import org.laurens.train.billing.domain.journey.Tap;

import java.util.List;

public record UserInTransit(UserId userId, List<Journey> journeys, Tap startingTap) implements User {

}
