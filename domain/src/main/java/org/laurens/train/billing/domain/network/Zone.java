package org.laurens.train.billing.domain.network;

public enum Zone {
    ZONE_1(1),
    ZONE_2(2),
    ZONE_3(3),
    ZONE_4(4);

    private final int zoneOrder;
    Zone(int zoneOrder) {
        this.zoneOrder = zoneOrder;
    }

    public int intervalWith(Zone otherZone){
        return Math.abs(this.zoneOrder - otherZone.zoneOrder);
    }

    public int isCloserToCityCenter(Zone otherZone){
        return this.zoneOrder - otherZone.zoneOrder;
    }
}
