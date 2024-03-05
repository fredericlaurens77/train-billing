package org.laurens.train.billing.infrastructure.billing;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.laurens.train.billing.domain.user.UserRepository;

import java.io.FileWriter;
import java.io.IOException;

import static org.laurens.train.billing.infrastructure.billing.CustomerSummaries.ofUserList;

public class BillingWriter {

    private final UserRepository userRepository;
    private final String destination;

    ObjectMapper objectMapper = new ObjectMapper();


    public BillingWriter(String destination, UserRepository userRepository){
        this.destination = destination;
        this.userRepository = userRepository;
    }

    public void writeBillingFile() {
        CustomerSummaries customerSummaries = ofUserList(userRepository.getAll());
        try {
            FileWriter fileWriter = new FileWriter(destination);
            fileWriter.write(objectMapper.writeValueAsString(customerSummaries));
            fileWriter.close();
        } catch (IOException e) {
            throw new CannotWriteBillingFileException("Cannot write billing file at %s".formatted(destination));
        }
    }
}
