package com.tms.springjdbc.domain.repository;

import com.tms.springjdbc.infrastructure.search.SearchResult;
import com.tms.springjdbc.infrastructure.search.SelectColumn;
import com.tms.springjdbc.presentation.web.dto.JoinParam;
import com.tms.springjdbc.presentation.web.dto.PageRequest;
import com.tms.springjdbc.presentation.web.dto.SearchParam;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseDao<T, ID extends Serializable> {
    T save(T entity);

    void delete(T entity);

    void deleteById(ID id);

    Optional<T> findById(ID id);

    List<T> findAll();

    SearchResult<T> search(List<SelectColumn> selectColumns, List<SearchParam> searchParams, PageRequest pageRequest);

    SearchResult<T> searchJoin(List<SelectColumn> selectColumns, List<SearchParam> searchParams, List<JoinParam> join, PageRequest pageRequest);
}
