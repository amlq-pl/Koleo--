package pl.tcs.oopproject.viewmodel.seat;

public interface SeatInterface {
	public SeatType getSeatType(); //return seat type - window, middle, corridor, sleeper
	
	public int getNumber();
	
	public int getCarriageNumber();
}
