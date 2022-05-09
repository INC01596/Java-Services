//package com.incture.cherrywork.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.oauth2.jwt.Jwt;
//
//import com.sap.cloud.security.xsuaa.XsuaaServiceConfiguration;
//import com.sap.cloud.security.xsuaa.token.TokenAuthenticationConverter;
//
//@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
//@EnableWebSecurity
//@PropertySource("classpath:application.properties")
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//	private String[] noauth = { "/elc/admin/**", "/elc/claims/docs/**", "/elc/data/**", "/elc/notify/**",
//			"/elc/rule/**", "/elc/scheduler/**", "/elc/workbox/**", "/elc/workflow/**", "/elc/dif/**", "/elc/dms/**" };
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
//				.antMatchers(HttpMethod.POST, "/elc/odata/**").authenticated().antMatchers(noauth).permitAll()
//				.anyRequest().authenticated().and().oauth2ResourceServer().jwt()
//				.jwtAuthenticationConverter(getJwtAuthenticationConverter());
//		http.csrf().disable();
//
//	}
//
//	@Autowired
//	private XsuaaServiceConfiguration xsuaaServiceConfiguration;
//
//	Converter<Jwt, AbstractAuthenticationToken> getJwtAuthenticationConverter() {
//		TokenAuthenticationConverter converter = new TokenAuthenticationConverter(xsuaaServiceConfiguration);
//		converter.setLocalScopeAsAuthorities(true);
//		return converter;
//	}
//
//}
