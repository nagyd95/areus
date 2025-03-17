package com.areus.homework.service;

import com.areus.homework.entity.Customer;
import com.areus.homework.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAdults() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().filter(x -> {
                    int age = Period.between(x.getDateOfBirth(), LocalDate.now()).getYears();
                    return age >= 18 && age <= 40;
                })
                .toList();
    }

    public Optional<Customer> updateCustomer(Long id, Customer updatedCustomer) {
        return customerRepository.findById(id).map(customer -> {
            updateCustomerDetails(customer, updatedCustomer);
            return customerRepository.save(customer);
        });
    }

    private void updateCustomerDetails(Customer customer, Customer updatedCustomer) {
        customer.setUserName(updatedCustomer.getUserName());
        customer.setName(updatedCustomer.getName());
        customer.setEmail(updatedCustomer.getEmail());
        customer.setDateOfBirth(updatedCustomer.getDateOfBirth());
    }

    public boolean deleteCustomer(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Double getAverageCustomerAge() {
        return customerRepository.findAverageAge();
    }
}
