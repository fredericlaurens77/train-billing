package org.laurens.train.billing.infrastructure.billing;

import org.laurens.train.billing.domain.journey.Journey;

public record Trip(String stationStart,
        String stationEnd,
        int startedJourneyAt,
        int costInCents,
        int zoneFrom,
        int zoneTo) {


    public static Trip ofJourney(Journey journey) {
        return new Trip(
                journey.start().station().name(),
                journey.arrival().station().name(),
                journey.start().tapTime().localTime(),
                journey.cost().amountInCents(),
                journey.fromZoneToZone().start().number(),
                journey.fromZoneToZone().arrival().number());
    }
}
