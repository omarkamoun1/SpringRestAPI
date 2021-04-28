package com.jobdiva.api.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.jobdiva.api.convertor.JsonToAddExpenseEntryDefConverter;
import com.jobdiva.api.convertor.JsonToAddExpenseInvoiceDefConverter;
import com.jobdiva.api.convertor.JsonToAddJobNoteDefConverter;
import com.jobdiva.api.convertor.JsonToApproveExpenseEntryDefConverter;
import com.jobdiva.api.convertor.JsonToBillingUnitTypeConverter;
import com.jobdiva.api.convertor.JsonToCandidateAttachementConverter;
import com.jobdiva.api.convertor.JsonToCandidateEmailMergeDefConverter;
import com.jobdiva.api.convertor.JsonToCandidateQualConverter;
import com.jobdiva.api.convertor.JsonToCandidateReferenceDefConverter;
import com.jobdiva.api.convertor.JsonToCompanyAddressConverter;
import com.jobdiva.api.convertor.JsonToContactAddressConverter;
import com.jobdiva.api.convertor.JsonToContactAttachementConverter;
import com.jobdiva.api.convertor.JsonToContactRoleTypeConverter;
import com.jobdiva.api.convertor.JsonToCreateBillingRecordDefConverter;
import com.jobdiva.api.convertor.JsonToCreateCandidateNoteDefConverter;
import com.jobdiva.api.convertor.JsonToCreateCandidateProfileDefDocumentConverter;
import com.jobdiva.api.convertor.JsonToCreateCandidatesNoteDefConverter;
import com.jobdiva.api.convertor.JsonToCreateCompanyDefConverter;
import com.jobdiva.api.convertor.JsonToCreateContactDefConverter;
import com.jobdiva.api.convertor.JsonToCreateContactNoteDefConverter;
import com.jobdiva.api.convertor.JsonToCreateEventDefConverter;
import com.jobdiva.api.convertor.JsonToCreateJobApplicationDefConverter;
import com.jobdiva.api.convertor.JsonToCreateJobDefConverter;
import com.jobdiva.api.convertor.JsonToCreatePayRecordDefConverter;
import com.jobdiva.api.convertor.JsonToCreateTaskDefConverter;
import com.jobdiva.api.convertor.JsonToEducationQualConverter;
import com.jobdiva.api.convertor.JsonToEventNotificationConverter;
import com.jobdiva.api.convertor.JsonToExpenseEntryConverter;
import com.jobdiva.api.convertor.JsonToExperienceQualConverter;
import com.jobdiva.api.convertor.JsonToFrequencyTypeConverter;
import com.jobdiva.api.convertor.JsonToGroupInvoiceByTypeConverter;
import com.jobdiva.api.convertor.JsonToMarkTimesheetPaidDefConverter;
import com.jobdiva.api.convertor.JsonToOnboardinCandidateDocumentConverter;
import com.jobdiva.api.convertor.JsonToOwnerConverter;
import com.jobdiva.api.convertor.JsonToPhoneConverter;
import com.jobdiva.api.convertor.JsonToProxyHeaderConverter;
import com.jobdiva.api.convertor.JsonToProxyParameterConverter;
import com.jobdiva.api.convertor.JsonToQualificationTypeConverter;
import com.jobdiva.api.convertor.JsonToSearchCandidateProfileDefDocumentConverter;
import com.jobdiva.api.convertor.JsonToSearchCompanyDefConverter;
import com.jobdiva.api.convertor.JsonToSearchContactsDefConverter;
import com.jobdiva.api.convertor.JsonToSearchJobDefConverter;
import com.jobdiva.api.convertor.JsonToSearchStartDefConverter;
import com.jobdiva.api.convertor.JsonToShowOnInvoiceTypeConverter;
import com.jobdiva.api.convertor.JsonToSkillConverter;
import com.jobdiva.api.convertor.JsonToSocialNetworkConverter;
import com.jobdiva.api.convertor.JsonToSocialNetworkTypeConverter;
import com.jobdiva.api.convertor.JsonToTimeSheetEntryFormatTypeConverter;
import com.jobdiva.api.convertor.JsonToTimesheetEntryConverter;
import com.jobdiva.api.convertor.JsonToTimezoneConverter;
import com.jobdiva.api.convertor.JsonToTitleSkillCertificationConverter;
import com.jobdiva.api.convertor.JsonToUpdateBillingRecordDefConverter;
import com.jobdiva.api.convertor.JsonToUpdateCandidateAvailabilityDefConverter;
import com.jobdiva.api.convertor.JsonToUpdateCandidateHRRecordDefConverter;
import com.jobdiva.api.convertor.JsonToUpdateCandidateProfileDefConverter;
import com.jobdiva.api.convertor.JsonToUpdateCandidateQualificationsDefConverter;
import com.jobdiva.api.convertor.JsonToUpdateCandidateSNLinksDefConverter;
import com.jobdiva.api.convertor.JsonToUpdateCompanyDefConverter;
import com.jobdiva.api.convertor.JsonToUpdateContactDefConverter;
import com.jobdiva.api.convertor.JsonToUpdateEventDefConverter;
import com.jobdiva.api.convertor.JsonToUpdateJobDefConverter;
import com.jobdiva.api.convertor.JsonToUpdatePayRecordDefConverter;
import com.jobdiva.api.convertor.JsonToUpdatePayrollProfileDefConverter;
import com.jobdiva.api.convertor.JsonToUpdateStartDefConverter;
import com.jobdiva.api.convertor.JsonToUpdateTaskDefConverter;
import com.jobdiva.api.convertor.JsonToUplaodResumeConverter;
import com.jobdiva.api.convertor.JsonToUploadTimesheetAssignmentDefConverter;
import com.jobdiva.api.convertor.JsonToUploadTimesheetDefConverter;
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
				//
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
		registry.addConverter(new JsonToUpdateCandidateHRRecordDefConverter());
		//
		registry.addConverter(new JsonToProxyHeaderConverter());
		registry.addConverter(new JsonToProxyParameterConverter());
		//
		registry.addConverter(new JsonToSocialNetworkTypeConverter());
		registry.addConverter(new JsonToUplaodResumeConverter());
		registry.addConverter(new JsonToCandidateAttachementConverter());
		registry.addConverter(new JsonToContactAttachementConverter());
		registry.addConverter(new JsonToEventNotificationConverter());
		registry.addConverter(new JsonToSearchCandidateProfileDefDocumentConverter());
		registry.addConverter(new JsonToOnboardinCandidateDocumentConverter());
		//
		// V2
		registry.addConverter(new JsonToSearchCandidateProfileDefDocumentConverter());
		registry.addConverter(new JsonToCreateCandidateProfileDefDocumentConverter());
		registry.addConverter(new JsonToUpdateCandidateSNLinksDefConverter());
		registry.addConverter(new JsonToCandidateEmailMergeDefConverter());
		registry.addConverter(new JsonToCandidateReferenceDefConverter());
		registry.addConverter(new JsonToUpdateCandidateHRRecordDefConverter());
		registry.addConverter(new JsonToCreateCandidateNoteDefConverter());
		registry.addConverter(new JsonToUpdateCandidateAvailabilityDefConverter());
		registry.addConverter(new JsonToUpdateCandidateQualificationsDefConverter());
		registry.addConverter(new JsonToUpdateCandidateProfileDefConverter());
		registry.addConverter(new JsonToUploadTimesheetDefConverter());
		registry.addConverter(new JsonToCreateCandidatesNoteDefConverter());
		registry.addConverter(new JsonToSearchCompanyDefConverter());
		registry.addConverter(new JsonToCreateCompanyDefConverter());
		registry.addConverter(new JsonToUpdateCompanyDefConverter());
		registry.addConverter(new JsonToCreateBillingRecordDefConverter());
		registry.addConverter(new JsonToSearchContactsDefConverter());
		registry.addConverter(new JsonToCreateContactDefConverter());
		registry.addConverter(new JsonToCreateContactNoteDefConverter());
		registry.addConverter(new JsonToUpdateContactDefConverter());
		registry.addConverter(new JsonToCreateTaskDefConverter());
		registry.addConverter(new JsonToUpdateTaskDefConverter());
		registry.addConverter(new JsonToCreateEventDefConverter());
		registry.addConverter(new JsonToUpdateEventDefConverter());
		registry.addConverter(new JsonToSearchJobDefConverter());
		registry.addConverter(new JsonToCreateJobDefConverter());
		registry.addConverter(new JsonToAddJobNoteDefConverter());
		registry.addConverter(new JsonToUpdateJobDefConverter());
		registry.addConverter(new JsonToCreateJobApplicationDefConverter());
		registry.addConverter(new JsonToUploadTimesheetAssignmentDefConverter());
		registry.addConverter(new JsonToAddExpenseEntryDefConverter());
		registry.addConverter(new JsonToApproveExpenseEntryDefConverter());
		registry.addConverter(new JsonToAddExpenseInvoiceDefConverter());
		registry.addConverter(new JsonToMarkTimesheetPaidDefConverter());
		registry.addConverter(new JsonToUpdatePayrollProfileDefConverter());
		registry.addConverter(new JsonToCreateBillingRecordDefConverter());
		registry.addConverter(new JsonToUpdateBillingRecordDefConverter());
		registry.addConverter(new JsonToUpdatePayRecordDefConverter());
		registry.addConverter(new JsonToCreatePayRecordDefConverter());
		registry.addConverter(new JsonToSearchStartDefConverter());
		registry.addConverter(new JsonToUpdateStartDefConverter());
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
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// registry.addInterceptor(new RequestInterceptor());
	}
}
