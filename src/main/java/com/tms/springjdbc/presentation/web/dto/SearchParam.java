package com.tms.springjdbc.presentation.web.dto;

import com.tms.springjdbc.infrastructure.search.Operation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SearchParam {
    private String name;
    private Object value;
    private Operation operation;
}
