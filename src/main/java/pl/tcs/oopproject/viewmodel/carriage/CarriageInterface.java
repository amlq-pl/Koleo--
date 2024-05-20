package pl.tcs.oopproject.viewmodel.carriage;

public interface CarriageInterface {
	CarriageClassType getClassType(); //first or second
	
	CarriageType getCarriageType(); //single compartment or compartment
	
	int getNumber();
	
	void display();
	
	
	
}
