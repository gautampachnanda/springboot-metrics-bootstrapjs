package org.pachnanda.springboot.microservice;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codahale.metrics.annotation.Timed;

@RestController
public class HelloController {
    
    @RequestMapping("/")
    @Timed
    public String index() {
        return "Greetings from Spring Boot!";
    }
    
}
