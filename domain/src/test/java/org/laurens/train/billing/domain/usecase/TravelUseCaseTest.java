package org.laurens.train.billing.domain.usecase;

import org.junit.jupiter.api.Test;
import org.laurens.train.billing.domain.journey.Journey;
import org.laurens.train.billing.domain.journey.Tap;
import org.laurens.train.billing.domain.journey.TapTime;
import org.laurens.train.billing.domain.network.Station;
import org.laurens.train.billing.domain.user.*;

import static org.assertj.core.api.Assertions.assertThat;

class TravelUseCaseTest {

    private final UserRepository users = new UserRepositoryFake();
    private final UserId userId = new UserId(1);
    private final Tap previousStartingTap = new Tap(userId, Station.C, new TapTime(1));
    private final Tap startingEndTap = new Tap(userId, Station.D, new TapTime(1));
    private final Journey previousJourney = new Journey(previousStartingTap, startingEndTap);

    @Test
    void a_user_without_completed_journeys_should_be_able_to_start_a_trip() {
        //Given
        TravelUseCase travel = new TravelUseCase(users);
        //When
        Tap tap = new Tap(userId, Station.A, new TapTime(1));
        User updatedUser = travel.tapCard(tap);
        //Then
        assertThat(updatedUser.userId()).isEqualTo(userId);
        assertThat(updatedUser.journeys()).isEmpty();
        assertThat(updatedUser.getClass()).isEqualTo(UserInTransit.class);
        assertThat(((UserInTransit) updatedUser).startingTap()).isEqualTo(tap);
    }

    @Test
    void a_user_with_completed_journeys_should_be_able_to_start_a_trip() {
        //Given
        TravelUseCase travel = new TravelUseCase(users);
        travel.tapCard(previousJourney.start());
        travel.tapCard(previousJourney.arrival());
        //When
        Tap tap = new Tap(userId, Station.A, new TapTime(1));
        User updatedUser = travel.tapCard(tap);
        //Then
        assertThat(updatedUser.userId()).isEqualTo(userId);
        assertThat(updatedUser.journeys()).containsExactly(previousJourney);
        assertThat(updatedUser.getClass()).isEqualTo(UserInTransit.class);
        assertThat(((UserInTransit) updatedUser).startingTap()).isEqualTo(tap);
    }

    @Test
    void a_user_without_completed_journeys_should_be_able_to_end_a_trip() {
        //Given
        Tap startingTap = new Tap(userId, Station.A, new TapTime(1));
        TravelUseCase travel = new TravelUseCase(users);
        travel.tapCard(startingTap);
        //When
        Tap tap = new Tap(userId, Station.B, new TapTime(1));
        User updatedUser = travel.tapCard(tap);
        //Then
        assertThat(updatedUser.userId()).isEqualTo(userId);
        assertThat(updatedUser.journeys()).containsExactly(new Journey(startingTap, tap));
        assertThat(updatedUser.getClass()).isEqualTo(UserNotInTransit.class);
    }

    @Test
    void a_user_with_completed_journeys_should_be_able_to_end_a_trip() {
        //Given
        Tap startingTap = new Tap(userId, Station.A, new TapTime(2));
        TravelUseCase travel = new TravelUseCase(users);
        travel.tapCard(previousJourney.start());
        travel.tapCard(previousJourney.arrival());
        travel.tapCard(startingTap);
        //When
        Tap tap = new Tap(userId, Station.B, new TapTime(1));
        User updatedUser = travel.tapCard(tap);
        //Then
        assertThat(updatedUser.userId()).isEqualTo(userId);
        assertThat(updatedUser.journeys()).containsExactly(previousJourney, new Journey(startingTap, tap));
        assertThat(updatedUser.getClass()).isEqualTo(UserNotInTransit.class);
    }

}