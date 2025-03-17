package com.areus.homework;

import com.areus.homework.entity.Customer;
import com.areus.homework.repository.CustomerRepository;
import com.areus.homework.service.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer youngCustomer;
    private Customer oldCustomer;

    @BeforeEach
    void setUp() {
        youngCustomer = new Customer(1L, "youngUser", "John", "john@test.com", LocalDate.now().minusYears(25));
        oldCustomer = new Customer(2L, "oldUser", "Jane", "jane@test.com", LocalDate.now().minusYears(50));
    }

    @Test
    void testGetAdults() {
        when(customerRepository.findAll()).thenReturn(List.of(youngCustomer, oldCustomer));

        List<Customer> result = customerService.getAdults();

        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getName());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void testUpdateCustomer_Success() {
        Customer updatedCustomer = new Customer(1L, "newUser", "John Updated", "john.updated@test.com", LocalDate.now().minusYears(30));

        when(customerRepository.findById(1L)).thenReturn(Optional.of(youngCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);

        Optional<Customer> result = customerService.updateCustomer(1L, updatedCustomer);

        assertTrue(result.isPresent());
        assertEquals("John Updated", result.get().getName());
        verify(customerRepository, times(1)).findById(1L);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void testUpdateCustomer_NotFound() {
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Customer> result = customerService.updateCustomer(99L, youngCustomer);

        assertFalse(result.isPresent());
        verify(customerRepository, times(1)).findById(99L);
        verify(customerRepository, never()).save(any());
    }

    @Test
    void testDeleteCustomer_Success() {
        when(customerRepository.existsById(1L)).thenReturn(true);

        boolean result = customerService.deleteCustomer(1L);

        assertTrue(result);
        verify(customerRepository, times(1)).existsById(1L);
        verify(customerRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteCustomer_NotFound() {
        when(customerRepository.existsById(99L)).thenReturn(false);

        boolean result = customerService.deleteCustomer(99L);

        assertFalse(result);
        verify(customerRepository, times(1)).existsById(99L);
        verify(customerRepository, never()).deleteById(anyLong());
    }

    @Test
    void testGetAverageCustomerAge() {
        when(customerRepository.findAverageAge()).thenReturn(30.5);

        Double averageAge = customerService.getAverageCustomerAge();

        assertEquals(30.5, averageAge);
        verify(customerRepository, times(1)).findAverageAge();
    }

}
