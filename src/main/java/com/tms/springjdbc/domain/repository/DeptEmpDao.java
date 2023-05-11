package com.tms.springjdbc.domain.repository;

import com.tms.springjdbc.application.dto.CustomDeptEmpResponse;
import com.tms.springjdbc.domain.model.DeptEmpEntity;
import com.tms.springjdbc.infrastructure.search.SearchResult;
import com.tms.springjdbc.presentation.web.dto.PageRequest;

public interface DeptEmpDao extends BaseDao<DeptEmpEntity, Long> {
    SearchResult<CustomDeptEmpResponse> findCustomDeptEmp(PageRequest pageRequest);

    SearchResult<CustomDeptEmpResponse> join2(PageRequest pageRequest);
}
