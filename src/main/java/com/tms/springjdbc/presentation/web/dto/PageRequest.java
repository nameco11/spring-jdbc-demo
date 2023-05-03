package com.tms.springjdbc.presentation.web.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PageRequest {
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;

    private int page;
    private int size;
    private String sort;
    private String direction;

    public PageRequest() {
        this.page = DEFAULT_PAGE;
        this.size = DEFAULT_SIZE;
    }
    public PageRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public PageRequest(int page, int size, String sort, String direction) {
        this.page = page;
        this.size = size;
        this.sort = sort;
        this.direction = direction;
    }
}
