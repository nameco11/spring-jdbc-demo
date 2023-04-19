package com.tms.springjdbc.dao;

import com.tms.springjdbc.domain.Car;
import com.tms.springjdbc.mapper.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class CarDAOex extends BaseDAO<Car> {
    public CarDAOex(DataSource dataSource, ConnectionManager connectionManager) {
        super(dataSource, connectionManager);
    }

    @Override
    protected String getTableName() {
        return "cars";
    }

    @Override
    protected RowMapper<Car> getRowMapper() {
        return resultSet -> {
            Car car = new Car();
            car.setId(resultSet.getInt("id"));
            return car;
        };
    }

    @Override
    protected String getInsertSql() {
        return "INSERT INTO cars (type, quantity) VALUES (?, ?)";
    }

    @Override
    protected void setInsertParameters(PreparedStatement preparedStatement, Car entity) throws SQLException {
        preparedStatement.setString(1, entity.getType());
        preparedStatement.setInt(2, entity.getQuantity());
    }

//    @Override
//    protected MapSqlParameterSource getParameters(Car entity) {
//        MapSqlParameterSource params = new MapSqlParameterSource();
//        params.addValue("id", entity.getId());
//        params.addValue("price", entity.getPrice());
//        return params;
//    }
//
//    @Override
//    protected RowMapper<Car> getRowMapper() {
//        return new RowMapper<Car>() {
//            public Car mapRow(ResultSet rs, int rowNum) throws SQLException {
//                long id = rs.getLong("id");
//                double price = rs.getDouble("price");
//                return new Car(id, make, model, year, price);
//            }
//        };
//    }
}
