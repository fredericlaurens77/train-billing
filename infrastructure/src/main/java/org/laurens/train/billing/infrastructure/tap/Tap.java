package org.laurens.train.billing.infrastructure.tap;

import org.laurens.train.billing.domain.journey.TapTime;
import org.laurens.train.billing.domain.network.Station;
import org.laurens.train.billing.domain.user.UserId;

public record Tap(int unixTimestamp, int customerId, String station) {

    org.laurens.train.billing.domain.journey.Tap toDomainTap(){
        return new org.laurens.train.billing.domain.journey.Tap(new UserId(customerId), Station.valueOf(station), new TapTime(unixTimestamp));
    }
}
