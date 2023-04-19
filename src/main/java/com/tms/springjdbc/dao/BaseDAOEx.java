package com.tms.springjdbc.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.List;

public abstract class BaseDAOEx<T> {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private TransactionTemplate transactionTemplate;

    public BaseDAOEx(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.transactionTemplate = new TransactionTemplate(new DataSourceTransactionManager(dataSource));
    }

    @Transactional
    public void save(T entity) {
      //  transactionTemplate.execute(new TransactionCallbackWithoutResult() {
        //    @Override
          //  protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                String sql = "INSERT INTO " + getTableName() + " (" + getColumnNames() + ") VALUES (" + getColumnValues() + ")";
                namedParameterJdbcTemplate.update(sql, getParameters(entity));
          //  }
       // });
    }

    public void update(T entity) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                String sql = "UPDATE " + getTableName() + " SET " + getUpdateColumnValues() + " WHERE " + getWhereClause();
                namedParameterJdbcTemplate.update(sql, getParameters(entity));
            }
        });
    }

    public void delete(T entity) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                String sql = "DELETE FROM " + getTableName() + " WHERE " + getWhereClause();
                namedParameterJdbcTemplate.update(sql, getParameters(entity));
            }
        });
    }


    public T findById(Long id) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, params, getRowMapper());
    }

    public List<T> findAll() {
        String sql = "SELECT * FROM " + getTableName();
        return namedParameterJdbcTemplate.query(sql, getRowMapper());
    }

    protected abstract String getTableName();

    protected abstract String getColumnNames();

    protected abstract String getColumnValues();

    protected abstract String getUpdateColumnValues();

    protected abstract String getWhereClause();

    protected abstract MapSqlParameterSource getParameters(T entity);

    protected abstract RowMapper<T> getRowMapper();
}
