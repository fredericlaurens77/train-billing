package org.laurens.train.billing.infrastructure.billing;

public class CannotWriteBillingFileException extends RuntimeException {
    final String message;

    public CannotWriteBillingFileException(String message) {
        this.message = message;
    }
}
