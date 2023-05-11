package com.tms.springjdbc.application.services;

import com.tms.springjdbc.application.dto.CustomDeptEmpResponse;
import com.tms.springjdbc.domain.model.DeptEmpEntity;
import com.tms.springjdbc.domain.repository.DeptEmpDao;
import com.tms.springjdbc.infrastructure.search.SearchResult;
import com.tms.springjdbc.presentation.web.dto.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class DeptEmpService extends BaseServiceImpl<DeptEmpEntity, Long, DeptEmpDao> {
    public DeptEmpService(DeptEmpDao baseDao) {
        super(baseDao);
    }

    public SearchResult<CustomDeptEmpResponse> findCustomDeptEmp(PageRequest pageRequest) {
        return this.baseDao.findCustomDeptEmp(pageRequest);
    }

    public SearchResult<CustomDeptEmpResponse> join2(PageRequest pageRequest) {
        return this.baseDao.join2(pageRequest);
    }
}
