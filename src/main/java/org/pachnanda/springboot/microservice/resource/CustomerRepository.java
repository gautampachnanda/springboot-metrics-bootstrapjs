package org.pachnanda.springboot.microservice.resource;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.pachnanda.springboot.microservice.entities.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {

    public List<Customer> findByFirstName(String firstName);
    public List<Customer> findByLastName(String lastName);

}