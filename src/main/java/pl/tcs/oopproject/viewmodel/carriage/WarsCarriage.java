package pl.tcs.oopproject.viewmodel.carriage;

public class WarsCarriage extends AbstractCarriageDecorator {
	public WarsCarriage(Carriage carriage) {
		super(carriage);
	}
	
	@Override
	public void display() {
		System.out.println("Wars Carriage number " + super.getNumber());
	}
}
