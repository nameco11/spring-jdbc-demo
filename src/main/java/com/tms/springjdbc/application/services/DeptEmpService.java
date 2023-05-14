package com.tms.springjdbc.application.services;

import com.tms.springjdbc.application.dto.CustomDeptEmpResponse;
import com.tms.springjdbc.domain.model.DeptEmpEntity;
import com.tms.springjdbc.domain.repository.DeptEmpDao;
import com.tms.springjdbc.infrastructure.search.SearchResult;
import com.tms.springjdbc.presentation.web.dto.PageRequest;
import com.tms.springjdbc.presentation.web.dto.SearchParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptEmpService extends BaseServiceImpl<DeptEmpEntity, Long, DeptEmpDao> {
    public DeptEmpService(DeptEmpDao baseDao) {
        super(baseDao);
    }

    public SearchResult<CustomDeptEmpResponse> findCustomDeptEmp(PageRequest pageRequest) {
        return baseDao.findCustomDeptEmp(pageRequest);
    }

    public SearchResult<CustomDeptEmpResponse> findCustomDeptEmp(List<SearchParam> searchParams, PageRequest pageRequest) {
        return baseDao.findCustomDeptEmp(searchParams, pageRequest);
    }

    public SearchResult<CustomDeptEmpResponse> join2(PageRequest pageRequest) {
        return baseDao.join2(pageRequest);
    }
}
