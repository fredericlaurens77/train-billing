package org.laurens.train.billing.infrastructure.tap;

import org.junit.jupiter.api.Test;
import org.laurens.train.billing.domain.journey.Journey;
import org.laurens.train.billing.domain.journey.Tap;
import org.laurens.train.billing.domain.journey.TapTime;
import org.laurens.train.billing.domain.network.Station;
import org.laurens.train.billing.domain.user.*;
import org.laurens.train.billing.infrastructure.users.MapUserRepository;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TapReaderTest {

    ClassLoader classLoader = getClass().getClassLoader();

    @Test
    void should_be_able_to_read_a_tap() {
        UserRepository users = new MapUserRepository();
        TapReader tapReader = new TapReader(
                getFileName("tapFiles/oneTap.txt"), users);
        tapReader.readTaps();
        assertThat(users.getAll().stream().map(User::userId)).containsExactly(UserId.of(1));
        assertThat(users.getAll()).containsExactly(new UserInTransit(UserId.of(1), User.NO_JOURNEY, new Tap(UserId.of(1), Station.A, new TapTime(1))));
    }


    @Test
    void should_be_able_to_read_taps_for_one_user() {
        UserRepository users = new MapUserRepository();
        TapReader tapReader = new TapReader(getFileName("tapFiles/tapsForOneUser.txt"), users);
        tapReader.readTaps();
        assertThat(users.getAll().stream().map(User::userId)).containsExactly(new UserId(1));
        assertThat(users.getAll()).containsExactly(new UserNotInTransit(UserId.of(1),
                List.of(new Journey(
                        new Tap(UserId.of(1), Station.A, new TapTime(1)),
                        new Tap(UserId.of(1), Station.D, new TapTime(2))))));
    }

    @Test
    void should_be_able_to_read_taps_for_several_users_in_order() {
        UserRepository users = new MapUserRepository();
        TapReader tapReader = new TapReader(getFileName("tapFiles/tapsForSeveralUsersInOrder.txt"), users);
        tapReader.readTaps();
        assertThat(users.getAll().stream().map(User::userId)).containsExactly(new UserId(1), new UserId(2));
        assertThat(users.getAll()).containsExactlyInAnyOrder(
                new UserNotInTransit(UserId.of(1),
                    List.of(new Journey(
                            new Tap(UserId.of(1), Station.A, new TapTime(1)),
                            new Tap(UserId.of(1), Station.D, new TapTime(2))))),
                new UserNotInTransit(UserId.of(2),
                    List.of(new Journey(
                            new Tap(UserId.of(2), Station.B, new TapTime(2)),
                            new Tap(UserId.of(2), Station.C, new TapTime(3))))));
    }

    @Test
    void should_be_able_to_read_mixed_taps_for_several_users() throws NullPointerException {
        UserRepository users = new MapUserRepository();
        TapReader tapReader = new TapReader(getFileName("tapFiles/mixedTapsForSeveralUsers.txt"), users);
        tapReader.readTaps();
        assertThat(users.getAll().stream().map(User::userId)).containsExactly(new UserId(2), new UserId(3));
        assertThat(users.getAll()).containsExactlyInAnyOrder(new UserNotInTransit(UserId.of(3),
                List.of(new Journey(
                        new Tap(UserId.of(3), Station.H, new TapTime(3)),
                        new Tap(UserId.of(3), Station.E, new TapTime(27))))),
        new UserNotInTransit(UserId.of(2),
                List.of(new Journey(
                        new Tap(UserId.of(2), Station.H, new TapTime(3)),
                        new Tap(UserId.of(2), Station.G, new TapTime(10))))));
    }

    @Test
    void should_throw_exception_if_tap_file_does_not_exist(){
        UserRepository users = new MapUserRepository();
        TapReader tapReader = new TapReader("imaginary.txt", users);
        assertThrows(TapFileNotFoundException.class, tapReader::readTaps);
    }

    private String getFileName(String name) {
        return Objects.requireNonNull(classLoader.getResource(name)).getFile();
    }
}