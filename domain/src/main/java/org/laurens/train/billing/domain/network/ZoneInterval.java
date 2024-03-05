package org.laurens.train.billing.domain.network;

public record ZoneInterval(Zone start, Zone arrival) {

    public ZoneInterval(Zone zone){
        this(zone, zone);
    }
    public int size(){
        return start.intervalWith(arrival);
    }
}
