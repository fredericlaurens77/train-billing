package org.laurens.train.billing.infrastructure.tap;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.laurens.train.billing.domain.usecase.TravelUseCase;
import org.laurens.train.billing.domain.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class TapReader {
    private final Logger logger = LoggerFactory.getLogger(TapReader.class);
    private final String fileName;
    private final TravelUseCase travel;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public TapReader(String fileName, UserRepository userRepository){
        this.fileName = fileName;
        this.travel = new TravelUseCase(userRepository);
    }

    public void readTaps() {
        Taps taps;
        try {
            logger.info("Started reading taps from file: %s".formatted(fileName));
            taps = objectMapper.readValue(new File(fileName), Taps.class);
        } catch (IOException e) {
            throw new TapFileNotFoundException("Tap file could not be found at %s".formatted(fileName));
        }
        taps.taps().forEach(tap -> travel.tapCard(tap.toDomainTap()));
        logger.info("Finished reading taps successfully");
    }
}
