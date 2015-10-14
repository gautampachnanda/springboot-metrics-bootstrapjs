package org.pachnanda.springboot.microservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.pachnanda.springboot.microservice.entities.Customer;
import org.pachnanda.springboot.microservice.resource.CustomerRepository;

import java.util.List;

/**
 * Created by gautamp on 13/10/2015.
 */
@RestController
public class CustomerController {


    @Autowired
    private CustomerRepository repository;

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public ResponseEntity<List<Customer>> customers() {
        List<Customer> customers = repository.findAll();
        return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
    }

    @RequestMapping(value = "/customers/firstName/{name}", method = RequestMethod.GET)
    public ResponseEntity<List<Customer>>  customersByFirstName(@PathVariable String name) {
        List<Customer> customers = repository.findByFirstName(name);
        return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
    }

    @RequestMapping(value = "/customers/lastName/{name}", method = RequestMethod.GET)
    public ResponseEntity<List<Customer>>  customersByLastName(@PathVariable String name) {
        List<Customer> customers = repository.findByLastName(name);
        return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
    }
}
