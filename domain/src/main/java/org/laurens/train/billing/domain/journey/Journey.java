package org.laurens.train.billing.domain.journey;

import org.laurens.train.billing.domain.network.Zone;

import java.util.Set;

public record Journey(Tap start, Tap arrival) {

    public Set<Zone> zonesCrossed(){
        return start.station().smallestZoneIntervalWith(arrival.station());
    }
}
