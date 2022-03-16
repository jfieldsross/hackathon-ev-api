package com.harrydulaney.firebaseauthstarter.controller;

import com.harrydulaney.firebaseauthstarter.model.BaseModelDto;
import com.harrydulaney.firebaseauthstarter.model.Reservation;
import com.harrydulaney.firebaseauthstarter.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class ReservationController {

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("reservations/{id}")
    public ResponseEntity<List<BaseModelDto>> getReservations(@PathVariable BigDecimal id) {
        List<BaseModelDto> reservations = reservationService.getReservations(id);
        if (reservations == null || reservations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @PutMapping(value = "books", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Reservation>> save(@RequestBody List<Reservation> reservation) {
        List<Reservation> reservations = this.reservationService.save(reservation);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

}
