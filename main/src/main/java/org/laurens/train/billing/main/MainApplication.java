package org.laurens.train.billing.main;

import org.laurens.train.billing.domain.user.UserRepository;
import org.laurens.train.billing.infrastructure.billing.BillingWriter;
import org.laurens.train.billing.infrastructure.tap.TapReader;
import org.laurens.train.billing.infrastructure.users.MapUserRepository;


public class MainApplication {

    public static void main(String[] args) {
        validateArguments(args);
        UserRepository userRepository = new MapUserRepository();
        TapReader tapReader = new TapReader(args[0], userRepository);
        BillingWriter billingWriter = new BillingWriter(args[1], userRepository);
        BillingGenerationPerformer billingGenerationPerformer = new BillingGenerationPerformer(tapReader, billingWriter);
        billingGenerationPerformer.perform();
    }

    private static void validateArguments(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("""
         Exactly two parameters are needed:
         - the name of the tap file to process
         - and the name of the billing file to be written""");
        }
        validateSingleFileName(args[0], "Tap file cannot be blank");

        validateSingleFileName(args[1], "Destination billing file cannot be blank");
    }

    private static void validateSingleFileName(String arg, String fileCannotBeBlank) {
        if (arg == null || arg.isBlank()) {
            throw new IllegalArgumentException(fileCannotBeBlank);
        }
    }
}
