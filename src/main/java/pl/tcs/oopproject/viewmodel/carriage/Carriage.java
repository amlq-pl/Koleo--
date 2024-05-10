package pl.tcs.oopproject.viewmodel.carriage;

public interface Carriage {
	public CarriageClassType getClassType(); //first or second
	
	public CarriageType getCarriageType(); //single compartment or compartment
	
	public int getNumber();
	
}
