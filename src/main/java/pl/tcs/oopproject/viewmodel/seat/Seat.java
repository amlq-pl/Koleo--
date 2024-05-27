package pl.tcs.oopproject.viewmodel.seat;

public class Seat {
	private final SeatType seatType;
	private final int number;
	
	boolean available = false;
	
	public Seat(SeatType seatType, int number) {
		this.seatType = seatType;
		this.number = number;
	}
	
	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	public SeatType getSeatType() {
		return seatType;
	}
	
	public int getNumber() {
		return number;
	}
	
	public void display() {
		seatType.display();
		System.out.println("Seat Number: " + number);
	}
}
