package org.pachnanda.springboot.microservice;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.pachnanda.springboot.microservice.entities.Customer;
import org.pachnanda.springboot.microservice.resource.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.fasterxml.classmate.TypeResolver;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackageClasses = { HelloController.class })
public class Application {

	@Bean
	public Validator configurationPropertiesValidator() {
		return new PropertiesValidator();
	}

	public static void main(String[] args) {

		ConfigurableApplicationContext context = new SpringApplicationBuilder(
				Application.class).profiles("app").run(args);
		System.out.println("Let's inspect the beans provided by Spring Boot:");

		String[] beanNames = context.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		for (String beanName : beanNames) {
			System.out.println(beanName);
		}

	}

	@Service
	@Profile("app")
	static class Startup implements CommandLineRunner {

		@Autowired
		private CustomerRepository repository;

		@Autowired
		private AppProperties properties;

		// @Override
		public void run(String... args) {
			System.out.println("=========================================");
			System.out.println("Sample host: " + this.properties.getHost());
			System.out.println("Sample port: " + this.properties.getPort());
			System.out.println("=========================================");
			refreshCustomers();
		}

		private void refreshCustomers() {
			repository.deleteAll();
			for (String firstName : Arrays.asList("Alex", "Bob", "Simon")) {
				for (String lastName : Arrays
						.asList("Smith", "Jones", "Porter")) {
					repository.save(new Customer(firstName, lastName));
				}
			}
		}

		@Bean
		public Docket petApi() {
			return new Docket(DocumentationType.SWAGGER_2)
					.select()
					.apis(RequestHandlerSelectors.any())
					.paths(PathSelectors.any())
					.build()
					.pathMapping("/")
					.directModelSubstitute(LocalDate.class, String.class)
					.genericModelSubstitutes(ResponseEntity.class)
					.alternateTypeRules(
							newRule(typeResolver.resolve(DeferredResult.class,
									typeResolver.resolve(ResponseEntity.class,
											WildcardType.class)), typeResolver
									.resolve(WildcardType.class)))
					.useDefaultResponseMessages(false)
					.globalResponseMessage(
							RequestMethod.GET,
							newArrayList(new ResponseMessageBuilder().code(500)
									.message("500 message")
									.responseModel(new ModelRef("Error"))
									.build()))
					.securitySchemes(newArrayList(apiKey()))
					.securityContexts(newArrayList(securityContext()));
		}

		@Autowired
		private TypeResolver typeResolver;

		private ApiKey apiKey() {
			return new ApiKey("mykey", "api_key", "header");
		}

		private SecurityContext securityContext() {
			return SecurityContext.builder().securityReferences(defaultAuth())
					.forPaths(PathSelectors.regex("/anyPath.*")).build();
		}

		List<SecurityReference> defaultAuth() {
			AuthorizationScope authorizationScope = new AuthorizationScope(
					"global", "accessEverything");
			AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
			authorizationScopes[0] = authorizationScope;
			return newArrayList(new SecurityReference("mykey",
					authorizationScopes));
		}

		@Bean
		SecurityConfiguration security() {
			return new SecurityConfiguration("test-app-client-id",
					"test-app-realm", "test-app", "apiKey");
		}

		@Bean
		UiConfiguration uiConfig() {
			return new UiConfiguration("validatorUrl");
		}
	}

}