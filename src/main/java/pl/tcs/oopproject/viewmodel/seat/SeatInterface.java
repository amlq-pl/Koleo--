package pl.tcs.oopproject.viewmodel.seat;

public interface SeatInterface {
	SeatType getSeatType(); //return seat type - window, middle, corridor, sleeper
	
	int getNumber();
	
	int getCarriageNumber();
	
	void display();
}
