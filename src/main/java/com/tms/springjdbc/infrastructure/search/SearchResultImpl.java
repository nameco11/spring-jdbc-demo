package com.tms.springjdbc.infrastructure.search;

import java.util.List;

public class SearchResultImpl<E> implements SearchResult<E> {
    private final List<E> content;
    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;

    public SearchResultImpl(List<E> content, int page, int size, long totalElements, int totalPages) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    @Override
    public List<E> getContent() {
        return content;
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public long getTotalElements() {
        return totalElements;
    }

    @Override
    public int getTotalPages() {
        return totalPages;
    }
}
