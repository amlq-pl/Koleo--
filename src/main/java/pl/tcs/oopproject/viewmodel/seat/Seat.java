package pl.tcs.oopproject.viewmodel.seat;

import pl.tcs.oopproject.viewmodel.carriage.Carriage;
import pl.tcs.oopproject.viewmodel.exception.IllegalOperationException;

public class Seat{
	private final SeatType seatType;
	private final int number;
	private Carriage carriage;
	
	boolean available = false;
	
	public Seat(SeatType seatType, int number) {
		this.seatType = seatType;
		this.number = number;
		carriage = null;
	}
	
	public void setAvailable(boolean available) {this.available = available;}
	
	public void setCarriage(Carriage carriage) {
		if(this.carriage == null)
			this.carriage = carriage;
		else
			throw new IllegalOperationException();
	}
	
	public SeatType getSeatType() {
		return seatType;
	}
	
	public int getNumber() {
		return number;
	}
	
	public int getCarriageNumber() {
		return carriage.getNumber();
	}
	
	public Carriage getCarriage() {
		return carriage;
	}
	
	public void display() {
		seatType.display();
		System.out.println("Seat Number: " + number);
	}
	
	public void displayCarriage() {
		if(carriage == null)
			System.out.println("ERROR: This seat is not connected to any carriage!");
		else
			carriage.display();
	}
}
