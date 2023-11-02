package com.thoughtworks.pulpticket.booking.controller;

import com.thoughtworks.pulpticket.booking.BookingService;
import com.thoughtworks.pulpticket.booking.model.BookingConfirmationResponse;
import com.thoughtworks.pulpticket.booking.repository.Booking;
import com.thoughtworks.pulpticket.booking.repository.BookingRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {
    BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    @PostMapping("/bookings")
    public ResponseEntity<BookingConfirmationResponse> createBooking(@Valid @RequestBody BookingRequest bookingRequest){
        Booking booking = bookingService.book(bookingRequest);
        BookingConfirmationResponse response = constructBookingConfirmation(booking);
        return new ResponseEntity<>(response , HttpStatus.CREATED);
    }

    private BookingConfirmationResponse constructBookingConfirmation(Booking booking) {
        BookingConfirmationResponse response = new BookingConfirmationResponse();
        response.setCustomerName(booking.getCustomer().getName());
        response.setDate(booking.getDate());
        response.setStartTime(booking.getShow().getSlot().getStartTime());
        response.setAmountPaid(booking.getAmountPaid());
        response.setNoOfSeats(booking.getNoOfSeats());
        return response;

    }
}
