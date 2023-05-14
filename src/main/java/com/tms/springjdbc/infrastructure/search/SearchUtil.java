package com.tms.springjdbc.infrastructure.search;

import com.tms.springjdbc.presentation.web.dto.JoinParam;
import com.tms.springjdbc.presentation.web.dto.SearchParam;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class SearchUtil {

    private SearchUtil() {
        // do not call
    }
    public static String generateSql(List<SelectColumn> selectColumns, List<SearchParam> searchParams, List<JoinParam> joinParams) {
        Objects.requireNonNull(joinParams, "joinParams must not be null");
        StringBuilder queryBuilder = new StringBuilder();
        appendSelectClause(queryBuilder, selectColumns);
        appendFromClause(queryBuilder, joinParams.get(0).getTable());
        if (joinParams.size() > 1) {
            appendJoinClauses(queryBuilder, joinParams.subList(1, joinParams.size()));
        }
        appendWhereClause(queryBuilder, searchParams);
        log.debug("Generated SQL: {}", queryBuilder.toString());
        return queryBuilder.toString();
    }


    public static String generateCountSql(List<SearchParam> searchParams, List<JoinParam> joinParams) {
        StringBuilder queryBuilder = new StringBuilder();
        appendCountClause(queryBuilder);
        appendFromClause(queryBuilder, joinParams.get(0).getTable());
        if (joinParams.size() > 1) {
            appendJoinClauses(queryBuilder, joinParams.subList(1, joinParams.size()));
        }
        appendWhereClause(queryBuilder, searchParams);
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

    private static void appendFromClause(StringBuilder queryBuilder, String mainTable) {
        queryBuilder.append("FROM ").append(mainTable).append(" ");
    }

    private static void appendWhereClause(StringBuilder queryBuilder, List<SearchParam> searchParams) {
        if (!searchParams.isEmpty()) {
            queryBuilder.append("WHERE ");
            for (int i = 0; i < searchParams.size(); i++) {
                SearchParam param = searchParams.get(i);
                queryBuilder.append(generateCondition(param)).append(" ");
                if (i < searchParams.size() - 1) {
                    queryBuilder.append("AND ");
                }
            }
        }
    }

    private static void appendJoinClauses(StringBuilder queryBuilder, List<JoinParam> joinParams) {
        joinParams.forEach(joinParam -> {
            String joinType = joinParam.getJoinType().getValue();
            String joinTable = joinParam.getTable();
            String alias = joinParam.getAlias();
            String column = joinParam.getColumn();
            queryBuilder.append(joinType).append(" JOIN ").append(joinTable).append(" AS ").append(alias)
                    .append(" ON ").append(alias).append(".").append(column).append(" = ").append(joinParams.get(0).getAlias()).append(".").append(column).append(" ");
        });
    }

    public static String replaceDotWithUnderscore(String name) {
        return name.replace('.', '_');
    }


    /**
     * Generates a SQL condition for a given SearchParam.
     *
     * @param param The SearchParam for which to generate a condition.
     * @return The generated SQL condition.
     */
    public static String generateCondition(SearchParam param) {
        String name = replaceDotWithUnderscore(param.getName());
        Operation operation = param.getOperation();
        Object value = param.getValue();

        StringBuilder conditionBuilder = new StringBuilder();
        conditionBuilder.append(param.getName());
        switch (operation) {
            case EQUAL:
            case NOT_EQUAL:
            case GREATER_THAN:
            case LESS_THAN:
            case GREATER_THAN_OR_EQUAL_TO:
            case LESS_THAN_OR_EQUAL_TO:
            case LIKE:
                conditionBuilder.append(operationToSql(operation)).append(" :").append(name);
                break;
            case BETWEEN:
                if (value instanceof List && ((List<?>) value).size() == 2) {
                    conditionBuilder.append(" BETWEEN :").append(name).append("From AND :").append(name).append("To");
                } else {
                    throw new IllegalArgumentException("Invalid value for BETWEEN operation: " + value);
                }
                break;
            case OR:
                if (value instanceof List) {
                    conditionBuilder.append(" (");
                    for (int i = 0; i < ((List<?>) value).size(); i++) {
                        conditionBuilder.append(operationToSql(operation)).append(" :").append(name).append(i);
                        if (i < ((List<?>) value).size() - 1) {
                            conditionBuilder.append(" OR ");
                        }
                    }
                    conditionBuilder.append(")");
                } else {
                    throw new IllegalArgumentException("Invalid value for OR operation: " + value);
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid operation: " + operation);
        }

        return conditionBuilder.toString();
    }

    private static String operationToSql(Operation operation) {
        switch (operation) {
            case EQUAL:
                return " = ";
            case NOT_EQUAL:
                return " != ";
            case GREATER_THAN:
                return " > ";
            case LESS_THAN:
                return " < ";
            case GREATER_THAN_OR_EQUAL_TO:
                return " >= ";
            case LESS_THAN_OR_EQUAL_TO:
                return " <= ";
            case LIKE:
                return " LIKE ";
            default:
                return "";
        }
    }

}
