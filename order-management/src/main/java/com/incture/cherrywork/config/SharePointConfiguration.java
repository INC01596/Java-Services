package com.incture.cherrywork.config;



import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.xml.xpath.XPathExpressionFactoryBean;

import com.incture.cherrywork.dtos.SharePointPropertiesDto;



@Configuration
@EnableConfigurationProperties({ SharePointPropertiesDto.class })
public class SharePointConfiguration {

	@Bean
	public XPathExpressionFactoryBean securityTokenExpressionFactoryBean() {
		XPathExpressionFactoryBean xPathExpressionFactoryBean = new XPathExpressionFactoryBean();
		xPathExpressionFactoryBean.setExpression(
				"/S:Envelope/S:Body/wst:RequestSecurityTokenResponse/wst:RequestedSecurityToken/wsse:BinarySecurityToken");

		Map<String, String> namespaces = new HashMap<>();
		namespaces.put("S", "http://www.w3.org/2003/05/soap-envelope");
		namespaces.put("wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
		namespaces.put("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
		namespaces.put("wsa", "http://www.w3.org/2005/08/addressing");
		namespaces.put("wst", "http://schemas.xmlsoap.org/ws/2005/02/trust");
		xPathExpressionFactoryBean.setNamespaces(namespaces);
		return xPathExpressionFactoryBean;
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public SharePointPropertiesDto sharePointProperties(SharePointPropertiesDto sharePointProperties) {
		sharePointProperties.setUsername("Indu.Varanasi@incture.com");
		sharePointProperties.setPassword("AMMA@nanna7");
		sharePointProperties.setEndpointToken("https://login.microsoftonline.com");
		// PCR-Document is the group name
		sharePointProperties.setEndpointDomain("https://incturet.sharepoint.com/sites/Cherryworks-CollaborativeOrderManagement-IND00120673/Shared%20Documents/Forms");
		return sharePointProperties;
	}
}
