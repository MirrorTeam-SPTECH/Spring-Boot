package com.exemple.apipagamento.portalchurras.domain.exceptions;

public class PaymentException extends DomainException {
    public PaymentException(String message) {
        super(message, "PAYMENT_ERROR");
    }

    public PaymentException(String message, Throwable cause) {
        super(message, "PAYMENT_ERROR", cause);
    }
}

