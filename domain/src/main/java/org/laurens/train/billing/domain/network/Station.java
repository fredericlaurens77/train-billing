package org.laurens.train.billing.domain.network;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public enum Station{
    A(Set.of(Zone.ZONE_1)),
    B(Set.of(Zone.ZONE_1)),
    C(Set.of(Zone.ZONE_2, Zone.ZONE_3)),
    D(Set.of(Zone.ZONE_2)),
    E(Set.of(Zone.ZONE_2, Zone.ZONE_3)),
    F(Set.of(Zone.ZONE_3, Zone.ZONE_4)),
    G(Set.of(Zone.ZONE_4)),
    H(Set.of(Zone.ZONE_4)),
    I(Set.of(Zone.ZONE_4));

    private final Set<Zone> zones;

    Station(Set<Zone> zones) {
        this.zones = zones;
    }

    public Zone minZone(){
        return this.zones.stream().min(Zone::isCloserToCityCenter).orElse(zones.stream().iterator().next());
    }

    public Zone maxZone(){
        return this.zones.stream().max(Zone::isCloserToCityCenter).orElse(zones.stream().iterator().next());
    }

    public ZoneInterval smallestZoneIntervalWith(Station otherStation){
        List<ZoneInterval> intervals = List.of(
                new ZoneInterval(this.minZone(), otherStation.minZone()),
                new ZoneInterval(this.maxZone(), otherStation.maxZone()),
                new ZoneInterval(this.maxZone(), otherStation.minZone()),
                new ZoneInterval(this.minZone(), otherStation.maxZone()));

        return intervals.stream().min(Comparator.comparing(ZoneInterval::size)).orElse(intervals.getFirst());
    }
}
