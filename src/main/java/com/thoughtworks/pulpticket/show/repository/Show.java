package com.thoughtworks.pulpticket.show.repository;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thoughtworks.pulpticket.movie.repository.Movie;
import com.thoughtworks.pulpticket.screen.repository.Screen;
import com.thoughtworks.pulpticket.slot.repository.Slot;
import com.thoughtworks.pulpticket.utilities.serializers.date.DateSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "show")
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @JsonSerialize(using = DateSerializer.class)
    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    private Slot slot;

    @ManyToOne
    @JoinColumn(name = "screen_id")
    private Screen screen;

    @Column(nullable = false)
    @DecimalMin(value = "0.0", inclusive = false, message = "Cost should be greater than {value}")
    @Digits(integer = 4, fraction = 2, message = "Cost can have at most {integer} integral digits, and {fraction} fractional digits")
    private BigDecimal cost;
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    public Show() {
    }

    public Show(Date date, Slot slot, Screen screen, BigDecimal cost, Movie movie) {
        this.date = date;
        this.slot = slot;
        this.screen = screen;
        this.cost = cost;
        this.movie = movie;
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

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
