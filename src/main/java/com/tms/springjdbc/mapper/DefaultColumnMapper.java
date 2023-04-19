package com.tms.springjdbc.mapper;

public class DefaultColumnMapper implements ColumnMapper {
    @Override
    public String getColumnName(String propertyName) {
        return toSnakeCase(propertyName);
    }

    private String toSnakeCase(String input) {
        return input.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }
}

