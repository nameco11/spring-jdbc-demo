package com.tms.springjdbc.infrastructure.search;

import com.tms.springjdbc.presentation.web.dto.JoinParam;
import com.tms.springjdbc.presentation.web.dto.SearchParam;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class SearchUtil {

    private SearchUtil() {
        // do not call
    }
    public static String generateSql(List<SelectColumn> selectColumns, List<SearchParam> searchParams, List<JoinParam> joinParams) {
        StringBuilder queryBuilder = new StringBuilder();
        appendSelectClause(queryBuilder, selectColumns);
        appendFromClause(queryBuilder, joinParams);
        appendWhereClause(queryBuilder, searchParams);
        appendJoinClauses(queryBuilder, joinParams);
        log.debug("Generated SQL: {}", queryBuilder.toString());
        return queryBuilder.toString();
    }

    public static String generateCountSql(List<SearchParam> searchParams, List<JoinParam> joinParams) {
        StringBuilder queryBuilder = new StringBuilder();
        appendCountClause(queryBuilder);
        appendFromClause(queryBuilder, joinParams);
        appendWhereClause(queryBuilder, searchParams);
        appendJoinClauses(queryBuilder, joinParams);
        log.debug("Generated COUNT SQL: {}", queryBuilder.toString());
        return queryBuilder.toString();
    }

    private static void appendCountClause(StringBuilder queryBuilder) {
        queryBuilder.append("SELECT COUNT(*) ");
    }

    private static void appendSelectClause(StringBuilder queryBuilder, List<SelectColumn> selectColumns) {
        if (!selectColumns.isEmpty()) {
            String columns = selectColumns.stream()
                    .map(SelectColumn::getColumn)
                    .collect(Collectors.joining(", "));
            queryBuilder.append("SELECT ").append(columns).append(" ");
        } else {
            queryBuilder.append("SELECT * ");
        }
    }

    private static void appendFromClause(StringBuilder queryBuilder, List<JoinParam> joinParams) {
        queryBuilder.append("FROM ").append(joinParams.get(0).getTable()).append(" ");
    }

    private static void appendWhereClause(StringBuilder queryBuilder, List<SearchParam> searchParams) {
        if (!searchParams.isEmpty()) {
            queryBuilder.append("WHERE ");
            searchParams.forEach(param -> {
                queryBuilder.append(generateCondition(param)).append(" ");
                queryBuilder.append("AND ");
            });
            queryBuilder.delete(queryBuilder.length() - 5, queryBuilder.length()); // Remove the trailing "AND"
        }
    }

    private static void appendJoinClauses(StringBuilder queryBuilder, List<JoinParam> joinParams) {
        joinParams.stream().skip(1).forEach(joinParam -> {
            String joinType = joinParam.getJoinType().toString();
            String joinTable = joinParam.getTable();
            String alias = joinParam.getAlias();
            String onClause = joinParam.getOnClause();

            queryBuilder.append(joinType).append(" JOIN ").append(joinTable).append(" AS ").append(alias)
                    .append(" ON ").append(onClause).append(" ");
        });
    }

    public static String generateCondition(SearchParam param) {
        String name = param.getName();
        Operation operation = param.getOperation();
        Object value = param.getValue();

        StringBuilder conditionBuilder = new StringBuilder();
        conditionBuilder.append(name);

        switch (operation) {
            case EQUAL:
                conditionBuilder.append(" = :").append(value);
                break;
            case NOT_EQUAL:
                conditionBuilder.append(" != :").append(value);
                break;
            case GREATER_THAN:
                conditionBuilder.append(" > :").append(value);
                break;
            case LESS_THAN:
                conditionBuilder.append(" < :").append(value);
                break;
            case GREATER_THAN_OR_EQUAL_TO:
                conditionBuilder.append(" >= :").append(value);
                break;
            case LESS_THAN_OR_EQUAL_TO:
                conditionBuilder.append(" <= :").append(value);
                break;
            case BETWEEN:
                if (value instanceof List && ((List<?>) value).size() == 2) {
                    List<?> rangeValues = (List<?>) value;
                    conditionBuilder.append(" BETWEEN :").append(rangeValues.get(0)).append("1 AND :").append(rangeValues.get(1)).append("2");
                } else {
                    throw new IllegalArgumentException("Invalid value for BETWEEN operation: " + value);
                }
                break;
            case LIKE:
                conditionBuilder.append(" LIKE :").append(value);
                break;
            default:
                throw new IllegalArgumentException("Invalid operation: " + operation);
        }

        return conditionBuilder.toString();
    }

//    public static String buildSearchQuery(String baseQuery, List<SearchParam> searchParams, String sort, String order) {
//        StringBuilder queryBuilder = new StringBuilder(baseQuery);
//
//        for (SearchParam param : searchParams) {
//            queryBuilder.append(" AND ").append(param.getName()).append(" ").append(param.getOperation().getValue());
//
//            if (param.getOperation() == Operation.BETWEEN) {
//                queryBuilder.append(" :").append(param.getName()).append("1 AND :").append(param.getName()).append("2");
//            } else {
//                queryBuilder.append(" :").append(param.getName());
//            }
//        }
//
//        if (sort != null && !sort.isEmpty()) {
//            queryBuilder.append(" ORDER BY ").append(sort);
//            if (order != null && !order.isEmpty()) {
//                queryBuilder.append(" ").append(order);
//            }
//        }
//
//        return queryBuilder.toString();
//    }
//
//    public static String generateSql(List<SearchParam> searchParams, List<JoinParam> joinParams) {
//        StringBuilder sqlBuilder = new StringBuilder();
//        sqlBuilder.append("SELECT * FROM your_table");
//
//        if (!joinParams.isEmpty()) {
//            for (JoinParam joinParam : joinParams) {
//                String joinClause = generateJoinClause(joinParam);
//                sqlBuilder.append(" ").append(joinClause);
//            }
//        }
//
//        if (!searchParams.isEmpty()) {
//            StringBuilder conditionBuilder = new StringBuilder();
//            conditionBuilder.append(" WHERE ");
//
//            for (int i = 0; i < searchParams.size(); i++) {
//                SearchParam param = searchParams.get(i);
//                String condition = generateCondition(param);
//                if (i > 0) {
//                    conditionBuilder.append(" AND ");
//                }
//                conditionBuilder.append(condition);
//            }
//
//            sqlBuilder.append(conditionBuilder);
//        }
//
//        return sqlBuilder.toString();
//    }
//
//    private static String generateJoinClause(JoinParam joinParam) {
//        String joinType = joinParam.getJoinType().name();
//        String table = joinParam.getTable();
//        String alias = joinParam.getAlias();
//        String onClause = joinParam.getOnClause();
//
//        return String.format("%s %s AS %s ON %s", joinType, table, alias, onClause);
//    }
//
//    private static String generateCondition(SearchParam param) {
//        String name = param.getName();
//        Operation operation = param.getOperation();
//        Object value = param.getValue();
//
//        StringBuilder conditionBuilder = new StringBuilder();
//        conditionBuilder.append(name);
//
//        switch (operation) {
//            case EQUAL:
//                conditionBuilder.append(" = ").append(value);
//                break;
//            case NOT_EQUAL:
//                conditionBuilder.append(" != ").append(value);
//                break;
//            case GREATER_THAN:
//                conditionBuilder.append(" > ").append(value);
//                break;
//            case LESS_THAN:
//                conditionBuilder.append(" < ").append(value);
//                break;
//            case GREATER_THAN_OR_EQUAL_TO:
//                conditionBuilder.append(" >= ").append(value);
//                break;
//            case LESS_THAN_OR_EQUAL_TO:
//                conditionBuilder.append(" <= ").append(value);
//                break;
//            case BETWEEN:
//                if (value instanceof List && ((List<?>) value).size() == 2) {
//                    List<?> rangeValues = (List<?>) value;
//                    Object minValue = rangeValues.get(0);
//                    Object maxValue = rangeValues.get(1);
//                    conditionBuilder.append(" BETWEEN ").append(minValue).append(" AND ").append(maxValue);
//                } else {
//                    throw new IllegalArgumentException("Invalid value for BETWEEN operation: " + value);
//                }
//                break;
//            case LIKE:
//                conditionBuilder.append(" LIKE '%").append(value).append("%'");
//                break;
//            default:
//                throw new IllegalArgumentException("Invalid operation: " + operation);
//        }
//
//        return conditionBuilder.toString();
//    }
//        public static String buildSqlQuery(List<SearchParam> searchParams, String tableName) {
//            String selectClause = "SELECT * FROM " + tableName + " ";
//            String whereClause = buildWhereClause(searchParams);
//
//            return selectClause + whereClause;
//        }
//
//        private static String buildWhereClause(List<SearchParam> searchParams) {
//            if (searchParams == null || searchParams.isEmpty()) {
//                return "";
//            }
//
//            StringJoiner joiner = new StringJoiner(" AND ");
//            for (SearchParam param : searchParams) {
//                String column = param.getName();
//                String value = param.getValue();
//                String operator = param.getOperation().getSqlOperator();
//
//                joiner.add(column + " " + operator + " " + value);
//            }
//
//            return "WHERE " + joiner.toString();
//        }
}
