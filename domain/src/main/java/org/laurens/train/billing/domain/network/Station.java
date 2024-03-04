package org.laurens.train.billing.domain.network;

import java.util.List;

public enum Station{
    A("A", List.of(Zone.ZONE_1)),
    B("B", List.of(Zone.ZONE_1)),
    C("C", List.of(Zone.ZONE_2, Zone.ZONE_3)),
    D("D", List.of(Zone.ZONE_2)),
    E("E", List.of(Zone.ZONE_2, Zone.ZONE_3)),
    F("F", List.of(Zone.ZONE_3, Zone.ZONE_4)),
    G("G", List.of(Zone.ZONE_4)),
    H("H", List.of(Zone.ZONE_4)),
    I("I", List.of(Zone.ZONE_4));

    private final String stationName;
    private final List<Zone> zones;

    Station(String stationName, List<Zone> zones) {
        this.stationName = stationName;
        this.zones = zones;
    }
}
