package com.tms.springjdbc.presentation.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JoinParam {
    private String table;
    private String alias;
    private String column;
    private JoinType joinType;
    public JoinParam(String table) {
        this.table = table;
    }
    public enum JoinType {
        INNER_JOIN("INNER"),
        LEFT_JOIN("LEFT"),
        RIGHT_JOIN("RIGHT"),
        FULL_OUTER_JOIN("FULL OUTER");

        private final String value;

        JoinType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
