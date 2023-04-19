package com.tms.springjdbc.dao;

import com.tms.springjdbc.configuration.AppProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class ConnectionManager {

//    @Value("${app.datasource.maria.url}")
//    private String dsUrl;
//    @Value("${app.datasource.maria.username}")
//    private String dsUserName;
//    @Value("${app.datasource.maria.password}")
//    private String dsPassword;

    private final HikariDataSource dataSource;

    public ConnectionManager(AppProperties appProperties) {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(appProperties.getDatasource().getH2().getUrl());
        config.setUsername(appProperties.getDatasource().getH2().getUsername());
        config.setPassword(appProperties.getDatasource().getH2().getPassword());

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setIdleTimeout(30000);
        config.setConnectionTimeout(2000);
        config.setPoolName("zaConnectionPool");

        dataSource = new HikariDataSource(config);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }

//    public class ConnectionManager implements DisposableBean {
//
//        @Override
//        public void destroy() {
//            close();
//        }
//    }
}
