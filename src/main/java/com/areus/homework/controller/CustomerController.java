package com.areus.homework.controller;

import com.areus.homework.entity.Customer;
import com.areus.homework.repository.CustomerRepository;
import com.areus.homework.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final CustomerService customerService;

    public CustomerController(CustomerRepository customerRepository, CustomerService customerService) {
        this.customerRepository = customerRepository;
        this.customerService = customerService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        Customer newCustomer = customerRepository.save(customer);

        if (newCustomer.getId() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer creation failed");

        return ResponseEntity.status(HttpStatus.CREATED).body(String.valueOf(newCustomer.getId()));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Customer>> getCustomers() {
        return ResponseEntity.status(HttpStatus.OK).body(customerRepository.findAll());
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        return customerRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        return customerService.deleteCustomer(id) ?
                ResponseEntity.ok("Customer deleted") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        return customerService.updateCustomer(id, updatedCustomer)
                .map(customer -> ResponseEntity.ok("Customer updated"))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found"));
    }

    @GetMapping("/averageAge")
    public ResponseEntity<Double> getAverageCustomerAge() {
        Double averageAge = customerService.getAverageCustomerAge();

        if (averageAge == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(averageAge);
    }
    @GetMapping("/adults")
    public ResponseEntity<List<Customer>> getAdults() {
        List<Customer> adults = customerService.getAdults();

        if (adults.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(adults);
    }
}
