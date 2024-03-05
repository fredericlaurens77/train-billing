package org.laurens.train.billing.domain.network;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


class ZoneTest {

    public static Stream<Arguments> should_give_the_right_interval_between_zones() {
        return Stream.of(
                Arguments.of(Zone.ZONE_1, Zone.ZONE_1, 0),
                Arguments.of(Zone.ZONE_1, Zone.ZONE_2, 1),
                Arguments.of(Zone.ZONE_1, Zone.ZONE_3, 2),
                Arguments.of(Zone.ZONE_1, Zone.ZONE_4, 3),
                Arguments.of(Zone.ZONE_2, Zone.ZONE_1, 1),
                Arguments.of(Zone.ZONE_2, Zone.ZONE_2, 0),
                Arguments.of(Zone.ZONE_2, Zone.ZONE_3, 1),
                Arguments.of(Zone.ZONE_2, Zone.ZONE_4, 2),
                Arguments.of(Zone.ZONE_3, Zone.ZONE_1, 2),
                Arguments.of(Zone.ZONE_3, Zone.ZONE_2, 1),
                Arguments.of(Zone.ZONE_3, Zone.ZONE_3, 0),
                Arguments.of(Zone.ZONE_3, Zone.ZONE_4, 1),
                Arguments.of(Zone.ZONE_4, Zone.ZONE_1, 3),
                Arguments.of(Zone.ZONE_4, Zone.ZONE_2, 2),
                Arguments.of(Zone.ZONE_4, Zone.ZONE_3, 1),
                Arguments.of(Zone.ZONE_4, Zone.ZONE_4, 0)
        );
    }

    public static Stream<Arguments> should_give_the_closest_zone_to_city_center() {
        return Stream.of(
                Arguments.of(Zone.ZONE_1, Zone.ZONE_1, 0),
                Arguments.of(Zone.ZONE_1, Zone.ZONE_2, -1),
                Arguments.of(Zone.ZONE_1, Zone.ZONE_3, -2),
                Arguments.of(Zone.ZONE_1, Zone.ZONE_4, -3),
                Arguments.of(Zone.ZONE_2, Zone.ZONE_1, 1),
                Arguments.of(Zone.ZONE_2, Zone.ZONE_2, 0),
                Arguments.of(Zone.ZONE_2, Zone.ZONE_3, -1),
                Arguments.of(Zone.ZONE_2, Zone.ZONE_4, -2),
                Arguments.of(Zone.ZONE_3, Zone.ZONE_1, 2),
                Arguments.of(Zone.ZONE_3, Zone.ZONE_2, 1),
                Arguments.of(Zone.ZONE_3, Zone.ZONE_3, 0),
                Arguments.of(Zone.ZONE_3, Zone.ZONE_4, -1),
                Arguments.of(Zone.ZONE_4, Zone.ZONE_1, 3),
                Arguments.of(Zone.ZONE_4, Zone.ZONE_2, 2),
                Arguments.of(Zone.ZONE_4, Zone.ZONE_3, 1),
                Arguments.of(Zone.ZONE_4, Zone.ZONE_4, 0)
        );
    }

    @ParameterizedTest
    @MethodSource
    void should_give_the_right_interval_between_zones(Zone zoneA, Zone zoneB, int interval){
        assertThat(zoneA.intervalWith(zoneB)).isEqualTo(interval);
    }

    @ParameterizedTest
    @MethodSource
    void should_give_the_closest_zone_to_city_center(Zone zoneA, Zone zoneB, int answer){
        assertThat(zoneA.isCloserToCityCenter(zoneB)).isEqualTo(answer);
    }
}