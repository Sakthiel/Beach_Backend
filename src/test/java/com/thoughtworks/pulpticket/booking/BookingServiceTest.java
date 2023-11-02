package com.thoughtworks.pulpticket.booking;

import com.thoughtworks.pulpticket.booking.exceptions.NotEnoughSeatsException;
import com.thoughtworks.pulpticket.booking.repository.Booking;
import com.thoughtworks.pulpticket.booking.repository.BookingRepository;
import com.thoughtworks.pulpticket.booking.repository.BookingRequest;
import com.thoughtworks.pulpticket.customer.repository.Customer;
import com.thoughtworks.pulpticket.customer.repository.CustomerRepository;
import com.thoughtworks.pulpticket.movie.repository.Movie;
import com.thoughtworks.pulpticket.screen.repository.Screen;
import com.thoughtworks.pulpticket.show.repository.Show;
import com.thoughtworks.pulpticket.show.repository.ShowRepository;
import com.thoughtworks.pulpticket.slot.repository.Slot;
import com.thoughtworks.pulpticket.users.repository.User;
import com.thoughtworks.pulpticket.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.Optional;

import static com.thoughtworks.pulpticket.show.repository.Constants.TOTAL_NO_OF_SEATS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class BookingServiceTest {
    private BookingRepository bookingRepository;
    private CustomerRepository customerRepository;
    private ShowRepository showRepository;

    private UserRepository userRepository;

    private BookingService bookingService;
    private Date bookingDate;

    private Movie movie;

    private Screen screen;

    private Show show;
    private User user;
    private Customer customer;

    private Slot slot;

    private BookingRequest bookingRequest;
    @BeforeEach
    public void beforeEach(){
        bookingRepository = mock(BookingRepository.class);
        customerRepository = mock(CustomerRepository.class);
        showRepository = mock(ShowRepository.class);
        userRepository = mock(UserRepository.class);
        bookingDate = Date.valueOf("2020-06-01");
        slot = new Slot("13:00-16:00", Time.valueOf("13:00:00"), Time.valueOf("16:00:00"));
        movie = new Movie(
                "tteefdu" ,
                "Bigil" , 100L ,
                "sport" , BigDecimal.valueOf(7.5) ,
                "https://m.media-amazon.com/images/M/MV5BMjI0MDMzNTQ0M15BMl5BanBnXkFtZTgwMTM5NzM3NDM@._V1_.jpg");
        screen = new Screen("GoldPlex");
        show = new Show(bookingDate, slot,screen,BigDecimal.valueOf(500), movie);
        bookingService = new BookingService(bookingRepository, customerRepository, showRepository,userRepository);
        user = new User("testUser", "testPassword" , "ROLE_CUSTOMER");
        customer =new Customer("Sakthi", "1234567890", "abc@gmail.com",user);
        bookingRequest = new BookingRequest(bookingDate, 1L,"Sakthi" ,  1);
    }

    @Test
    public void should_save_booking(){
        int noOfSeats = 2;
        Booking booking = new Booking(bookingDate, show, customer, 1, BigDecimal.valueOf(500));
        when(showRepository.findById(bookingRequest.getShowId())).thenReturn(java.util.Optional.of(show));
        Booking mockBooking = mock(Booking.class);
        when(bookingRepository.save(booking)).thenReturn(mockBooking);
        when(userRepository.findByUsernameIgnoreCase(bookingRequest.getUsername())).thenReturn(Optional.of(user));
        when(customerRepository.findByUserId(user.getId())).thenReturn(Optional.of(customer));
        Booking actualBooking = bookingService.book(bookingRequest);

        verify(bookingRepository).save(booking);
        assertThat(actualBooking, is(equalTo(mockBooking)));
    }

    @Test
    public void should_not_book_seat_when_seats_are_not_available() {
        when(bookingRepository.bookedSeatsByShow(show.getId())).thenReturn(TOTAL_NO_OF_SEATS);
        when(showRepository.findById(bookingRequest.getShowId())).thenReturn(Optional.of(show));
        assertThrows(NotEnoughSeatsException.class, () -> bookingService.book(bookingRequest));
        verifyNoInteractions(customerRepository);
        verify(bookingRepository, never()).save(any(Booking.class));
    }

}
