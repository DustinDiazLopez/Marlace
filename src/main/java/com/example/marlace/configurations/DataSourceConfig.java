package com.example.marlace.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(env.getProperty("spring.datasource.url"));
        dataSourceBuilder.driverClassName(env.getProperty("spring.datasource.driverClassName"));
        dataSourceBuilder.username(env.getProperty("marlace.db.user"));
        dataSourceBuilder.password(env.getProperty("marlace.db.password"));
        return dataSourceBuilder.build();
    }
}
