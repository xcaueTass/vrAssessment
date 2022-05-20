package br.com.miniautorizator.card.configs;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource getDataSource(ApplicationProperties appProp) {
        HikariConfig config = new HikariConfig();
        config.setMinimumIdle(appProp.getMinumunIdle());
        config.setMaximumPoolSize(appProp.getMaximunPoolSize());
        config.setPoolName(appProp.getPoolName());
        config.setConnectionTimeout(appProp.getConnectionTimeout());
        config.setIdleTimeout(appProp.getIdleTimeout());
        config.setMaxLifetime(appProp.getMaxLifetime());
        config.setJdbcUrl(appProp.getUrl());
        config.setDriverClassName(appProp.getDriverClassName());
        config.setUsername(appProp.getUsername());
        config.setPassword(appProp.getPassword());

        return new HikariDataSource(config);
    }

}
