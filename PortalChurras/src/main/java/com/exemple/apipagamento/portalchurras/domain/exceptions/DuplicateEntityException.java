package com.exemple.apipagamento.portalchurras.domain.exceptions;

public class DuplicateEntityException extends DomainException {
    private final String fieldName;
    private final Object value;

    public DuplicateEntityException(String entityName, String fieldName, Object value) {
        super(String.format("%s com %s '%s' já existe", entityName, fieldName, value),
                "DUPLICATE_ENTITY");
        this.fieldName = fieldName;
        this.value = value;
    }

    public String getFieldName() { return fieldName; }
    public Object getValue() { return value; }
}