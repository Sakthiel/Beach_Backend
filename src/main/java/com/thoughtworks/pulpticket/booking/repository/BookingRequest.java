package com.thoughtworks.pulpticket.booking.repository;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

import java.sql.Date;

import static com.thoughtworks.pulpticket.show.repository.Constants.MAX_SEATS_PER_BOOKING;
import static com.thoughtworks.pulpticket.show.repository.Constants.MIN_SEATS_PER_BOOKING;

public class BookingRequest {
    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;

    @JsonProperty
    private Long showId;

    @JsonProperty
    private String username;

    @JsonProperty
    @DecimalMax(value = MAX_SEATS_PER_BOOKING, message = "Maximum {value} seats allowed per booking")
    @DecimalMin(value = MIN_SEATS_PER_BOOKING, message = "Maximum {value} seats allowed per booking")
    private int noOfSeats;

    public BookingRequest() {
    }

    public BookingRequest(Date date, Long showId, String username, int noOfSeats) {
        this.date = date;
        this.showId = showId;
        this.username = username;
        this.noOfSeats = noOfSeats;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getShowId() {
        return showId;
    }

    public void setShowId(Long showId) {
        this.showId = showId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }
}
