package com.preving.restapi.sajpapi.conf;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.sql.DataSource;

@Configuration
@Profile("prod")
public class DataBaseProdConfigApplication {

	@Value("${datasource.db-sajp.jndi-name}")
	private String vigSaludJndiName;

	@Value("${datasource.db-secure.jndi-name}")
	private String optecJndiName;

	@Primary
	@Bean(name = "datasourceApp", destroyMethod="")
	public DataSource gcDataSource() throws Exception {
		JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
		return dataSourceLookup.getDataSource(this.vigSaludJndiName);
	}

	@Bean(name = "datasourceSecure", destroyMethod = "")
	public DataSource secureDataSource() throws Exception {
		JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
		return dataSourceLookup.getDataSource(this.optecJndiName);
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
