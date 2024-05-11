package pl.tcs.oopproject.viewmodel.seat;

import pl.tcs.oopproject.viewmodel.carriage.CarriageInterface;
import pl.tcs.oopproject.viewmodel.exception.IllegalOperationException;

public class Seat implements SeatInterface{
	private final SeatType seatType;
	private final int number;
	private CarriageInterface carriage;
	
	boolean available = false;
	
	public Seat(SeatType seatType, int number) {
		this.seatType = seatType;
		this.number = number;
		carriage = null;
	}
	
	public void setAvailable(boolean available) {this.available = available;}
	
	public void setCarriage(CarriageInterface carriage) {
		if(this.carriage == null)
			this.carriage = carriage;
		else
			throw new IllegalOperationException();
	}
	
	@Override
	public SeatType getSeatType() {
		return seatType;
	}
	
	@Override
	public int getNumber() {
		return number;
	}
	
	@Override
	public int getCarriageNumber() {
		return carriage.getNumber();
	}
	
	public CarriageInterface getCarriage() {
		return carriage;
	}
	
	@Override
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
