package br.com.microservices.orchestrated.paymentservice.core.enums;

public enum ESagaStatus {
    SUCCESS,
    ROLLBACK_FAILING,
    FAIL
}