package com.tms.springjdbc.infrastructure.dao.maria;

import com.tms.springjdbc.domain.model.DeptEmpEntity;
import com.tms.springjdbc.domain.repository.DeptEmpDao;
import com.tms.springjdbc.infrastructure.dao.port.BaseDaoImpl;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DeptEmpDaoImpl extends BaseDaoImpl<DeptEmpEntity,Long> implements DeptEmpDao {
    public DeptEmpDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(namedParameterJdbcTemplate, DeptEmpEntity.class);
    }
}
