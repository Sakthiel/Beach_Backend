package com.thoughtworks.pulpticket.booking.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thoughtworks.pulpticket.utilities.serializers.date.DateSerializer;
import com.thoughtworks.pulpticket.utilities.serializers.time.TimeSerializer;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

public class BookingConfirmationResponse {
    @JsonProperty
    private String customerName;
    @JsonProperty
    @JsonSerialize(using = DateSerializer.class)
    private Date date;
    @JsonProperty
    @JsonSerialize(using = TimeSerializer.class)
    private Time startTime;
    @JsonProperty
    private BigDecimal amountPaid;
    @JsonProperty
    private int noOfSeats;

    public BookingConfirmationResponse() {
    }

    public BookingConfirmationResponse(String customerName, Date date, Time startTime, BigDecimal amountPaid, int noOfSeats) {
        this.customerName = customerName;
        this.date = date;
        this.startTime = startTime;
        this.amountPaid = amountPaid;
        this.noOfSeats = noOfSeats;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public int getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }
}
