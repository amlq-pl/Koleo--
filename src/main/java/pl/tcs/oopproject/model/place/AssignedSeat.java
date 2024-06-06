package pl.tcs.oopproject.model.place;

import pl.tcs.oopproject.model.carriage.Carriage;
import pl.tcs.oopproject.model.seat.Seat;

public record AssignedSeat(Carriage carriage, Seat seat) {
	public final static AssignedSeat TRAIN_WITHOUT_RESERVATION = new AssignedSeat(null, null);
	
}