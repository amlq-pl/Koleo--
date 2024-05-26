package pl.tcs.oopproject.viewmodel.carriage;

import pl.tcs.oopproject.viewmodel.exception.IllegalOperationException;
import pl.tcs.oopproject.viewmodel.seat.Seat;
import pl.tcs.oopproject.viewmodel.seat.SeatType;

import java.util.HashSet;

public class Carriage{
	private final CarriageClassType carriageClassType;
	private final CarriageType carriageType;
	private final int number;
	private final int numberOfSeats;
	
	HashSet<Seat> seats;
	
	public Carriage(CarriageClassType carriageClassType, CarriageType carriageType, int number, int numberOfSeats) {
		this.carriageClassType = carriageClassType;
		this.carriageType = carriageType;
		this.number = number;
		this.numberOfSeats = numberOfSeats;
	}
	
	public void addSeat(Seat seat) {
		if(carriageType.equals(CarriageType.WARS)) throw new IllegalArgumentException();
		if(carriageType.equals(CarriageType.SLEEPER) && !seat.getSeatType().equals(SeatType.BERTH))
			throw new IllegalArgumentException();
		if(!carriageType.equals(CarriageType.SLEEPER) && seat.getSeatType().equals(SeatType.BERTH))
			throw new IllegalArgumentException();

		if(seats == null) seats = new HashSet<>();
		if(seats.size() >= numberOfSeats && !seats.contains(seat)) throw new IllegalOperationException();
		if(seat.getCarriage() != null) return;
		seat.setCarriage(this);
		seats.add(seat);
	}
	
	public void addSeat(Seat... seat) {
		if(seats == null) seats = new HashSet<>();
		int x = 0;
		for(Seat s : seat)
			if(!seats.contains(s))
				++x;
		if(x + seats.size() > numberOfSeats)
			throw new IllegalOperationException();
		
		for(Seat s : seat) {
			addSeat(s);
		}
	}
	
	public CarriageClassType getClassType() {
		return carriageClassType;
	}
	
	public CarriageType getCarriageType() {
		return carriageType;
	}
	
	public int getNumber() {
		return number;
	}
	
	public int getNumberOfSeats() {
		return numberOfSeats;
	}
	
	public void display() {
		carriageType.display();
		carriageClassType.display();
		System.out.println("Carriage Number: " + number);
		System.out.println("Number of Seats: " + numberOfSeats);
	}
	
	public void displaySeats() {
		System.out.println("Carriage " + number);
		for(Seat s : seats) {
			s.display();
		}
		
	}
}
