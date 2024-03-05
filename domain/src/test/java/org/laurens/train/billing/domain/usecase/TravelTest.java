package org.laurens.train.billing.domain.usecase;

import org.junit.jupiter.api.Test;
import org.laurens.train.billing.domain.journey.Journey;
import org.laurens.train.billing.domain.journey.Tap;
import org.laurens.train.billing.domain.journey.TapTime;
import org.laurens.train.billing.domain.network.Station;
import org.laurens.train.billing.domain.user.User;
import org.laurens.train.billing.domain.user.UserId;
import org.laurens.train.billing.domain.user.UserInTransit;
import org.laurens.train.billing.domain.user.UserNotInTransit;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.laurens.train.billing.domain.user.User.NO_JOURNEY;

class TravelTest {

    private final UserId userId = new UserId(1);
    private final Tap previousStartingTap = new Tap(userId, Station.C, new TapTime(LocalTime.now()));
    private final Tap startingEndTap = new Tap(userId, Station.D, new TapTime(LocalTime.now()));
    private final Journey previousJourney = new Journey(previousStartingTap, startingEndTap);

    @Test
    void a_user_without_completed_journeys_should_be_able_to_start_a_trip() {
        //Given
        UserNotInTransit user = new UserNotInTransit(userId, NO_JOURNEY);
        Travel travel = new Travel();
        //When
        Tap tap = new Tap(userId, Station.A, new TapTime(LocalTime.now()));
        User updatedUser = travel.tapCard(user, tap);
        //Then
        assertThat(updatedUser.userId()).isEqualTo(userId);
        assertThat(updatedUser.journeys()).isEmpty();
        assertThat(updatedUser.getClass()).isEqualTo(UserInTransit.class);
        assertThat(((UserInTransit) updatedUser).startingTap()).isEqualTo(tap);
    }

    @Test
    void a_user_with_completed_journeys_should_be_able_to_start_a_trip() {
        //Given
        UserNotInTransit user = new UserNotInTransit(userId, List.of(previousJourney));
        Travel travel = new Travel();
        //When
        Tap tap = new Tap(userId, Station.A, new TapTime(LocalTime.now()));
        User updatedUser = travel.tapCard(user, tap);
        //Then
        assertThat(updatedUser.userId()).isEqualTo(userId);
        assertThat(updatedUser.journeys()).containsExactly(previousJourney);
        assertThat(updatedUser.getClass()).isEqualTo(UserInTransit.class);
        assertThat(((UserInTransit) updatedUser).startingTap()).isEqualTo(tap);
    }

    @Test
    void a_user_without_completed_journeys_should_be_able_to_end_a_trip() {
        //Given
        Tap startingTap = new Tap(userId, Station.A, new TapTime(LocalTime.now()));
        UserInTransit user = new UserInTransit(userId, NO_JOURNEY, startingTap);
        Travel travel = new Travel();
        //When
        Tap tap = new Tap(userId, Station.B, new TapTime(LocalTime.now()));
        User updatedUser = travel.tapCard(user, tap);
        //Then
        assertThat(updatedUser.userId()).isEqualTo(userId);
        assertThat(updatedUser.journeys()).containsExactly(new Journey(startingTap, tap));
        assertThat(updatedUser.getClass()).isEqualTo(UserNotInTransit.class);
    }

    @Test
    void a_user_with_completed_journeys_should_be_able_to_end_a_trip() {
        //Given
        Tap startingTap = new Tap(userId, Station.A, new TapTime(LocalTime.now()));
        UserInTransit user = new UserInTransit(userId, List.of(previousJourney), startingTap);
        Travel travel = new Travel();
        //When
        Tap tap = new Tap(userId, Station.B, new TapTime(LocalTime.now()));
        User updatedUser = travel.tapCard(user, tap);
        //Then
        assertThat(updatedUser.userId()).isEqualTo(userId);
        assertThat(updatedUser.journeys()).containsExactly(previousJourney, new Journey(startingTap, tap));
        assertThat(updatedUser.getClass()).isEqualTo(UserNotInTransit.class);
    }

}