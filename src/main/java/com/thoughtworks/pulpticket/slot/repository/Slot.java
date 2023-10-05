package com.thoughtworks.pulpticket.slot.repository;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thoughtworks.pulpticket.utilities.serializers.time.TimeSerializer;
import jakarta.persistence.*;

import java.sql.Time;

@Entity
@Table(name = "slot")
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name ;

    @Column(nullable = false)
    @JsonSerialize(using = TimeSerializer.class)
    private Time startTime;

    @Column(nullable = false)
    @JsonSerialize(using = TimeSerializer.class)
    private Time endTime;

    public Slot() {
    }

    public Slot(String name, Time startTime, Time endTime) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
}
