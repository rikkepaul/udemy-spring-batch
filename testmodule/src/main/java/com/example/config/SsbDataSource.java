package com.example.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class SsbDataSource {

    @Bean
    public DataSource customDataSource() {
        DataSourceBuilder dataSource = DataSourceBuilder.create();
        dataSource.url("jdbc:mysql://localhost:3306/ssb");
        dataSource.username("root");
        dataSource.password("new_password");
        return dataSource.build();
    }
    @Bean
    public NamedParameterJdbcTemplate customJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
