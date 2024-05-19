package pl.tcs.oopproject.viewmodel;

import pl.tcs.oopproject.viewmodel.carriage.Carriage;
import pl.tcs.oopproject.viewmodel.findconnection.Connection;
import pl.tcs.oopproject.viewmodel.seat.Seat;

public abstract class Order {
	Connection connection;
	int discount;
	
	Order(Connection connection, int discount) {
		this.connection = connection;
		this.discount = discount;
	}
	
	abstract Carriage getCarriage(); //might be null
	
	abstract Seat getSeat(); //might be null
	
	abstract String TicketType(); //czy miesięczny, czy jako
	
	public int getCost() {return 0;} //TO DO
	
}
