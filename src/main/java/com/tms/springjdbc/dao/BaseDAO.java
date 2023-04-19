package com.tms.springjdbc.dao;


import com.tms.springjdbc.mapper.RowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDAO<T> {
    private final DataSource dataSource;
    private final ConnectionManager connectionManager;

    //    public BaseDAO(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }
    public BaseDAO(DataSource dataSource, ConnectionManager connectionManager) {
        this.dataSource = dataSource;
        this.connectionManager = connectionManager;
    }

    protected abstract String getTableName();

    protected abstract RowMapper<T> getRowMapper();

    protected abstract String getInsertSql();

    protected abstract void setInsertParameters(PreparedStatement preparedStatement, T entity) throws SQLException;

    public List<T> findAll() throws SQLException {
        String sql = "SELECT * FROM " + getTableName();
//        try (Connection connection = dataSource.getConnection();
        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            List<T> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(getRowMapper().mapRow(resultSet));
            }
            return result;
        }
    }

    public T findById(Object id) throws SQLException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setObject(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return getRowMapper().mapRow(resultSet);
                }
                return null;
            }
        }
    }

    public void save(T entity) throws SQLException {
        String sql = getInsertSql();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            setInsertParameters(preparedStatement, entity);
            preparedStatement.executeUpdate();
        }
    }

    public void executeBatch(List<T> entities) throws SQLException {
        String sql = getInsertSql();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            for (T entity : entities) {
                setInsertParameters(preparedStatement, entity);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    public List<T> findWithPaging(int offset, int limit) throws SQLException {
        String sql = "SELECT * FROM " + getTableName() + " LIMIT ?, ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, limit);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<T> result = new ArrayList<>();
                while (resultSet.next()) {
                    result.add(getRowMapper().mapRow(resultSet));
                }
                return result;
            }
        }
    }
}