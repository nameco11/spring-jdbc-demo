package com.tms.springjdbc.infrastructure.configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.datasource")
@Setter
@Getter
public class Property {

//    @NestedConfigurationProperty
//    private AppProperties appProperties;
//    @Setter
//    @Getter
//    public static class AppProperties {
//        @NestedConfigurationProperty
//        private DataSourceProperties h2;

        @NestedConfigurationProperty
        private DataSourceProperties maria;

        @NestedConfigurationProperty
        private DataSourceProperties clickhouse;

        // Getter and Setter methods for h2 and maria
   // }

    @Setter
    @Getter
    public static class DataSourceProperties {
        @NestedConfigurationProperty
        private HikariProperties hikari;

        private String url;
        private String type;
        private String driverClassName;
        private String username;
        private String password;

        // Getter and Setter methods for all the properties
    }

    @Setter
    @Getter
    public static class HikariProperties {
        private String poolName;
        private int maximumPoolSize;
        private boolean autoCommit;
        private int minimumIdle;
        private int idleTimeout;
        private int maxLifetime;
        private int connectionTimeout;

        // Getter and Setter methods for all the properties
    }
}

