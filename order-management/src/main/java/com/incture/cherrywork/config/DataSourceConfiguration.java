package com.incture.cherrywork.config;

import javax.sql.DataSource;


import org.springframework.boot.jdbc.DataSourceBuilder;

import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.config.java.ServiceScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.zaxxer.hikari.HikariDataSource;

import io.pivotal.cfenv.core.CfCredentials;
import io.pivotal.cfenv.jdbc.CfJdbcEnv;


@Configuration
@Profile("cloud")
@ServiceScan
public class DataSourceConfiguration extends AbstractCloudConfig {

	@Bean
	@Primary
	public DataSource datasource() {
		CfJdbcEnv cfJdbcEnv = new CfJdbcEnv();
		CfCredentials cfCredentials = cfJdbcEnv.findCredentialsByTag("hana");
		return DataSourceBuilder.create().type(HikariDataSource.class)
				.driverClassName(com.sap.db.jdbc.Driver.class.getName()).url(cfCredentials.getUri())
				.username(cfCredentials.getUsername()).password(cfCredentials.getPassword()).build();
	}
	
//    @Bean
//    ServletRegistrationBean h2servletRegistration(){
//        ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet());
//        registrationBean.addUrlMappings("/console/*");
//        return registrationBean;
//    }
}
