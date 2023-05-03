package com.tms.springjdbc.application.services;

import com.tms.springjdbc.domain.model.DeptEmpEntity;
import com.tms.springjdbc.domain.repository.DeptEmpDao;
import org.springframework.stereotype.Service;

@Service
public class DeptEmpService extends BaseServiceImpl<DeptEmpEntity, Long, DeptEmpDao> {
    public DeptEmpService(DeptEmpDao baseDao) {
        super(baseDao);
    }

}
