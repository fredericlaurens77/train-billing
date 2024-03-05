package org.laurens.train.billing.infrastructure.billing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.writethemfirst.approvals.Approvals;
import org.junit.jupiter.api.Test;
import org.laurens.train.billing.domain.journey.Journey;
import org.laurens.train.billing.domain.journey.Tap;
import org.laurens.train.billing.domain.journey.TapTime;
import org.laurens.train.billing.domain.network.Station;
import org.laurens.train.billing.domain.user.User;
import org.laurens.train.billing.domain.user.UserId;
import org.laurens.train.billing.domain.user.UserNotInTransit;
import org.laurens.train.billing.domain.user.UserRepository;
import org.laurens.train.billing.infrastructure.users.MapUserRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BillingWriterTest {

    public static final String OUTPUT_TXT = "output.txt";
    public static final String OUTPUT_EMPTY_TXT = "output_empty.txt";
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void should_write_correct_billing_file_for_empty_user_repository() throws IOException {
        UserRepository userRepository = new MapUserRepository();
        BillingWriter billingWriter = new BillingWriter("output_empty.txt", userRepository);
        billingWriter.writeBillingFile();
        String json = objectMapper.writeValueAsString(objectMapper.readValue(new File(OUTPUT_EMPTY_TXT), CustomerSummaries.class));
        Approvals.verify(json);
        File file = new File(OUTPUT_EMPTY_TXT);
        assertThat(file.delete()).isTrue();
    }

    @Test
    void should_write_correct_billing_file_with_data() throws IOException {
        UserRepository userRepository = new UserRepository() {
            @Override
            public User findOrCreateUser(UserId userId) {
                return null;
            }

            @Override
            public void updateUser(UserId userId, User user) {
            }

            @Override
            public List<User> getAll() {
                return List.of(
                        new UserNotInTransit(UserId.of(1), List.of(
                                new Journey(new Tap(UserId.of(1), Station.A, new TapTime(1)),
                                        new Tap(UserId.of(2), Station.D, new TapTime(3)))
                        )));
            }
        };
        BillingWriter billingWriter = new BillingWriter(OUTPUT_TXT, userRepository);
        billingWriter.writeBillingFile();
        String json = objectMapper.writeValueAsString(objectMapper.readValue(new File(OUTPUT_TXT), CustomerSummaries.class));
        Approvals.verify(json);
        File file = new File(OUTPUT_TXT);
        assertThat(file.delete()).isTrue();
    }

    @Test
    void should_throw_exception_if_file_cannot_be_written(){
        UserRepository userRepository = new MapUserRepository();
        BillingWriter billingWriter = new BillingWriter("output??!!.txt", userRepository);
        assertThrows(CannotWriteBillingFileException.class, billingWriter::writeBillingFile);
    }
}