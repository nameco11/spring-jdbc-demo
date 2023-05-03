package com.tms.springjdbc.infrastructure.dao.port;

import com.tms.springjdbc.domain.repository.BaseDao;
import com.tms.springjdbc.infrastructure.annotation.Column;
import com.tms.springjdbc.infrastructure.annotation.Id;
import com.tms.springjdbc.infrastructure.annotation.Table;
import com.tms.springjdbc.infrastructure.search.SearchResult;
import com.tms.springjdbc.infrastructure.search.SearchResultImpl;
import com.tms.springjdbc.infrastructure.search.SearchUtil;
import com.tms.springjdbc.infrastructure.search.SelectColumn;
import com.tms.springjdbc.presentation.web.dto.JoinParam;
import com.tms.springjdbc.presentation.web.dto.PageRequest;
import com.tms.springjdbc.presentation.web.dto.SearchParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class BaseDaoImpl<T,ID extends Serializable> implements BaseDao<T, ID> {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public BaseDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, Class<T> entityClass) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];;
    }

    @SuppressWarnings("unchecked")
    protected ID getId(T entity) {
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                try {
                    field.setAccessible(true);
                    return (ID) field.get(entity);
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException("Error accessing field with @Id annotation: " + field.getName(), e);
                }
            }
        }
        throw new IllegalStateException("Entity class " + entityClass.getName() + " must have a field annotated with @Id.");
    }

    protected String getTableName() {
        Table table = entityClass.getAnnotation(Table.class);
        if (table != null) {
            return table.value();
        } else {
            throw new IllegalStateException("Entity class " + entityClass.getName() + " must be annotated with @Table.");
        }
    }

    protected List<JoinParam> getJoinParam() {
        List<JoinParam> joinParams = new ArrayList<>();
        joinParams.add(new JoinParam(getTableName()));
        return joinParams;
    }


    @Override
    public T save(T entity) {
        String query = "INSERT INTO " + getTableName() + " (...) VALUES (...)";
        namedParameterJdbcTemplate.update(query, getSqlParameterSource(entity));
        return entity;
    }

    @Override
    public void delete(T entity) {
        String query = "DELETE FROM " + getTableName() + " WHERE id = :id";
        namedParameterJdbcTemplate.update(query, new MapSqlParameterSource("id", getId(entity)));
    }

    @Override
    public void deleteById(ID id) {
        String query = "DELETE FROM " + getTableName() + " WHERE id = :id";
        namedParameterJdbcTemplate.update(query, new MapSqlParameterSource("id", id));
    }

    @Override
    public Optional<T> findById(ID id) {
        String query = "SELECT * FROM " + getTableName() + " WHERE id = :id";
        List<T> result = namedParameterJdbcTemplate.query(query, new MapSqlParameterSource("id", id), getRowMapper());
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public List<T> findAll() {
        String query = "SELECT * FROM " + getTableName();
        return namedParameterJdbcTemplate.query(query, getRowMapper());
    }

    @Override
    public SearchResult<T> search(List<SelectColumn> selectColumns, List<SearchParam> searchParams, PageRequest pageRequest) {
        String query = SearchUtil.generateSql(selectColumns, searchParams, getJoinParam());
        return getSearchResult(searchParams, getJoinParam(), pageRequest, query);
    }

    @Override
    public SearchResult<T> searchJoin(List<SelectColumn> selectColumns, List<SearchParam> searchParams, List<JoinParam> joinParams, PageRequest pageRequest) {
        String query = SearchUtil.generateSql(selectColumns, searchParams, joinParams);
        return getSearchResult(searchParams, joinParams, pageRequest, query);
    }

    private SearchResult<T> getSearchResult(List<SearchParam> searchParams, List<JoinParam> joinParams, PageRequest pageRequest, String query) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        query += " LIMIT :offset, :size";
        parameterSource.addValue("offset", pageRequest.getPage() * pageRequest.getSize());
        parameterSource.addValue("size", pageRequest.getSize());

        List<T> content;
        try {
            content = namedParameterJdbcTemplate.query(query, parameterSource, new BeanPropertyRowMapper<>(entityClass));
        } catch (EmptyResultDataAccessException e) {
            content = new ArrayList<>();
        }

        long totalElements = countTotalElements(searchParams, joinParams, parameterSource);
        int totalPages = (int) Math.ceil((double) totalElements / pageRequest.getSize());
        return new SearchResultImpl<>(content, pageRequest.getPage(), pageRequest.getSize(), totalElements, totalPages);
    }

    private long countTotalElements(List<SearchParam> searchParams, List<JoinParam> joinParams, MapSqlParameterSource parameterSource) {
        String countQuery = SearchUtil.generateCountSql(searchParams, joinParams);
        return namedParameterJdbcTemplate.queryForObject(countQuery, parameterSource, Long.class);
    }


    protected final RowMapper<T> getRowMapper() {
        return new RowMapper<T>() {
            @Override
            public T mapRow(ResultSet rs, int rowNum) throws SQLException {
                T entity = null;
                try {
                    entity = getEntityClass().getDeclaredConstructor().newInstance();
                    for (Field field : entity.getClass().getDeclaredFields()) {
                        field.setAccessible(true);
                        String columnName = getColumnName(field);
                        Object value = rs.getObject(columnName);
                        field.set(entity, value);
                    }
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                         InvocationTargetException e) {
                    log.error("Error creating new instance of entity", e);
                }
                return entity;
            }
        };
    }
    protected final SqlParameterSource getSqlParameterSource(T entity) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
        for (Field field : entity.getClass().getDeclaredFields()) {
            String columnName = getColumnName(field);
            parameterSource.registerSqlType(columnName, Types.OTHER);
        }
        return parameterSource;
    }

    private String getColumnName(Field field) {
        String columnName = field.getName();
        Column column = field.getAnnotation(Column.class);
        if (column != null) {
            columnName = column.name();
        }
        return columnName;
    }

    @SuppressWarnings("unchecked")
    private Class<T> getEntityClass() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }
}

