package org.laurens.train.billing.main;

import org.junit.jupiter.api.Test;
import org.laurens.train.billing.infrastructure.tap.TapFileNotFoundException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class MainApplicationTest {
    @Test
    void should_throw_an_exception_because_the_number_of_arguments_is_less_than_two() {
        Exception exception = assertThrows(RuntimeException.class, () -> MainApplication.main(new String[]{"one"}));
        assertThat(exception.getMessage()).contains("""
         Exactly two parameters are needed:
         - the name of the tap file to process
         - and the name of the billing file to be written""");
    }
    @Test
    void should_throw_an_exception_because_the_number_of_arguments_more_than_two() {
        Exception exception = assertThrows(RuntimeException.class, () -> MainApplication.main(new String[]{"one", "two", "three"}));
        assertThat(exception.getMessage()).contains("""
         Exactly two parameters are needed:
         - the name of the tap file to process
         - and the name of the billing file to be written""");
    }
    @Test
    void should_throw_an_exception_because_argument_one_is_blank() {
        Exception exception = assertThrows(RuntimeException.class, () -> MainApplication.main(new String[]{" ", "two"}));
        assertThat(exception.getMessage()).contains("Tap file cannot be blank");
    }
    @Test
    void should_throw_an_exception_because_argument_two_is_blank() {
        Exception exception = assertThrows(RuntimeException.class, () -> MainApplication.main(new String[]{"one", null}));
        assertThat(exception.getMessage()).contains("Destination billing file cannot be blank");
    }

    @Test
    void should_throw_file_not_found_exception(){
        assertThrows(TapFileNotFoundException.class, () -> MainApplication.main(new String[]{"one", "two"}));
    }
}