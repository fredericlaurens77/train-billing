package org.laurens.train.billing.main;

import org.laurens.train.billing.infrastructure.billing.BillingWriter;
import org.laurens.train.billing.infrastructure.tap.TapReader;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class BillingGenerationPerformer {

    private final Logger logger = LoggerFactory.getLogger(BillingGenerationPerformer.class);
    private final TapReader tapReader;
    private final BillingWriter billingWriter;

    public BillingGenerationPerformer(TapReader tapReader, BillingWriter billingWriter){
        this.tapReader = tapReader;
        this.billingWriter = billingWriter;
    }

    public void perform() {
        logger.info("Started generating billing");
        tapReader.readTaps();
        billingWriter.writeBillingFile();
        logger.info("Finished generating billing successfully");
    }
}
