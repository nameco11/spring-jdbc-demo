package com.tms.springjdbc.application.services;

import com.tms.springjdbc.domain.model.TripsEntity;
import com.tms.springjdbc.domain.repository.TripsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TripsService extends BaseServiceImpl<TripsEntity, Long, TripsDao> {
    @Autowired
    public TripsService(TripsDao baseDao) {
        super(baseDao);
    }
}
