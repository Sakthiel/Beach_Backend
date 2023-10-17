package com.thoughtworks.pulpticket.customer.repository;

import com.thoughtworks.pulpticket.users.repository.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

public class CustomerTest {
    private Validator validator;
    private final User user = mock(User.class);

    @BeforeEach
    public void beforeEach() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void should_not_allow_customer_name_to_be_blank() {

        final Customer customer = new Customer("", "1234567890", "abc@gmail.com",user);

        final Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertThat(violations.iterator().next().getMessage(), is("Name must not be blank"));
    }

    @Test
    public void should_allow_phone_number_with_only_10_digits() {
        final Customer customer = new Customer("Sakthi", "99932", "abc@gmail.com",user);

        final Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertThat(violations.iterator().next().getMessage(), is("Phone number must have exactly 10 digits"));
    }

    @Test
    public void should_not_allow_blank_phone_number() {
        final Customer customer = new Customer("Sakthi", "","test@gmail.com",user);

        final Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertThat(violations.iterator().next().getMessage(), is("Phone number must have exactly 10 digits"));
    }

    @Test
    public void should_not_allow_invalid_email_id() {
        final Customer customer = new Customer("Sakthi", "1234567890","abcgmail.com",user);

        final Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertThat(violations.iterator().next().getMessage(), is("Invalid Email Id"));
    }
}
