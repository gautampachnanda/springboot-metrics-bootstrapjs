package org.pachnanda.springboot.microservice;

import org.pachnanda.springboot.microservice.resource.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;
import org.pachnanda.springboot.microservice.entities.Customer;

import java.util.Arrays;

@SpringBootApplication
public class Application {


    @Bean
    public Validator configurationPropertiesValidator() {
        return new PropertiesValidator();
    }

    public static void main(String[] args) {

        ConfigurableApplicationContext context = new SpringApplicationBuilder(Application.class)
                .profiles("app").run(args);
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

        @Override
        public void run(String... args) {
            System.out.println("=========================================");
            System.out.println("Sample host: " + this.properties.getHost());
            System.out.println("Sample port: " + this.properties.getPort());
            System.out.println("=========================================");
            refreshCustomers();
        }

        private void refreshCustomers() {
            repository.deleteAll();
            for(String firstName: Arrays.asList("Alex","Bob","Simon")){
                for(String lastName: Arrays.asList("Smith","Jones","Porter")){
                    repository.save(new Customer(firstName, lastName));
                }
            }
        }
    }

}