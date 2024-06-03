package pl.tcs.oopproject.model.place;

import pl.tcs.oopproject.model.carriage.Carriage;
import pl.tcs.oopproject.model.seat.Seat;

public record SpecificSeat(Carriage carriage, Seat seat) { }
