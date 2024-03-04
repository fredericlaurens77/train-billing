package org.laurens.train.billing.domain.user;

import org.laurens.train.billing.domain.journey.Journey;

import java.util.List;

public record UserNotInTransit(UserId userId, List<Journey> journeys) implements User {

}
