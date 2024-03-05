package org.laurens.train.billing.domain.network;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class StationTest {


    public static Stream<Arguments> should_give_min_zone_for_station() {
        return Stream.of(
                Arguments.of(Station.A, Zone.ZONE_1),
                Arguments.of(Station.B, Zone.ZONE_1),
                Arguments.of(Station.C, Zone.ZONE_2),
                Arguments.of(Station.D, Zone.ZONE_2),
                Arguments.of(Station.E, Zone.ZONE_2),
                Arguments.of(Station.F, Zone.ZONE_3),
                Arguments.of(Station.G, Zone.ZONE_4),
                Arguments.of(Station.H, Zone.ZONE_4),
                Arguments.of(Station.I, Zone.ZONE_4)
        );
    }

    public static Stream<Arguments> should_give_max_zone_for_station() {
        return Stream.of(
                Arguments.of(Station.A, Zone.ZONE_1),
                Arguments.of(Station.B, Zone.ZONE_1),
                Arguments.of(Station.C, Zone.ZONE_3),
                Arguments.of(Station.D, Zone.ZONE_2),
                Arguments.of(Station.E, Zone.ZONE_3),
                Arguments.of(Station.F, Zone.ZONE_4),
                Arguments.of(Station.G, Zone.ZONE_4),
                Arguments.of(Station.H, Zone.ZONE_4),
                Arguments.of(Station.I, Zone.ZONE_4)
        );
    }

    public static Stream<Arguments> should_give_smallest_zone_interval_between_stations() {
        return Stream.of(
                Arguments.of(Station.A, Station.B, Set.of(Zone.ZONE_1)),
                Arguments.of(Station.A, Station.C, Set.of(Zone.ZONE_1, Zone.ZONE_2)),
                Arguments.of(Station.A, Station.F, Set.of(Zone.ZONE_1, Zone.ZONE_3)),
                Arguments.of(Station.E, Station.F, Set.of(Zone.ZONE_3)),
                Arguments.of(Station.E, Station.G, Set.of(Zone.ZONE_3, Zone.ZONE_4)),
                Arguments.of(Station.F, Station.G, Set.of(Zone.ZONE_4))
        );
    }

    @ParameterizedTest
    @MethodSource
    void should_give_min_zone_for_station(Station station, Zone minZone){
        assertThat(station.minZone()).isEqualTo(minZone);
    }
    @ParameterizedTest
    @MethodSource
    void should_give_max_zone_for_station(Station station, Zone maxZone){
        assertThat(station.maxZone()).isEqualTo(maxZone);
    }

    @ParameterizedTest
    @MethodSource
    void should_give_smallest_zone_interval_between_stations(Station stationA, Station stationB, Set<Zone> zones){
        assertThat(stationA.smallestZoneIntervalWith(stationB)).isEqualTo(zones);
    }
}