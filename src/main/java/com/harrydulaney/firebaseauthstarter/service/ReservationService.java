package com.harrydulaney.firebaseauthstarter.service;

import com.harrydulaney.firebaseauthstarter.model.BaseModelDto;
import com.harrydulaney.firebaseauthstarter.model.Reservation;
import com.harrydulaney.firebaseauthstarter.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("ReservationService")
public class ReservationService {

    private ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository dataRepository) {
        this.reservationRepository = dataRepository;
    }

    public List<BaseModelDto> getReservations(BigDecimal id) {
        return new ArrayList<>();
    }

    public List<Reservation> save(List<Reservation> reservation) {
        return new ArrayList<>();
    }
}
