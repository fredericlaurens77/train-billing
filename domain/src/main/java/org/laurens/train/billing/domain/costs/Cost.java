package org.laurens.train.billing.domain.costs;

import org.laurens.train.billing.domain.network.Zone;

import java.util.*;

public enum Cost {

    COST_1_OR_2(new Amount(240), Set.of(Zone.ZONE_1, Zone.ZONE_2)),
    COST_3_OR_4(new Amount(200), Set.of(Zone.ZONE_3, Zone.ZONE_4)),
    COST_1_OR_2_TO_3(new Amount(280), Set.of(Zone.ZONE_1, Zone.ZONE_2), Set.of(Zone.ZONE_3)),
    COST_1_OR_2_TO_4(new Amount(300), Set.of(Zone.ZONE_1, Zone.ZONE_2), Set.of(Zone.ZONE_4));

    private final Amount amount;
    private final Set<Zone> startingZones;
    private final Set<Zone> arrivalZones;

    Cost(Amount amount, Set<Zone> zoneCrossed) {
        this(amount, zoneCrossed, zoneCrossed);
    }

    Cost(Amount amount, Set<Zone> startingZones, Set<Zone> arrivalZones) {
        this.amount = amount;
        this.startingZones = startingZones;
        this.arrivalZones = arrivalZones;
    }

    public static Amount cost(Set<Zone> zones){
        return costGrid().get(zones).amount;
    }

    private static Map<Set<Zone>, Cost> costGrid(){
        Map<Set<Zone>, Cost> costGrid = new HashMap<>();
        Arrays.stream(Cost.values()).forEach(cost -> createEntries(costGrid, cost));
        return Map.copyOf(costGrid);
    }

    private static void createEntries(Map<Set<Zone>, Cost> costGrid, Cost cost) {
        cost.startingZones.forEach(startingZone -> cost.arrivalZones.forEach(arrivalZone -> {
            if(startingZone.equals(arrivalZone)){
                costGrid.put(Set.of(startingZone), cost);
            } else {
                costGrid.put(Set.of(startingZone, arrivalZone), cost);
            }
        })
        );
    }
}
