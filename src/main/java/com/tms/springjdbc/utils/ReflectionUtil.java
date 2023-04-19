package com.tms.springjdbc.utils;

import com.tms.springjdbc.mapper.ColumnMapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReflectionUtil {
    public static <T> T mapRowToEntity(ResultSet resultSet, Class<T> entityClass, ColumnMapper columnMapper) throws SQLException {
        try {
            T instance = entityClass.getDeclaredConstructor().newInstance();
            Field[] fields = entityClass.getDeclaredFields();
            for (Field field : fields) {
                String propertyName = field.getName();
                String columnName = columnMapper.getColumnName(propertyName);
                Object value = resultSet.getObject(columnName);
                if (value != null) {
                    String setterName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
                    Method setter = entityClass.getMethod(setterName, field.getType());
                    setter.invoke(instance, value);
                }
            }
            return instance;
        } catch (Exception e) {
            throw new SQLException("Failed to map row to entity", e);
        }
    }
}

