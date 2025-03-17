package com.areus.homework;

import com.areus.homework.entity.Customer;
import com.areus.homework.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerIT {

	@LocalServerPort
	private int port;
	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired
	private CustomerRepository customerRepository;
	private String baseUrl;
	private String jwtToken;
	@BeforeEach
	void setUp() {
		baseUrl = "http://localhost:" + port+"/customer";
		customerRepository.deleteAll();
		jwtToken = loginAndGetToken();
	}
	private String loginAndGetToken() {
		String authUrl = "http://localhost:" + port+"/login";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>("admin", headers);

		ResponseEntity<String> responseEntity = restTemplate.postForEntity(authUrl, request, String.class);
		return responseEntity.getBody();
	}

	@Test
	void testCreateCustomer_Success() {
		Customer customer = new Customer( "jani22", "Jani", "jani@email.com", LocalDate.now().minusYears(25));

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", jwtToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Customer> request = new HttpEntity<>(customer, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/create", request, String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(customerRepository.findAll()).hasSize(1);
	}
	@Test
	void testGetAllCustomers() {
		customerRepository.save(new Customer( "jani22", "Jani", "jani@email.com", LocalDate.now().minusYears(25)));

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", jwtToken);
		HttpEntity<String> request = new HttpEntity<>(headers);

		ResponseEntity<List> response = restTemplate.exchange(baseUrl + "/getAll", HttpMethod.GET, request, List.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().size()).isGreaterThan(0);
	}
	@Test
	void testGetCustomerById_Success() {
		Customer savedCustomer = customerRepository.save(new Customer( "jani22", "Jani", "jani@email.com", LocalDate.now().minusYears(25)));

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", jwtToken);
		HttpEntity<String> request = new HttpEntity<>(headers);

		ResponseEntity<Customer> response = restTemplate.exchange(baseUrl + "/getById/" + savedCustomer.getId(), HttpMethod.GET, request, Customer.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getName()).isEqualTo("Jani");
	}
	@Test
	void testDeleteCustomer_Success() {
		Customer savedCustomer = customerRepository.save(new Customer( "jani22", "Jani", "jani@email.com", LocalDate.now().minusYears(25)));

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", jwtToken);
		HttpEntity<String> request = new HttpEntity<>(headers);

		ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/delete/" + savedCustomer.getId(), HttpMethod.DELETE, request, String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(customerRepository.findById(savedCustomer.getId())).isEmpty();
	}
	@Test
	void testUpdateCustomer_Success() {
		Customer savedCustomer = customerRepository.save(new Customer( "jani22", "Jani", "jani@email.com", LocalDate.now().minusYears(25)));
		Customer updatedCustomer = new Customer(savedCustomer.getId(), "janiUpdated", "Jani Nagyon Updated", "jani@email.com", LocalDate.now().minusYears(35));

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", jwtToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Customer> request = new HttpEntity<>(updatedCustomer, headers);

		ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/update/" + savedCustomer.getId(), HttpMethod.PUT, request, String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(customerRepository.findById(savedCustomer.getId()).get().getName()).isEqualTo("Jani Nagyon Updated");
		assertThat(customerRepository.findById(savedCustomer.getId()).get().getUserName()).isEqualTo("janiUpdated");

	}
	@Test
	void testGetAverageCustomerAge_Success() {
		customerRepository.save(new Customer( "jani22", "Jani", "jani@email.com", LocalDate.now().minusYears(25)));
		customerRepository.save(new Customer( "jani23", "Jani", "jani@email.com", LocalDate.now().minusYears(35)));

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", jwtToken);
		HttpEntity<String> request = new HttpEntity<>(headers);

		ResponseEntity<Double> response = restTemplate.exchange(baseUrl + "/averageAge", HttpMethod.GET, request, Double.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo(30.0);
	}

	@Test
	void testGetAdults_Success() {
		customerRepository.save(new Customer( "jani22", "Jani", "jani@email.com", LocalDate.now().minusYears(10)));
		customerRepository.save(new Customer( "jani23", "Jani", "jani@email.com", LocalDate.now().minusYears(25)));
		customerRepository.save(new Customer( "jani24", "Jani", "jani@email.com", LocalDate.now().minusYears(60)));

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", jwtToken);
		HttpEntity<String> request = new HttpEntity<>(headers);

		ResponseEntity<List> response = restTemplate.exchange(baseUrl + "/adults", HttpMethod.GET, request, List.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		assertThat(response.getBody().size()).isEqualTo(1);
	}

}
