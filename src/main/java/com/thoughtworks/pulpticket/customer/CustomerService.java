package com.thoughtworks.pulpticket.customer;

import com.thoughtworks.pulpticket.customer.exceptions.PhoneNumberAlreadyExistException;
import com.thoughtworks.pulpticket.customer.exceptions.UsernameAlreadyExistException;
import com.thoughtworks.pulpticket.customer.model.CustomerRequest;
import com.thoughtworks.pulpticket.customer.repository.Customer;
import com.thoughtworks.pulpticket.customer.repository.CustomerRepository;
import com.thoughtworks.pulpticket.users.UserPrincipalService;
import com.thoughtworks.pulpticket.users.repository.User;
import com.thoughtworks.pulpticket.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {


    UserRepository userRepository;

    CustomerRepository customerRepository;

    UserPrincipalService userPrincipalService;

    @Autowired
    public CustomerService(UserRepository userRepository, CustomerRepository customerRepository, UserPrincipalService userPrincipalService) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.userPrincipalService = userPrincipalService;
    }

    public void create(CustomerRequest customerRequest)  {
        String username = customerRequest.getUserCredentials().getUsername();
        String password = customerRequest.getUserCredentials().getPassword();
        if (userRepository.findByUsernameIgnoreCase(username).isPresent()) {
            throw new UsernameAlreadyExistException("This username already exists");
        }
        if(!customerRepository.getCustomerByPhoneNumber(customerRequest.getPhoneNumber()).isEmpty()){
            throw new PhoneNumberAlreadyExistException("Phone number already exists");
        }
        String role = "ROLE_CUSTOMER";

        User user = new User(username , password , role);
        userRepository.save(user);

        Customer customer = new Customer(
                customerRequest.getName(),
                customerRequest.getPhoneNumber(),
                customerRequest.getEmail(),
                user
        );
        customerRepository.save(customer);
    }
}
