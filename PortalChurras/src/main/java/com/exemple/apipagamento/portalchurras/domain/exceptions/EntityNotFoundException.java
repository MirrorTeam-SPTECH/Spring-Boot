package com.exemple.apipagamento.portalchurras.domain.exceptions;

public class EntityNotFoundException extends DomainException {
    private final String entityName;
    private final Object identifier;

    public EntityNotFoundException(String entityName, Object identifier) {
        super(String.format("%s não encontrado: %s", entityName, identifier),
                "ENTITY_NOT_FOUND");
        this.entityName = entityName;
        this.identifier = identifier;
    }

    public String getEntityName() { return entityName; }
    public Object getIdentifier() { return identifier; }
}
