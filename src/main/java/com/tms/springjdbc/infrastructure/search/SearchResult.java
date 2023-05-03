package com.tms.springjdbc.infrastructure.search;

import java.util.List;

public interface SearchResult<T> {
    List<T> getContent();
    int getPage();
    int getSize();
    long getTotalElements();
    int getTotalPages();
}
