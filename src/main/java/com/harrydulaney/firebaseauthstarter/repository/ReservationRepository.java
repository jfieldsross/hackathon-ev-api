package com.harrydulaney.firebaseauthstarter.repository;

import com.harrydulaney.firebaseauthstarter.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, BigDecimal> {
}
