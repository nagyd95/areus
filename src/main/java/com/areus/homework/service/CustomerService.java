package com.areus.homework.service;

import com.areus.homework.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<Customer> getAdults();

    Optional<Customer> updateCustomer(Long id, Customer updatedCustomer);

    boolean deleteCustomer(Long id);

    Double getAverageCustomerAge();
}
