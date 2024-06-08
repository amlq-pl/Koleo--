package pl.tcs.oopproject.model.carriage;

public record Carriage(CarriageClass carriageClass, CarriageType carriageType, int number, int numberOfSeats) {
	@Override
	public String toString() {
		return carriageClass + "\n" + carriageType + " " + number;
				
	}
}
