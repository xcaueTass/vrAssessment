package br.com.miniautorizator.card.configs;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import br.com.miniautorizator.configs.ApplicationProperties;

@Configuration
public class DataSourceConfig {

	@Autowired
	ApplicationProperties appProp;

	@Bean
	public DataSource getDataSource() {
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
