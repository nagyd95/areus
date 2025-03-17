package com.areus.homework.repository;

import com.areus.homework.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    //@Query(value = "SELECT AVG(EXTRACT(YEAR FROM age(c.dateOfBirth))) FROM Customer c", nativeQuery = true)
    @Query("SELECT AVG(YEAR(CURRENT_DATE) - YEAR(c.dateOfBirth) - " +
            "CASE " +
            "WHEN MONTH(CURRENT_DATE) < MONTH(c.dateOfBirth) " +
            "OR (MONTH(CURRENT_DATE) = MONTH(c.dateOfBirth) AND DAY(CURRENT_DATE) < DAY(c.dateOfBirth)) " +
            "THEN 1 " +
            "ELSE 0 " +
            "END ) " +
            "FROM Customer c")
    Double findAverageAge();
}
