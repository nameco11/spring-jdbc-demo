package com.tms.springjdbc.application.services;

import com.tms.springjdbc.domain.model.EmployeesEntity;
import com.tms.springjdbc.domain.repository.EmployeesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeesService extends BaseServiceImpl<EmployeesEntity, Long, EmployeesDao> {
    @Autowired
    public EmployeesService(EmployeesDao baseDao) {
        super(baseDao);
    }

}
