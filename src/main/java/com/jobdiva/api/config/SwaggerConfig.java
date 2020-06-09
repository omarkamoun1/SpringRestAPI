package com.jobdiva.api.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.jobdiva.api.convertor.JsonToAttachmentConverter;
import com.jobdiva.api.convertor.JsonToBillingUnitTypeConverter;
import com.jobdiva.api.convertor.JsonToCandidateQualConverter;
import com.jobdiva.api.convertor.JsonToCompanyAddressConverter;
import com.jobdiva.api.convertor.JsonToContactAddressConverter;
import com.jobdiva.api.convertor.JsonToContactRoleTypeConverter;
import com.jobdiva.api.convertor.JsonToEducationQualConverter;
import com.jobdiva.api.convertor.JsonToExpenseEntryConverter;
import com.jobdiva.api.convertor.JsonToExperienceQualConverter;
import com.jobdiva.api.convertor.JsonToFrequencyTypeConverter;
import com.jobdiva.api.convertor.JsonToGroupInvoiceByTypeConverter;
import com.jobdiva.api.convertor.JsonToOwnerConverter;
import com.jobdiva.api.convertor.JsonToPhoneConverter;
import com.jobdiva.api.convertor.JsonToQualificationTypeConverter;
import com.jobdiva.api.convertor.JsonToShowOnInvoiceTypeConverter;
import com.jobdiva.api.convertor.JsonToSkillConverter;
import com.jobdiva.api.convertor.JsonToSocialNetworkConverter;
import com.jobdiva.api.convertor.JsonToTimeSheetEntryFormatTypeConverter;
import com.jobdiva.api.convertor.JsonToTimesheetEntryConverter;
import com.jobdiva.api.convertor.JsonToTimezoneConverter;
import com.jobdiva.api.convertor.JsonToTitleSkillCertificationConverter;
import com.jobdiva.api.convertor.JsonToUserFieldConverter;
import com.jobdiva.api.convertor.JsonToUserRoleConverter;
import com.jobdiva.api.convertor.JsonToWeekendingTypeConverter;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig extends WebMvcConfigurationSupport {
	
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2) //
				.select() //
				.apis(RequestHandlerSelectors.basePackage("com.jobdiva.api.controller")) //
				.paths(regex("/api.*")) //
				.build() //
				.securitySchemes(Arrays.asList(apiKey()))//
				.securityContexts(Arrays.asList(securityContext())) //
				.apiInfo(metaData());
	}
	
	private ApiKey apiKey() {
		return new ApiKey("Authorization", "Authorization", "header");
	}
	
	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
	}
	
	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
	}
	
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new JsonToCompanyAddressConverter());
		registry.addConverter(new JsonToUserFieldConverter());
		registry.addConverter(new JsonToTimezoneConverter());
		registry.addConverter(new JsonToPhoneConverter());
		registry.addConverter(new JsonToContactAddressConverter());
		registry.addConverter(new JsonToOwnerConverter());
		registry.addConverter(new JsonToCandidateQualConverter());
		registry.addConverter(new JsonToExperienceQualConverter());
		registry.addConverter(new JsonToEducationQualConverter());
		registry.addConverter(new JsonToSocialNetworkConverter());
		registry.addConverter(new JsonToTimesheetEntryConverter());
		registry.addConverter(new JsonToExpenseEntryConverter());
		registry.addConverter(new JsonToQualificationTypeConverter());
		registry.addConverter(new JsonToContactRoleTypeConverter());
		registry.addConverter(new JsonToTitleSkillCertificationConverter());
		registry.addConverter(new JsonToShowOnInvoiceTypeConverter());
		registry.addConverter(new JsonToGroupInvoiceByTypeConverter());
		registry.addConverter(new JsonToTimeSheetEntryFormatTypeConverter());
		registry.addConverter(new JsonToFrequencyTypeConverter());
		registry.addConverter(new JsonToWeekendingTypeConverter());
		registry.addConverter(new JsonToBillingUnitTypeConverter());
		registry.addConverter(new JsonToUserRoleConverter());
		registry.addConverter(new JsonToSkillConverter());
		registry.addConverter(new JsonToAttachmentConverter());
	}
	
	private ApiInfo metaData() {
		return new ApiInfoBuilder()
				// .title("JobDiva REST API") //
				// .description("HTTP-based RESTful APIs For JobDiva") //
				// .version("1.0.0") //
				.build();
	}
	
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("jobdiva-api.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("logo.png").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("img/*").addResourceLocations("classpath:/META-INF/resources/img");
		//
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		//
	}
}
