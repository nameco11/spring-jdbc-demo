package com.tms.springjdbc.domain.repository;

import com.tms.springjdbc.application.dto.CustomDeptEmpResponse;
import com.tms.springjdbc.domain.model.DeptEmpEntity;
import com.tms.springjdbc.infrastructure.search.SearchResult;
import com.tms.springjdbc.presentation.web.dto.PageRequest;
import com.tms.springjdbc.presentation.web.dto.SearchParam;

import java.util.List;

public interface DeptEmpDao extends BaseDao<DeptEmpEntity, Long> {
    SearchResult<CustomDeptEmpResponse> findCustomDeptEmp(PageRequest pageRequest);

    SearchResult<CustomDeptEmpResponse> findCustomDeptEmp(List<SearchParam> searchParams, PageRequest pageRequest);

    SearchResult<CustomDeptEmpResponse> join2(PageRequest pageRequest);
}
