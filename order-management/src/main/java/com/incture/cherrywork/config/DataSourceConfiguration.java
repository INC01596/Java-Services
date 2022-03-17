package com.incture.cherrywork.config;



import javax.sql.DataSource;


import org.springframework.boot.jdbc.DataSourceBuilder;

import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.config.java.ServiceScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;


import com.zaxxer.hikari.HikariDataSource;

import io.pivotal.cfenv.core.CfCredentials;
import io.pivotal.cfenv.jdbc.CfJdbcEnv;


@Configuration
@Profile("cloud")
@ServiceScan
@ComponentScan({ "com.incture.cherrywork" })
@PropertySource(value = { "classpath:application.properties" })
public class DataSourceConfiguration extends AbstractCloudConfig {
	
//	@Autowired
//	private Environment environment;

	@Bean
	@Primary
	public DataSource datasource() {
		CfJdbcEnv cfJdbcEnv = new CfJdbcEnv();
		CfCredentials cfCredentials = cfJdbcEnv.findCredentialsByTag("hana");
		return DataSourceBuilder.create().type(HikariDataSource.class)
				.driverClassName(com.sap.db.jdbc.Driver.class.getName()).url(cfCredentials.getUri())
				.username(cfCredentials.getUsername()).password(cfCredentials.getPassword()).build();
	}
		
//	private Properties hibernateProperties() {
//		Properties properties = new Properties();
//		properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
//		properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
//		properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
//		properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
//		properties.put("hibernate.jdbc.batch_size", environment.getRequiredProperty("hibernate.jdbc.batch_size"));
//		properties.put("hibernate.cache.use_second_level_cache",
//				environment.getRequiredProperty("hibernate.cache.use_second_level_cache"));
//		return properties;
//	}

	
	
//    @Bean
//    ServletRegistrationBean h2servletRegistration(){
//        ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet());
//        registrationBean.addUrlMappings("/console/*");
//        return registrationBean;
//    }
}
