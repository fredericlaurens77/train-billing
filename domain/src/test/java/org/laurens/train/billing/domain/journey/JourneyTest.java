package org.laurens.train.billing.domain.journey;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.laurens.train.billing.domain.fare.Amount;
import org.laurens.train.billing.domain.network.Station;
import org.laurens.train.billing.domain.network.Zone;
import org.laurens.train.billing.domain.network.ZoneInterval;
import org.laurens.train.billing.domain.user.UserId;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class JourneyTest {

    public static Stream<Arguments> should_give_the_right_set_of_crossed_zones() {
        return Stream.of(
                Arguments.of(Station.A, Station.B, new ZoneInterval(Zone.ZONE_1)),
                Arguments.of(Station.B, Station.A, new ZoneInterval(Zone.ZONE_1)),
                Arguments.of(Station.A, Station.C, new ZoneInterval(Zone.ZONE_1, Zone.ZONE_2)),
                Arguments.of(Station.B, Station.D, new ZoneInterval(Zone.ZONE_1, Zone.ZONE_2)),
                Arguments.of(Station.D, Station.B, new ZoneInterval(Zone.ZONE_2, Zone.ZONE_1)),
                Arguments.of(Station.B, Station.F, new ZoneInterval(Zone.ZONE_1, Zone.ZONE_3)),
                Arguments.of(Station.F, Station.B, new ZoneInterval(Zone.ZONE_3, Zone.ZONE_1)),
                Arguments.of(Station.D, Station.F, new ZoneInterval(Zone.ZONE_2, Zone.ZONE_3)),
                Arguments.of(Station.F, Station.D, new ZoneInterval(Zone.ZONE_3, Zone.ZONE_2)),
                Arguments.of(Station.H, Station.F, new ZoneInterval(Zone.ZONE_4)),
                Arguments.of(Station.F, Station.H, new ZoneInterval(Zone.ZONE_4)),
                Arguments.of(Station.A, Station.H, new ZoneInterval(Zone.ZONE_1, Zone.ZONE_4)),
                Arguments.of(Station.H, Station.A, new ZoneInterval(Zone.ZONE_4, Zone.ZONE_1))
        );
    }

    public static Stream<Arguments> should_give_the_right_price_for_journey() {
        return Stream.of(
                Arguments.of(Station.A, Station.B, 240),
                Arguments.of(Station.B, Station.A, 240),
                Arguments.of(Station.A, Station.C, 240),
                Arguments.of(Station.B, Station.D, 240),
                Arguments.of(Station.D, Station.B, 240),
                Arguments.of(Station.B, Station.F, 280),
                Arguments.of(Station.F, Station.B, 280),
                Arguments.of(Station.D, Station.F, 280),
                Arguments.of(Station.F, Station.D, 280),
                Arguments.of(Station.E, Station.F, 200),
                Arguments.of(Station.F, Station.E, 200),
                Arguments.of(Station.H, Station.F, 200),
                Arguments.of(Station.F, Station.H, 200),
                Arguments.of(Station.A, Station.H, 300),
                Arguments.of(Station.H, Station.A, 300)
        );
    }

    @ParameterizedTest
    @MethodSource
    void should_give_the_right_set_of_crossed_zones(Station start, Station arrival, ZoneInterval zoneInterval){
        assertThat(new Journey(
                new Tap(new UserId(0), start, new TapTime(1)),
                new Tap(new UserId(0), arrival, new TapTime(1))).fromZoneToZone()).isEqualTo(zoneInterval);
    }

    @ParameterizedTest
    @MethodSource
    void should_give_the_right_price_for_journey(Station start, Station arrival, int price){
        assertThat(new Journey(
                new Tap(new UserId(0), start, new TapTime(1)),
                new Tap(new UserId(0), arrival, new TapTime(1))).cost()).isEqualTo(new Amount(price));
    }
}