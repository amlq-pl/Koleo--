package pl.tcs.oopproject.model.carriage;

public record Carriage(CarriageClass carriageClass, CarriageType carriageType, int number, int numberOfSeats) {
	public void display() {
		carriageType.display();
		carriageClass.display();
		System.out.println("Carriage Number: " + number);
		System.out.println("Number of Seats: " + numberOfSeats);
	}
}
