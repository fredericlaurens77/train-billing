package org.laurens.train.billing.domain.fare;

import org.laurens.train.billing.domain.network.Zone;
import org.laurens.train.billing.domain.network.ZoneInterval;

import java.util.*;

public enum Fare {

    WITHIN_1_OR_2(new Amount(240), Set.of(Zone.ZONE_1, Zone.ZONE_2)),
    WITHIN_3_OR_4(new Amount(200), Set.of(Zone.ZONE_3, Zone.ZONE_4)),
    FROM_1_OR_2_TO_3(new Amount(280), Set.of(Zone.ZONE_1, Zone.ZONE_2), Set.of(Zone.ZONE_3)),
    FROM_1_OR_2_TO_4(new Amount(300), Set.of(Zone.ZONE_1, Zone.ZONE_2), Set.of(Zone.ZONE_4));

    private final Amount amount;
    private final Set<Zone> startingZones;
    private final Set<Zone> arrivalZones;

    Fare(Amount amount, Set<Zone> withinZones) {
        this(amount, withinZones, withinZones);
    }

    Fare(Amount amount, Set<Zone> fromZones, Set<Zone> toZones) {
        this.amount = amount;
        this.startingZones = fromZones;
        this.arrivalZones = toZones;
    }

    public static Amount cost(ZoneInterval zonesCrossed){
        return costGrid().get(zonesCrossed).amount;
    }

    private static Map<ZoneInterval, Fare> costGrid(){
        Map<ZoneInterval, Fare> costGrid = new HashMap<>();
        Arrays.stream(Fare.values()).forEach(fare -> createZoneIntervalToFareMappings(costGrid, fare));
        return Map.copyOf(costGrid);
    }

    private static void createZoneIntervalToFareMappings(Map<ZoneInterval, Fare> costGrid, Fare fare) {
        fare.startingZones.forEach(startingZone -> fare.arrivalZones.forEach(
                arrivalZone -> {
                    costGrid.put(new ZoneInterval(startingZone,arrivalZone), fare);
                    costGrid.put(new ZoneInterval(arrivalZone, startingZone), fare);
                })
        );
    }
}
