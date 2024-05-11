package pl.tcs.oopproject.viewmodel;

import pl.tcs.oopproject.viewmodel.carriage.CarriageInterface;
import pl.tcs.oopproject.viewmodel.findconnection.Connection;
import pl.tcs.oopproject.viewmodel.seat.SeatInterface;

public abstract class Order {
	Connection connection;
	int discount;
	
	Order(Connection connection, int discount) {
		this.connection = connection;
		this.discount = discount;
	}
	
	abstract CarriageInterface getCarriage(); //might be null
	
	abstract SeatInterface getSeat(); //might be null
	
	abstract String TicketType(); //czy miesięczny, czy jako
	
	public int getCost() {return 0;} //TO DO
	
}
