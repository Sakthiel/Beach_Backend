package com.thoughtworks.pulpticket.users.repository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class UserTest {
    private Validator validator;

    @BeforeEach
    public void beforeEach() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
    @Test
    public void should_allow_user_to_be_created_with_valid_credentials() {

        final User user = new User("testUser", "Owner","ROLE_ADMIN");

        final Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations.size(),is(0) );
    }
    @Test
    public void should_not_allow_user_name_to_be_blank() {

        final User user = new User("", "testPassword@123" ,"ROLE_ADMIN");

        final Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations.iterator().next().getMessage(), is("Please enter valid username"));
    }
    @Test
    public void should_not_allow_password_to_be_blank() {

        final User user = new User("testUser", "","ROLE_ADMIN");

        final Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations.iterator().next().getMessage(), is("Please enter valid password"));
    }

    @Test
    public void should_not_allow_username_to_start_with_numbers() {

        final User user = new User("123testUser", "testUser","ROLE_ADMIN");

        final Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations.iterator().next().getMessage(), is("Please enter valid username"));
    }

}
