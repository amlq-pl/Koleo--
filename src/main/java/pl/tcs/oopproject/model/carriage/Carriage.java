package pl.tcs.oopproject.model.carriage;

public record Carriage(CarriageClassType carriageClassType, CarriageType carriageType, int number, int numberOfSeats) {
	public void display() {
		carriageType.display();
		carriageClassType.display();
		System.out.println("Carriage Number: " + number);
		System.out.println("Number of Seats: " + numberOfSeats);
	}
}
