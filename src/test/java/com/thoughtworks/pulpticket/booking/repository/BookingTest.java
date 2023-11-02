package com.thoughtworks.pulpticket.booking.repository;

import com.thoughtworks.pulpticket.customer.model.UserCredentials;
import com.thoughtworks.pulpticket.customer.repository.Customer;
import com.thoughtworks.pulpticket.movie.repository.Movie;
import com.thoughtworks.pulpticket.screen.repository.Screen;
import com.thoughtworks.pulpticket.show.repository.Show;
import com.thoughtworks.pulpticket.slot.repository.Slot;
import com.thoughtworks.pulpticket.users.repository.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class BookingTest {
    private Validator validator;

    private Slot slot;

    private Date date;
    private Show show;

    private Customer customer;

    private Movie movie;

    private Screen screen;

    private final User user = mock(User.class);

    @BeforeEach
    public void beforeEach() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        slot = new Slot("Slot name", Time.valueOf("13:00:00"), Time.valueOf("15:00:00"));
        date = Date.valueOf("2020-06-01");
        movie = new Movie(
                "tteefdu" ,
                "Bigil" , 100L ,
                "sport" , BigDecimal.valueOf(7.5) ,
                "https://m.media-amazon.com/images/M/MV5BMjI0MDMzNTQ0M15BMl5BanBnXkFtZTgwMTM5NzM3NDM@._V1_.jpg");
        screen = new Screen("GoldPlex");
        show = new Show(date, slot,screen,BigDecimal.valueOf(245.99), movie);
        customer =new Customer("Sakthi", "1234567890", "abc@gmail.com",user);




    }

    @Test
    public void should_allow_booking_to_be_created_with_valid_booking_details() {
        Booking booking = new Booking(date, show, customer, 2, BigDecimal.valueOf(244.99));

        final Set<ConstraintViolation<Booking>> violations = validator.validate(booking);

        assertThat(violations.size(),is(0) );
    }

    @Test
    public void should_not_allow_null_fields() {
        Booking booking = new Booking(null, null, null, null, null);

        final Set<ConstraintViolation<Booking>> violations = validator.validate(booking);

        final List<String> messages = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());

        assertTrue(messages.containsAll(Arrays
                .asList("No of seats must be provided",
                        "Customer must be provided",
                        "Amount paid must be provided",
                        "Date must be provided",
                        "Show must be provided")));
    }

    @Test
    public void should_not_allow_no_of_seats_less_than_0() {
        final Booking booking = new Booking(date, show, customer, -1, BigDecimal.valueOf(299));

        final Set<ConstraintViolation<Booking>> violations = validator.validate(booking);

        assertThat(violations.iterator().next().getMessage(), is("Seats should be greater than 0"));
    }

    @Test
    public void should_not_allow_amount_paid_less_than_0() {
        final Booking booking = new Booking(date, show, customer, 1, BigDecimal.valueOf(-1));

        final Set<ConstraintViolation<Booking>> violations = validator.validate(booking);

        assertThat(violations.iterator().next().getMessage(), is("Amount paid should be greater than 0"));
    }
}
