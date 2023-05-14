package com.tms.springjdbc.domain.repository;

import com.tms.springjdbc.domain.model.BaseEntity;
import com.tms.springjdbc.infrastructure.search.SearchResult;
import com.tms.springjdbc.infrastructure.search.SelectColumn;
import com.tms.springjdbc.presentation.web.dto.JoinParam;
import com.tms.springjdbc.presentation.web.dto.PageRequest;
import com.tms.springjdbc.presentation.web.dto.SearchParam;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseDao<T extends BaseEntity, ID extends Serializable> {
    T save(T entity);

    void delete(T entity);

    void deleteById(ID id);

    Optional<T> findById(ID id);

    List<T> findAll();

    public <E> SearchResult<E> search(List<SelectColumn> selectColumns, List<SearchParam> searchParams, PageRequest pageRequest);

    public <E> SearchResult<E> searchJoin(List<SelectColumn> selectColumns, List<SearchParam> searchParams, List<JoinParam> join, PageRequest pageRequest, Class<E> responseType);
}
