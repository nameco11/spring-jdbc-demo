package com.tms.springjdbc.infrastructure.search;

public enum Operation {
    EQUAL("="),
    NOT_EQUAL("<>"),
    GREATER_THAN(">"),
    LESS_THAN("<"),
    GREATER_THAN_OR_EQUAL_TO(">="),
    LESS_THAN_OR_EQUAL_TO("<="),
    BETWEEN("BETWEEN"),
    LIKE("LIKE"),
    OR("OR");

    private final String value;

    Operation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

