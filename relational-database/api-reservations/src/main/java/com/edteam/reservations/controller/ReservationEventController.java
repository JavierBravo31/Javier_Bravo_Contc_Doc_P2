package com.edteam.reservations.controller;

import com.edteam.reservations.model.ReservationEvent;
import com.edteam.reservations.util.ReservationFilters;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

@RestController
public class ReservationEventController {

    @GetMapping("/api/reservations/stream")
    public Flux<ReservationEvent> streamReservations() {
        return Flux.just(
                        new ReservationEvent("R001", "Javier Bravo", 120.50, List.of("javier@email.com")),
                        new ReservationEvent("R002", "Ana Torres", 0.0, List.of("ana@email.com")),
                        new ReservationEvent("R003", "Carlos Vera", 95.75, List.of("carlos@email.com")),
                        new ReservationEvent("R004", "Lucia Gomez", -15.0, List.of()),
                        new ReservationEvent("R005", "Mario Lopez", 210.99, List.of("mario@email.com"))
                )
                .delayElements(Duration.ofSeconds(1))
                .filter(ReservationFilters.VALID_RESERVATION)
                .doOnNext(ReservationFilters.PRINT_PROCESSED_EVENT)
                .defaultIfEmpty(
                        new ReservationEvent("DEFAULT", "Reserva Generica", 1.0, List.of("default@email.com"))
                );
    }
}