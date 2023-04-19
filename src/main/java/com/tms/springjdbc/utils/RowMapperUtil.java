package com.tms.springjdbc.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

public class RowMapperUtil<T> {
    private Function<ResultSet, T> rowMapper;

    public RowMapperUtil(Function<ResultSet, T> rowMapper) {
        this.rowMapper = rowMapper;
    }

    public T mapRow(ResultSet resultSet) throws SQLException {
        return rowMapper.apply(resultSet);
    }
}
