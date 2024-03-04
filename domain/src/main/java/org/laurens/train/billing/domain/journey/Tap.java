package org.laurens.train.billing.domain.journey;

import org.laurens.train.billing.domain.network.Station;
import org.laurens.train.billing.domain.user.UserId;

public record Tap(UserId userId, Station station, TapTime tapTime) {
}
