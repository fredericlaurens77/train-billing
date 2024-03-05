package org.laurens.train.billing.infrastructure.tap;

public class TapFileNotFoundException extends RuntimeException {

    final String message;

    public TapFileNotFoundException(String message) {
        this.message = message;
    }
}
