package com.exemple.apipagamento.portalchurras.domain.exceptions;

public class BusinessRuleException extends DomainException {
    public BusinessRuleException(String message) {
        super(message, "BUSINESS_RULE_VIOLATION");
    }
}