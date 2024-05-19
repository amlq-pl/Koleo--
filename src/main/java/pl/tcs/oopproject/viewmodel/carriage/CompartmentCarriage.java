package pl.tcs.oopproject.viewmodel.carriage;

public class CompartmentCarriage extends AbstractCarriageDecorator implements  CarriageWithSeats{
	public CompartmentCarriage(Carriage carriage) {
		super(carriage);
	}
	
	@Override
	public void display() {
	
	}
	
	@Override
	public int NumberOfSeats() {
		return 0;
	}
}
