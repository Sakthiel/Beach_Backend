package com.thoughtworks.pulpticket.booking.repository;

import com.thoughtworks.pulpticket.customer.repository.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking , Long> {
    @Query(value = "SELECT SUM(NO_OF_SEATS) FROM BOOKING WHERE SHOW_ID=?1", nativeQuery = true)
    Integer bookedSeatsByShow(Long showId);


}
