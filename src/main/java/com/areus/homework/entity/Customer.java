package com.areus.homework.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username cannot be empty")
    @Size(max = 50)
    private String userName;

    @NotBlank(message = "Name is required")
    @Size(max = 50)
    private String name;

    @Email(message = "Enter a valid email address")
    @NotBlank(message = "Email is mandatory")
    @Column(unique = true)
    private String email;

    @NotNull(message = "Birthdate is required")
    @Past(message = "Birthdate must be a past date")
    private LocalDate dateOfBirth;

    public Customer() {
    }
    public Customer(String userName, String name, String email, LocalDate dateOfBirth) {
        this.userName = userName;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }
    public Customer(long id, String userName, String name, String email, LocalDate dateOfBirth) {
        this.id = id;
        this.userName = userName;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
