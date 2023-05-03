package com.tms.springjdbc.application.services;

import com.tms.springjdbc.domain.repository.BaseDao;
import com.tms.springjdbc.infrastructure.search.SearchResult;
import com.tms.springjdbc.infrastructure.search.SelectColumn;
import com.tms.springjdbc.presentation.web.dto.JoinParam;
import com.tms.springjdbc.presentation.web.dto.PageRequest;
import com.tms.springjdbc.presentation.web.dto.SearchParam;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BaseServiceImpl<T, ID extends Serializable, D extends BaseDao<T, ID>> implements BaseService<T, ID> {
    protected final D baseDao;

    public BaseServiceImpl(D baseDao) {
        this.baseDao = baseDao;
    }
    @Override
    public T save(T entity) {
        return baseDao.save(entity);
    }

    @Override
    public void delete(T entity) {
        baseDao.delete(entity);
    }

    @Override
    public void deleteById(ID id) {
        baseDao.deleteById(id);
    }
    @Override
    public Optional<T> findById(ID id) {
        return baseDao.findById(id);
    }

    @Override
    public List<T> findAll() {
        return baseDao.findAll();
    }

    @Override
    public SearchResult<T> search(List<SelectColumn> selectColumns ,List<SearchParam> searchParams, PageRequest pageRequest) {
        return baseDao.search(selectColumns, searchParams, pageRequest);
    }

    @Override
    public SearchResult<T> search(List<SearchParam> searchParams, PageRequest pageRequest) {
        return baseDao.search(new ArrayList<>(), searchParams, pageRequest);
    }

    @Override
    public SearchResult<T> searchJoin(List<SelectColumn> selectColumns ,List<SearchParam> searchParams, List<JoinParam> join, PageRequest pageRequest) {
        return baseDao.searchJoin(selectColumns, searchParams, join, pageRequest);
    }

    @Override
    public SearchResult<T> searchJoin(List<SearchParam> searchParams, List<JoinParam> join, PageRequest pageRequest) {
        return baseDao.searchJoin(new ArrayList<>(), searchParams, join, pageRequest);
    }
}
