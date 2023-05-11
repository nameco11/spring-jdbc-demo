package com.tms.springjdbc.infrastructure.ext;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;

public class CustomBeanPropertySqlParameterSource extends BeanPropertySqlParameterSource {

    public CustomBeanPropertySqlParameterSource(Object object) {
        super(object);
    }

    @Override
    public boolean hasValue(String paramName) {
        String camelCaseParamName = toCamelCase(paramName);
        return super.hasValue(camelCaseParamName);
    }

    @Override
    public Object getValue(String paramName) throws IllegalArgumentException {
        String camelCaseParamName = toCamelCase(paramName);
        return super.getValue(camelCaseParamName);
    }

    private String toCamelCase(String s) {
        String[] parts = s.split("_");
        StringBuilder camelCaseString = new StringBuilder(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            camelCaseString.append(toProperCase(parts[i]));
        }
        return camelCaseString.toString();
    }

    private String toProperCase(String s) {
        return s.substring(0, 1).toUpperCase() +
                s.substring(1).toLowerCase();
    }
}

