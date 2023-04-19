package com.tms.springjdbc.dao;

import com.tms.springjdbc.domain.Car;
import com.tms.springjdbc.mapper.ColumnMapper;
import com.tms.springjdbc.mapper.DefaultColumnMapper;
import com.tms.springjdbc.utils.ReflectionUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarDAO {
//    private final JdbcTemplate jdbcTemplate;
//
//    public CarDAO(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }

    private JdbcTemplate jdbcTemplate;
    private ColumnMapper columnMapper;

    public CarDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.columnMapper = new DefaultColumnMapper();
    }

    private final RowMapper<Car> CAR_ROW_MAPPER = (resultSet, rowNum) -> {
        return ReflectionUtil.mapRowToEntity(resultSet, Car.class, columnMapper);
    };

    public List<Car> getAllCars() {
        String sql = "SELECT c.id, c.name, c.brand, c.groupid, c.color, c.type, c.quantity, "
                + "g.id as group_id, g.name as group_name, g.totalquantity "
                + "FROM Car c "
                + "JOIN Group g ON c.groupid = g.id";
        return jdbcTemplate.query(sql, CAR_ROW_MAPPER);
    }

    public Car getCarById(int id) {
        String sql = "SELECT c.id, c.name, c.brand, c.groupid, c.color, c.type, c.quantity, "
                + "g.id as group_id, g.name as group_name, g.totalquantity "
                + "FROM Car c "
                + "JOIN Group g ON c.groupid = g.id "
                + "WHERE c.id = ?";
        return jdbcTemplate.queryForObject(sql, CAR_ROW_MAPPER, id);
    }

    public int createCar(Car car) {
        String sql = "INSERT INTO Car (name, brand, groupid, color, type, quantity) VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, car.getName(), car.getBrand(), car.getGroupId(), car.getColor(), car.getType(), car.getQuantity());
    }

    public int updateCar(Car car) {
        String sql = "UPDATE Car SET name = ?, brand = ?, groupid = ?, color = ?, type = ?, quantity = ? WHERE id = ?";
        return jdbcTemplate.update(sql, car.getName(), car.getBrand(), car.getGroupId(), car.getColor(), car.getType(), car.getQuantity(), car.getId());
    }

    public int deleteCar(int id) {
        String sql = "DELETE FROM Car WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

//    private static final RowMapper<Car> CAR_ROW_MAPPER = (resultSet, rowNum) -> {
//        Car car = new Car();
//        car.setId(resultSet.getInt("id"));
//        car.setName(resultSet.getString("name"));
//        car.setBrand(resultSet.getString("brand"));
//        car.setGroupId(resultSet.getInt("groupid"));
//        car.setColor(resultSet.getString("color"));
//        car.setType(resultSet.getString("type"));
//        car.setQuantity(resultSet.getInt("quantity"));
//
//        Group group = new Group();
//        group.setId(resultSet.getInt("group_id"));
//        group.setName(resultSet.getString("group_name"));
//        group.setTotalAmount(resultSet.getInt("totalAmount"));
//        car.setGroup(group);
//
//        return car;
//    };
//
//    public List<Car> getAllCars() {
//        String sql = "SELECT c.id, c.name, c.brand, c.groupid, c.color, c.type, c.quantity, "
//                + "g.id as group_id, g.name as group_name, g.totalquantity "
//                + "FROM Car c "
//                + "JOIN Group g ON c.groupid = g.id";
//        return jdbcTemplate.query(sql, CAR_ROW_MAPPER);
//    }
}
