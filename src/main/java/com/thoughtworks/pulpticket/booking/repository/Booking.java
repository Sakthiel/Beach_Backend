package com.thoughtworks.pulpticket.booking.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thoughtworks.pulpticket.customer.repository.Customer;
import com.thoughtworks.pulpticket.screen.repository.Screen;
import com.thoughtworks.pulpticket.show.repository.Show;
import com.thoughtworks.pulpticket.utilities.serializers.date.DateSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;

    @Column(nullable = false)
    @JsonProperty
    @JsonSerialize(using = DateSerializer.class)
    @NotNull(message = "Date must be provided")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "show_id")
    @JsonProperty
    @NotNull(message = "Show must be provided")
    private Show show;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonProperty
    @NotNull(message = "Customer must be provided")
    private Customer customer;

    @Column(name = "no_of_seats", nullable = false)
    @JsonProperty
    @DecimalMin(value = "0", inclusive = false, message = "Seats should be greater than {value}")
    @DecimalMax(value = "10" , inclusive = true , message = "Seats should be equal to or greater than 10")
    @NotNull(message = "No of seats must be provided")
    private Integer noOfSeats;

    @Column(name = "amount_paid", nullable = false)
    @JsonProperty
    @DecimalMin(value = "0", inclusive = false, message = "Amount paid should be greater than {value}")
    @NotNull(message = "Amount paid must be provided")
    private BigDecimal amountPaid;

    public Booking(Date date, Show show, Customer customer, Integer noOfSeats, BigDecimal amountPaid) {
        this.date = date;
        this.show = show;
        this.customer = customer;
        this.noOfSeats = noOfSeats;
        this.amountPaid = amountPaid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Integer getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(Integer noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id) &&
                Objects.equals(date, booking.date) &&
                Objects.equals(show, booking.show) &&
                Objects.equals(customer, booking.customer) &&
                Objects.equals(noOfSeats, booking.noOfSeats) &&
                Objects.equals(amountPaid, booking.amountPaid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, show, customer, noOfSeats, amountPaid);
    }
}
