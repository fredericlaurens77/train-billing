package org.laurens.train.billing.domain.journey;

import org.laurens.train.billing.domain.costs.Amount;
import org.laurens.train.billing.domain.costs.Cost;
import org.laurens.train.billing.domain.network.Zone;

import java.util.Set;

public record Journey(Tap start, Tap arrival) {

    public Set<Zone> zonesCrossed(){
        return start.station().smallestZoneIntervalWith(arrival.station());
    }

    public Amount cost(){
        return Cost.cost(zonesCrossed());
    }
}
