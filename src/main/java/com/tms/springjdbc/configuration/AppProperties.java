package com.tms.springjdbc.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app")
@Getter
@Setter
public class AppProperties {
    private DataSource datasource;


    @Setter
    @Getter
    public static class DataSource {
        private Maria maria;
        private H2 h2;
    }

    @Setter
    @Getter
    public static class H2 {
        private String url;
        private String type;
        private String driverClassName;
        private String username;
        private String password;
        private Hikari hikari;
    }

    @Setter
    @Getter
    public static class Maria {
        private String url;
        private String type;
        private String driverClassName;
        private String username;
        private String password;
        private Hikari hikari;
    }

    @Getter
    @Setter
    public static class Hikari {
        private String poolName;
        private int maximumPoolSize;
        private boolean autoCommit;
        private int minimumIdle;
    }
}
