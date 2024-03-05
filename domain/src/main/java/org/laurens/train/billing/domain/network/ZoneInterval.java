package org.laurens.train.billing.domain.network;

public record ZoneInterval(Zone zoneA, Zone zoneB) {

    public int size(){
        return zoneA.intervalWith(zoneB);
    }
}
