package org.laurens.train.billing.infrastructure.billing;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.laurens.train.billing.domain.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;

import static org.laurens.train.billing.infrastructure.billing.CustomerSummaries.ofUserList;

public class BillingWriter {
    private final Logger logger = LoggerFactory.getLogger(BillingWriter.class);
    private final UserRepository userRepository;
    private final String destination;

    ObjectMapper objectMapper = new ObjectMapper();


    public BillingWriter(String destination, UserRepository userRepository){
        this.destination = destination;
        this.userRepository = userRepository;
    }

    public void writeBillingFile() {
        logger.info("Started writing billing at %s".formatted(destination));
        CustomerSummaries customerSummaries = ofUserList(userRepository.getAll());
        try {
            FileWriter fileWriter = new FileWriter(destination);
            fileWriter.write(objectMapper.writeValueAsString(customerSummaries));
            fileWriter.close();
            logger.info("Finished writing billing successfully");
        } catch (IOException e) {
            throw new CannotWriteBillingFileException("Cannot write billing file at %s".formatted(destination));
        }
    }
}
