package com.tms.springjdbc.configuration;

import com.tms.springjdbc.dao.ConnectionManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class BeanEx {

    private AppProperties appProperties;

    public BeanEx(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Bean
    public ConnectionManager connectionManager(AppProperties appProperties) {
        return new ConnectionManager(appProperties);
    }

//    @Bean
//    public DataSource dataSource() {
////        DriverManagerDataSource dataSource = new DriverManagerDataSource();
////        dataSource.setDriverClassName(appProperties.getDatasource().getH2().getDriverClassName());
////        dataSource.setUrl(appProperties.getDatasource().getH2().getUrl());
////        dataSource.setUsername(appProperties.getDatasource().getH2().getUsername());
////        dataSource.setPassword(appProperties.getDatasource().getH2().getPassword());
////
////        return dataSource;
//        HikariConfig config = new HikariConfig();
//        config.setJdbcUrl(appProperties.getDatasource().getH2().getUrl());
//        config.setUsername(appProperties.getDatasource().getH2().getUsername());
//        config.setPassword(appProperties.getDatasource().getH2().getPassword());
//
//        config.setMaximumPoolSize(10);
//        config.setMinimumIdle(5);
//        config.setIdleTimeout(30000);
//        config.setConnectionTimeout(2000);
//        config.setPoolName("zaConnectionPool");
//
//        return new HikariDataSource(config);
//    }
//
//    @Bean
//    public DataSourceTransactionManager transactionManager() {
//        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
//        transactionManager.setDataSource(dataSource());
//        return transactionManager;
//    }
//
//    @Bean
//    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
//        return new NamedParameterJdbcTemplate(dataSource());
//    }
}
