package com.thoughtworks.pulpticket.movie.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    private String id;
    private String name;
    private Long duration ;

    private String genre;

    private BigDecimal rating;

    private String poster;

    public Movie() {
    }

    public Movie(String id, String name, Long duration, String genre, BigDecimal rating, String poster) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.genre = genre;
        this.rating = rating;
        this.poster = poster;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
