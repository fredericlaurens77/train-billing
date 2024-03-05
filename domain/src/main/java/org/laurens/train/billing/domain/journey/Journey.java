package org.laurens.train.billing.domain.journey;

import org.laurens.train.billing.domain.fare.Amount;
import org.laurens.train.billing.domain.fare.Fare;
import org.laurens.train.billing.domain.network.ZoneInterval;

public record Journey(Tap start, Tap arrival) {
    public ZoneInterval fromZoneToZone(){
        return start.station().smallestZoneIntervalWith(arrival.station());
    }
    public Amount cost(){
        return Fare.cost(fromZoneToZone());
    }
}
