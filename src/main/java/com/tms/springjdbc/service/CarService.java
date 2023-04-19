package com.tms.springjdbc.service;

import com.tms.springjdbc.dao.CarDAOex;
import com.tms.springjdbc.domain.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class CarService {
//    @Autowired
//    private CarDAO carDAO;

    @Autowired
    private CarDAOex carDAOex;


    public List<Car> findAll() throws SQLException {
        return carDAOex.findAll();
    }

    public Car findById(int id) throws SQLException {
        return carDAOex.findById(id);
    }

    public void save(Car car) throws SQLException {
        carDAOex.save(car);
    }
}
