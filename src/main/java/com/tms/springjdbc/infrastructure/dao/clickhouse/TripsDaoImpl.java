package com.tms.springjdbc.infrastructure.dao.clickhouse;

import com.tms.springjdbc.domain.model.TripsEntity;
import com.tms.springjdbc.domain.repository.TripsDao;
import com.tms.springjdbc.infrastructure.dao.port.BaseDaoImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TripsDaoImpl extends BaseDaoImpl<TripsEntity,Long> implements TripsDao {
    public TripsDaoImpl(@Qualifier("clickhouseJdbcTemplate") NamedParameterJdbcTemplate clickhouseJdbcTemplate) {
        super(clickhouseJdbcTemplate, TripsEntity.class);
    }

//    public TripsDaoImpl(@Qualifier("clickhouseJdbcTemplate") NamedParameterJdbcTemplate clickhouseJdbcTemplate, Class<TripsEntity> entityClass) {
//        super(clickhouseJdbcTemplate, entityClass);
//    }
}
