package org.laurens.train.billing.main;

import org.junit.jupiter.api.Test;
import org.laurens.train.billing.infrastructure.billing.BillingWriter;
import org.laurens.train.billing.infrastructure.tap.TapReader;

import static org.mockito.Mockito.*;

class BillingGenerationPerformerTest {

    @Test
    void should_call_tap_reader_and_billing_writer() {
        TapReader tapReader = mock(TapReader.class);
        BillingWriter billingWriter = mock(BillingWriter.class);
        BillingGenerationPerformer billingGenerationPerformer = new BillingGenerationPerformer(tapReader, billingWriter);
        billingGenerationPerformer.perform();
        verify(tapReader, times(1)).readTaps();
        verify(billingWriter, times(1)).writeBillingFile();
    }
}