package org.laurens.train.billing.main;

import org.laurens.train.billing.infrastructure.billing.BillingWriter;
import org.laurens.train.billing.infrastructure.tap.TapReader;

public class BillingGenerationPerformer {

    private final TapReader tapReader;
    private final BillingWriter billingWriter;

    public BillingGenerationPerformer(TapReader tapReader, BillingWriter billingWriter){
        this.tapReader = tapReader;
        this.billingWriter = billingWriter;
    }

    public void perform() {
        tapReader.readTaps();
        billingWriter.writeBillingFile();
    }
}
