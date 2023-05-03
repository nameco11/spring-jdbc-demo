package com.tms.springjdbc.infrastructure.dao.maria;

import com.tms.springjdbc.domain.model.EmployeesEntity;
import com.tms.springjdbc.domain.repository.EmployeesDao;
import com.tms.springjdbc.infrastructure.dao.port.BaseDaoImpl;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeesDaoImpl extends BaseDaoImpl<EmployeesEntity,Long> implements EmployeesDao {
    public EmployeesDaoImpl(NamedParameterJdbcTemplate mariaJdbcTemplate) {
        super(mariaJdbcTemplate, EmployeesEntity.class);
    }
}
