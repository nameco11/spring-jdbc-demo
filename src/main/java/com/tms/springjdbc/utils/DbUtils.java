package com.tms.springjdbc.utils;

import com.tms.springjdbc.advantage.annotation.Column;
import com.tms.springjdbc.advantage.annotation.Id;
import com.tms.springjdbc.advantage.annotation.Table;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class DbUtils {
    private final static Log log = LogFactory.getLog(DbUtils.class);
    private static final Set<Class<?>> classes = new HashSet<Class<?>>();

    static {
        classes.add(java.lang.Integer.class);
        classes.add(java.lang.Long.class);
        classes.add(java.lang.Short.class);

        classes.add(java.lang.Double.class);
        classes.add(java.lang.String.class);
        classes.add(java.math.BigDecimal.class);

        classes.add(java.sql.Time.class);
        classes.add(java.util.Date.class);
        classes.add(java.sql.Timestamp.class);

    }

    public static <T> boolean isSingleClass(final Class<T> clazz) {
        return classes.contains(clazz);
    }

    public static <T> Object getFieldValue(final Object object, final String fieldName) {
        String up1 = firstCharToUpperCase(fieldName);
        Object value = null;
        try {
            Method getter = object.getClass().getMethod("get" + up1);
            if (getter == null) {
                getter = object.getClass().getMethod("is" + up1);
            }
            value = getter.invoke(object);
        } catch (Exception e) {
            log.error(fieldName + " 'get' or 'is' Method doesn't exist!", e);
        }
        return value;
    }

    public static String firstCharToUpperCase(final String string) {
        if (StringUtils.hasText(string)) {
            int length = string.length();
            String first = string.substring(0, 1);
            first = first.toUpperCase();
            if (1 == length) {
                return first;
            }

            String other = string.substring(1, length);
            return first + other;
        }
        return string;
    }

    public static <T> String getTableName(final Class<T> clazz) {
        Table table = clazz.getAnnotation(Table.class);
        if (null == table) {
            throw new RuntimeException("not find table name...");
        }
        StringBuilder tableName = new StringBuilder("`");
        String database = table.database();
        if (StringUtils.hasText(database)) {
            tableName.append(database);
            tableName.append("`.`");
        }
        tableName.append(table.name());
        tableName.append("`");
        return tableName.toString();
    }

    public static <T> String getIdColumn(final Class<T> clazz) {
        Field
                [] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            if ("serialVersionUID".equals(fieldName)) {
                continue;
            }
            Id id = field.getAnnotation(Id.class);
            if (null != id) {
                Column column = field.getAnnotation(Column.class);
                if (null != column) {
                    return column.name();
                }
            }
        }
        throw new RuntimeException("Id Conlmn not find");
    }

    public static <T> String getTableColumnAsFieldName(final Class<T> clazz) {
        StringBuilder stringBuffer = new StringBuilder();
        Field[] fields = clazz.getDeclaredFields();
        int i = 0;
        for (Field field : fields) {
            String fieldName = field.getName();
            if ("serialVersionUID".equals(fieldName)) {
                continue;
            }
            Column column = field.getAnnotation(Column.class);
            if (null != column) {
                if (0 != i) {
                    stringBuffer.append(',');
                }
                stringBuffer.append('`');
                stringBuffer.append(column.name());
                stringBuffer.append("` as `");
                stringBuffer.append(fieldName);
                stringBuffer.append("`");
                ++i;
            }
        }
        return stringBuffer.toString();
    }

    public static <T> String getSelectSql(final Class<T> clazz) {
        return "select " + DbUtils.getTableColumnAsFieldName(clazz) +
                " from " +
                DbUtils.getTableName(clazz);

    }
}
