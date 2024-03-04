package org.laurens.train.billing.domain.network;

public enum Zone {
    ZONE_1("1"),
    ZONE_2("2"),
    ZONE_3("3"),
    ZONE_4("4");

    private final String zoneName;
    Zone(String zoneName) {
        this.zoneName = zoneName;
    }
}
