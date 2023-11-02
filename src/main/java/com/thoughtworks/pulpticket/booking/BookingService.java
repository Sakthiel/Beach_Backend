package com.thoughtworks.pulpticket.booking;

import com.thoughtworks.pulpticket.booking.exceptions.NotEnoughSeatsException;
import com.thoughtworks.pulpticket.booking.repository.Booking;
import com.thoughtworks.pulpticket.booking.repository.BookingRepository;
import com.thoughtworks.pulpticket.booking.repository.BookingRequest;
import com.thoughtworks.pulpticket.customer.repository.Customer;
import com.thoughtworks.pulpticket.customer.repository.CustomerRepository;
import com.thoughtworks.pulpticket.screen.repository.Screen;
import com.thoughtworks.pulpticket.screen.repository.ScreenRepository;
import com.thoughtworks.pulpticket.show.repository.Show;
import com.thoughtworks.pulpticket.show.repository.ShowRepository;
import com.thoughtworks.pulpticket.users.repository.User;
import com.thoughtworks.pulpticket.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.thoughtworks.pulpticket.show.repository.Constants.TOTAL_NO_OF_SEATS;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final ShowRepository showRepository;

    private final UserRepository userRepository;
    @Autowired
    public BookingService(BookingRepository bookingRepository, CustomerRepository customerRepository, ShowRepository showRepository ,
                      UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.showRepository = showRepository;
        this.userRepository = userRepository;
    }

   public Booking book(BookingRequest bookingRequest){
        Show show = showRepository.findById(bookingRequest.getShowId()).orElseThrow(() -> new EmptyResultDataAccessException("Show not found", 1));

        if (availableSeats(show) < bookingRequest.getNoOfSeats()) {
            throw new NotEnoughSeatsException("No seats available");
        }

        User user = userRepository.findByUsernameIgnoreCase(bookingRequest.getUsername()).orElseThrow(() -> new EmptyResultDataAccessException("User not found", 1));
        Customer customer = customerRepository.findByUserId(user.getId()).orElseThrow(() -> new EmptyResultDataAccessException("Customer not found", 1));
        BigDecimal amountPaid = show.costFor(bookingRequest.getNoOfSeats());
        return bookingRepository.save(new Booking(bookingRequest.getDate(), show, customer, bookingRequest.getNoOfSeats(), amountPaid ));
   }

    private long availableSeats(Show show) {
        Integer bookedSeats = bookingRepository.bookedSeatsByShow(show.getId());
        if (noSeatsBooked(bookedSeats))
            return TOTAL_NO_OF_SEATS;

        return TOTAL_NO_OF_SEATS - bookedSeats;
    }

    private boolean noSeatsBooked(Integer bookedSeats) {
        return bookedSeats == null;
    }

}
