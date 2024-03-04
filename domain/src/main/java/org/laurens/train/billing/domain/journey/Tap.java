package org.laurens.train.billing.domain.journey;

import org.laurens.train.billing.domain.network.Station;
import org.laurens.train.billing.domain.user.User;

public record Tap(User user, Station station, TapTime tapTime) {
}
