package com.pilvadim.teplota.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="temperatures")
public class Temperature {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Float celsius;

    @Transient
    @Column(name = "placeid")
    private Integer placeId;

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime moment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getCelsius() {
        return celsius;
    }

    public void setValue(Float celsius) {
        this.celsius = celsius;
    }

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public LocalDateTime getMoment() {
        return moment;
    }

    public void setMoment(LocalDateTime moment) {
        this.moment = moment;
    }
}
