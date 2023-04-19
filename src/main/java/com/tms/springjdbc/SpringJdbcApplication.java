package com.tms.springjdbc;

import com.tms.springjdbc.domain.Car;
import com.tms.springjdbc.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringJdbcApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringJdbcApplication.class, args);
    }


    @Autowired
    CarService carService;

    @Override
    public void run(String... args) throws Exception {
        carService.save(Car.builder().type("1").quantity(100).build());
    }
}
