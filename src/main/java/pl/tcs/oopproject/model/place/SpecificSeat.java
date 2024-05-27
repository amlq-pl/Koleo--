package pl.tcs.oopproject.model.place;

import pl.tcs.oopproject.model.carriage.Carriage;
import pl.tcs.oopproject.model.seat.Seat;

public class SpecificSeat {
	private Carriage carriage;
	private Seat seat;
	
	public SpecificSeat(Carriage carriage, Seat seat) {
		this.carriage = carriage;
		this.seat = seat;
	}
	
	public Seat getSeat() {
		return seat;
	}
	
	public void setSeat(Seat seat) {
		this.seat = seat;
	}
	
	public Carriage getCarriage() {
		return carriage;
	}
	
	public void setCarriage(Carriage carriage) {
		this.carriage = carriage;
	}
}
