package com.tms.springjdbc.advantage.impl;

import com.tms.springjdbc.advantage.Advantage;
import com.tms.springjdbc.advantage.annotation.Column;
import com.tms.springjdbc.advantage.annotation.Id;
import com.tms.springjdbc.advantage.paging.Paging;
import com.tms.springjdbc.advantage.paging.PagingResult;
import com.tms.springjdbc.utils.DbUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdvantageImpl implements Advantage {
    private final static Log log = LogFactory.getLog(AdvantageImpl.class);

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public AdvantageImpl() {

    }

    public AdvantageImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public boolean insert(Object object) {
        StringBuilder sql = new StringBuilder();
        StringBuilder paramsSql = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        sql.append("insert into ");
        sql.append(DbUtils.getTableName(object.getClass()));
        sql.append(" (");
        int count = 0;

        Field idField = null;
        try {
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                if ("serialVersionUID".equals(fieldName)) {
                    continue;
                }
                Column column = field.getAnnotation(Column.class);
                if (null == column) {
                    continue;
                }
                Id id = field.getAnnotation(Id.class);
                if (null != id) {
                    idField = field;
                }
                Object value = DbUtils.getFieldValue(object, fieldName);
                if (value == null) {
                    continue;
                }
                if (null != id) {
                    idField = null;
                }

                if (count != 0) {
                    sql.append(',');
                    paramsSql.append(',');
                }
                count++;
                sql.append('`');
                sql.append(column.name());
                sql.append('`');

                paramsSql.append('?');
                params.add(value);
            }
            sql.append(") values (");
            sql.append(paramsSql);
            sql.append(')');

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        Object[] objects = params.toArray();

        boolean res = 1 == execute(sql.toString(), objects);
        try {
            if (res && null != idField) {
                Object id = queryForObject("SELECT LAST_INSERT_ID() as id", idField.getType());
                idField.setAccessible(true);
                idField.set(object, id);
            }
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }
        return res;

    }

    public boolean updateById(Object object) {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();

        sql.append("update ");
        sql.append(DbUtils.getTableName(object.getClass()));
        sql.append(" set ");

        int count = 0;
        Field idField = null;
        try {
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                if ("serialVersionUID".equals(fieldName)) {
                    continue;
                }
                // ID
                Id id = field.getAnnotation(Id.class);
                if (null != id) {
                    idField = field;
                    continue;
                }
                Column column = field.getAnnotation(Column.class);
                if (null == column) {
                    continue;
                }
                Object value = DbUtils.getFieldValue(object, fieldName);
                if (value == null) {
                    continue;
                }
                if (0 != count) {
                    sql.append(",");
                }
                sql.append('`');
                sql.append(column.name());
                sql.append("` = ?");
                params.add(value);
                count++;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
        sql.append(" where ");
        if (null == idField) {
            log.error("@Id not find");
            return false;
        }
        Column column = idField.getAnnotation(Column.class);
        if (null == column) {
            log.error("@Id not find");
            return false;
        }
        sql.append('`');
        sql.append(column.name());
        sql.append("` = ?");
        params.add(DbUtils.getFieldValue(object, idField.getName()));

        Object[] objects = params.toArray();

        return 1 == execute(sql.toString(), params.toArray(objects));
    }

    public int execute(String sql, Object... args) {
        long start = System.currentTimeMillis();
        if (log.isDebugEnabled()) {
            log.debug("params:" + Arrays.toString(args));
            log.debug("update sql:" + sql.toString());
        }
        int res = jdbcTemplate.update(sql, args);
        if (log.isDebugEnabled()) {
            log.debug("execute time:" + (System.currentTimeMillis() - start) + "ms");
        }
        return res;
    }


    @Override
    public <T> PagingResult<T> list(StringBuffer sql, Class<T> clazz, Paging paging) {
        PagingResult<T> result = new PagingResult<T>(paging);

        Object[] objects = paging.getParams().toArray();
        long rows = paging.getRows();
        if (0 != rows) {
            String totalSql = "select count(*) ";
            String tempSql = sql.toString().toLowerCase();
            totalSql += sql.substring(tempSql.indexOf("from"), sql.length());
            if (0 < totalSql.indexOf(" group ")) {
                totalSql = "select count(*) from (" + totalSql + ") temp";
            }
            long total = queryForObject(totalSql, Long.class, objects);
            paging.setTotal(total);
            if (0 == total) {
                return result;
            }
        }

        if (paging.isSort()) {
            String[] sorts = paging.getSorts();
            if (null != sorts && 0 < sorts.length) {
                int sortIndex = paging.getSortIndex();
                if (sortIndex >= sorts.length) {
                    sortIndex = sorts.length - 1;
                }
                sql.append(" order by ");
                sql.append('`');
                sql.append(sorts[sortIndex]);
                sql.append('`');
                if (paging.isDesc()) {
                    sql.append(" desc");
                } else {
                    sql.append(" asc");
                }
            }
        }

        if (0 != rows) {
            sql.append(" limit ");
            sql.append(paging.getStart());
            sql.append(",");
            sql.append(rows);
        }

        List<T> list = list(sql.toString(), clazz, objects);
        result.setList(list);

        return result;
    }

    public <T> List<T> list(String sql, Class<T> clazz, Object... args) {
        long start = System.currentTimeMillis();

        if (log.isDebugEnabled()) {
            log.debug("params:" + Arrays.toString(args));
            log.debug("query sql:" + sql);
        }
        List<T> list = null;

        if (null == args || 0 == args.length || args[0] == null) {
            if (DbUtils.isSingleClass(clazz)) {
                list = jdbcTemplate.queryForList(sql, clazz);
            } else {
                list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(clazz));
            }
        } else {
            if (DbUtils.isSingleClass(clazz)) {
                list = jdbcTemplate.queryForList(sql, clazz, args);
            } else {
                list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(clazz), args);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("execute time:" + (System.currentTimeMillis() - start) + "ms");
        }
        return list;
    }

    public <T> T queryForObject(String sql, Class<T> clazz, Object... args) {
        List<T> list = list(sql, clazz, args);
        if (null != list && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public <T> T queryById(Class<T> clazz, Object id) {
        String sql = "select " + DbUtils.getTableColumnAsFieldName(clazz) +
                " from " +
                DbUtils.getTableName(clazz) +
                " where `" +
                DbUtils.getIdColumn(clazz) +
                "` = ?";
        List<T> list = list(sql, clazz, id);
        if (null != list && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }


    public <T> boolean deleteById(Class<T> clazz, Object id) {
        String sb = "delete from " + DbUtils.getTableName(clazz) +
                " where `" +
                DbUtils.getIdColumn(clazz) +
                "` = ?";
        return 1 == execute(sb, id);
    }
}
