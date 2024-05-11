package pl.tcs.oopproject.viewmodel.carriage;

import pl.tcs.oopproject.viewmodel.seat.Seat;
import pl.tcs.oopproject.viewmodel.seat.SeatInterface;

import java.util.List;

public class Carriage implements CarriageInterface{
	private final CarriageClassType carriageClassType;
	private final CarriageType carriageType;
	private final int number;
	private final int numberOfSeats;
	
	List<SeatInterface> seats;
	
	public Carriage(CarriageClassType carriageClassType, CarriageType carriageType, int number, int numberOfSeats) {
		this.carriageClassType = carriageClassType;
		this.carriageType = carriageType;
		this.number = number;
		this.numberOfSeats = numberOfSeats;
	}
	
	public void addSeat(Seat seat) {
	
	}
	
	@Override
	public CarriageClassType getClassType() {
		return carriageClassType;
	}
	
	@Override
	public CarriageType getCarriageType() {
		return carriageType;
	}
	
	@Override
	public int getNumber() {
		return number;
	}
	
	public int getNumberOfSeats() {
		return numberOfSeats;
	}

	@Override
	public void display() {
		carriageType.display();
		carriageClassType.display();
		System.out.println("Carriage Number: " + number);
		System.out.println("Number of Seats: " + numberOfSeats);
	}
}
