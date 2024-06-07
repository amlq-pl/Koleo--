package pl.tcs.oopproject.model.carriage;

public record Carriage(CarriageClass carriageClass, CarriageType carriageType, int number, int numberOfSeats) {
	@Override
	public String toString() {
		return carriageType.toString() + "\n"
				+ carriageClass.toString() + "\n"
				+ "Carriage Number: " + number + "\n" +
				"Number of Seats: " + numberOfSeats;
				
	}
}
