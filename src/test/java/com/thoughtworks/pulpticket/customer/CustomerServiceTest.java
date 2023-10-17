package com.thoughtworks.pulpticket.customer;

import com.thoughtworks.pulpticket.customer.exceptions.PhoneNumberAlreadyExistException;
import com.thoughtworks.pulpticket.customer.exceptions.UsernameAlreadyExistException;
import com.thoughtworks.pulpticket.customer.model.CustomerRequest;
import com.thoughtworks.pulpticket.customer.model.UserCredentials;
import com.thoughtworks.pulpticket.customer.repository.Customer;
import com.thoughtworks.pulpticket.customer.repository.CustomerRepository;
import com.thoughtworks.pulpticket.users.UserPrincipalService;
import com.thoughtworks.pulpticket.users.repository.User;
import com.thoughtworks.pulpticket.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserPrincipalService userPrincipalService;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_create_customer_when_valid_details_are_sent() {
        UserCredentials userCredentials = new UserCredentials("testUser", "testPassword");
        CustomerRequest customerRequest = new CustomerRequest("Sakthi" , "1234567890" , "sakthi@gmail.com" , userCredentials);
        User user = new User("testUser", "testPassword" , "ROLE_CUSTOMER");
        List<Customer> customers = new ArrayList<>();

        when(customerRepository.getCustomerByPhoneNumber(customerRequest.getPhoneNumber())).thenReturn(customers);
        customerService.create(customerRequest);

        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    public void should_throw_exception_when_phone_number_already_exist() {
        UserCredentials userCredentials = new UserCredentials("testUser", "testPassword");
        CustomerRequest customerRequest = new CustomerRequest("Sakthi" , "1234567890" , "sakthi@gmail.com" , userCredentials);
        User user = new User("testUser", "testPassword" ,  "ROLE_CUSTOMER");
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer("Sakthi", "1234567890", "sakthi@gmail.com", user);
        customers.add(customer);
        when(customerRepository.getCustomerByPhoneNumber(customerRequest.getPhoneNumber())).thenReturn(customers);


        assertThrows(PhoneNumberAlreadyExistException.class, () -> {
            customerService.create(customerRequest);
        });
    }

    @Test
    public void should_throw_exception_when_username_already_exist() {
        UserCredentials userCredentials = new UserCredentials("testUser", "testPassword");
        CustomerRequest customerRequest = new CustomerRequest("Sakthi" , "1234567890" , "sakthi@gmail.com" , userCredentials);
        User user = new User("testUser", "testPassword" ,  "ROLE_CUSTOMER");
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer("Sakthi", "1234567890", "sakthi@gmail.com", user);
        customers.add(customer);
        when(userRepository.findByUsernameIgnoreCase(userCredentials.getUsername())).thenReturn(Optional.of(user));


        assertThrows(UsernameAlreadyExistException.class, () -> {
            customerService.create(customerRequest);
        });
    }


}
