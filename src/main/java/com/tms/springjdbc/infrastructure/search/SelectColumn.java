package com.tms.springjdbc.infrastructure.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SelectColumn {
    private String column;
    private String alias;
}
