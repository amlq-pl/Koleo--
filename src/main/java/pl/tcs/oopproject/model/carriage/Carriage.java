package pl.tcs.oopproject.model.carriage;

public class Carriage {
	private final CarriageClassType carriageClassType;
	private final CarriageType carriageType;
	private final int number;
	private final int numberOfSeats;
	
	public Carriage(CarriageClassType carriageClassType, CarriageType carriageType, int number, int numberOfSeats) {
		this.carriageClassType = carriageClassType;
		this.carriageType = carriageType;
		this.number = number;
		this.numberOfSeats = numberOfSeats;
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
	}
}
