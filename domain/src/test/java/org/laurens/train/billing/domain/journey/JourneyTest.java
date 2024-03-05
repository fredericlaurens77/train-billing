package org.laurens.train.billing.domain.journey;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.laurens.train.billing.domain.network.Station;
import org.laurens.train.billing.domain.network.Zone;
import org.laurens.train.billing.domain.user.UserId;

import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class JourneyTest {

    public static Stream<Arguments> should_give_the_right_set_of_crossed_zones() {
        return Stream.of(
                Arguments.of(Station.A, Station.B, Set.of(Zone.ZONE_1)),
                Arguments.of(Station.B, Station.A, Set.of(Zone.ZONE_1)),
                Arguments.of(Station.A, Station.C, Set.of(Zone.ZONE_1, Zone.ZONE_2)),
                Arguments.of(Station.B, Station.D, Set.of(Zone.ZONE_1, Zone.ZONE_2)),
                Arguments.of(Station.D, Station.B, Set.of(Zone.ZONE_1, Zone.ZONE_2)),
                Arguments.of(Station.B, Station.F, Set.of(Zone.ZONE_1, Zone.ZONE_3)),
                Arguments.of(Station.F, Station.B, Set.of(Zone.ZONE_1, Zone.ZONE_3)),
                Arguments.of(Station.D, Station.F, Set.of(Zone.ZONE_2, Zone.ZONE_3)),
                Arguments.of(Station.F, Station.D, Set.of(Zone.ZONE_2, Zone.ZONE_3)),
                Arguments.of(Station.H, Station.F, Set.of(Zone.ZONE_4)),
                Arguments.of(Station.F, Station.H, Set.of(Zone.ZONE_4)),
                Arguments.of(Station.A, Station.H, Set.of(Zone.ZONE_1, Zone.ZONE_4)),
                Arguments.of(Station.H, Station.A, Set.of(Zone.ZONE_1, Zone.ZONE_4))
        );
    }

    @ParameterizedTest
    @MethodSource
    void should_give_the_right_set_of_crossed_zones(Station start, Station arrival, Set<Zone> crossedZones){
        assertThat(new Journey(
                new Tap(new UserId(0), start, new TapTime(LocalTime.now())),
                new Tap(new UserId(0), arrival, new TapTime(LocalTime.now()))).zonesCrossed()).isEqualTo(crossedZones);
    }

}