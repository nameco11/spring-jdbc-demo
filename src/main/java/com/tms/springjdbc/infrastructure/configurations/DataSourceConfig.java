package com.tms.springjdbc.infrastructure.configurations;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(Property.class)
public class DataSourceConfig {

    private final Property property;

    public DataSourceConfig(Property property) {
        this.property = property;
    }


    @Bean
    @Primary
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(property.getMaria().getUrl());
        config.setUsername(property.getMaria().getUsername());
        config.setPassword(property.getMaria().getPassword());
        config.setDriverClassName(property.getMaria().getDriverClassName());
        //  configure HikariCP settings
        config.setMaximumPoolSize(property.getMaria().getHikari().getMaximumPoolSize());
        config.setMinimumIdle(property.getMaria().getHikari().getMinimumIdle());
        config.setConnectionTimeout(property.getMaria().getHikari().getConnectionTimeout());
        config.setIdleTimeout(property.getMaria().getHikari().getIdleTimeout());
        config.setMaxLifetime(property.getMaria().getHikari().getMaxLifetime());
        config.setPoolName("springHikariCP");
        return new HikariDataSource(config);
    }

    @Bean
    @Primary
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

//    @Bean
//    public NamedParameterJdbcTemplate mysqlJdbcTemplate(@Qualifier("mysqlDataSource") DataSource mysqlDataSource) {
//        return new NamedParameterJdbcTemplate(mysqlDataSource);
//

    @Bean
    @Qualifier("clickhouse")
    public DataSource clickhouseDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(property.getClickhouse().getUrl());
        config.setUsername(property.getClickhouse().getUsername());
        config.setPassword(property.getClickhouse().getPassword());
        config.setDriverClassName(property.getClickhouse().getDriverClassName());
        //  configure HikariCP settings
        config.setMaximumPoolSize(property.getClickhouse().getHikari().getMaximumPoolSize());
        config.setMinimumIdle(property.getClickhouse().getHikari().getMinimumIdle());
        config.setConnectionTimeout(property.getClickhouse().getHikari().getConnectionTimeout());
        config.setIdleTimeout(property.getClickhouse().getHikari().getIdleTimeout());
        config.setMaxLifetime(property.getClickhouse().getHikari().getMaxLifetime());
        config.setPoolName("clickhouseHikariCP");
        return new HikariDataSource(config);
    }

    @Bean
    public NamedParameterJdbcTemplate clickhouseJdbcTemplate(@Qualifier("clickhouse") DataSource clickhouseDataSource) {
        return new NamedParameterJdbcTemplate(clickhouseDataSource);
    }
}
