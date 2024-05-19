package pl.tcs.oopproject.viewmodel.carriage;

public abstract class AbstractCarriageDecorator implements Carriage {
	Carriage carriage;
	
	public AbstractCarriageDecorator(Carriage carriage) {
		this.carriage = carriage;
	}
	
	
	@Override
	public CarriageClassType getClassType(){
		return carriage.getClassType();
	}
	
	
	@Override
	public CarriageType getCarriageType() {
		return carriage.getCarriageType();
	}
	
	@Override
	public int getNumber(){
		return carriage.getNumber();
	}

}
