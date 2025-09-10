package com.exemple.apipagamento.portalchurras.domain.exceptions;

public class InvalidStateTransitionException extends DomainException {
    private final String currentState;
    private final String targetState;

    public InvalidStateTransitionException(String entity, String currentState, String targetState) {
        super(String.format("Transição inválida para %s: %s -> %s", entity, currentState, targetState),
                "INVALID_STATE_TRANSITION");
        this.currentState = currentState;
        this.targetState = targetState;
    }

    public String getCurrentState() { return currentState; }
    public String getTargetState() { return targetState; }
}