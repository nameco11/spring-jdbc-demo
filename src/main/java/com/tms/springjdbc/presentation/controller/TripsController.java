package com.tms.springjdbc.presentation.controller;

import com.tms.springjdbc.application.services.TripsService;
import com.tms.springjdbc.domain.model.TripsEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trips")
public class TripsController extends BaseController<TripsEntity, Long, TripsService> {
    public TripsController(TripsService service) {
        super(service);
    }
}
