package com.preving.restapi.sajpapi.conf;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by felips on 2/6/17.
 */
@Configuration
@Profile("dev")
public class DataBaseDevConfigApplication {

	@Primary
	@Bean(name = "datasourceApp")
	@ConfigurationProperties(prefix = "datasource.db-sajp")
	public DataSource appDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "datasourceSecure")
	@ConfigurationProperties(prefix = "datasource.db-secure")
	public DataSource secureDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean(name = "jdbcTemplateApp")
	public JdbcTemplate jdbcTemplateApp(@Qualifier("datasourceApp") DataSource dsApp) {
		return new JdbcTemplate(dsApp);
	}

	@Bean(name = "namedTemplateApp")
	public NamedParameterJdbcTemplate namedTemplateApp(@Qualifier("datasourceApp") DataSource dsApp) {
		return new NamedParameterJdbcTemplate(dsApp);
	}

	@Bean(name = "jdbcTemplateSecure")
	public JdbcTemplate jdbcTemplate(@Qualifier("datasourceSecure") DataSource dsSecure) {
		return new JdbcTemplate(dsSecure);
	}

}
