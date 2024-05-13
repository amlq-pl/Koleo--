package pl.tcs.oopproject.viewmodel.tickets;

import pl.tcs.oopproject.viewmodel.carriage.CarriageInterface;
import pl.tcs.oopproject.viewmodel.connection.ConnectionWithTransfers;
import pl.tcs.oopproject.viewmodel.seat.SeatInterface;

public abstract class Order {
	ConnectionWithTransfers connection;
	int discount;
	
	Order(ConnectionWithTransfers connection, int discount) {
		this.connection = connection;
		this.discount = discount;
	}
	
	abstract CarriageInterface getCarriage(); //might be null
	
	abstract SeatInterface getSeat(); //might be null
	
	abstract String TicketType(); //czy miesięczny, czy jako
	
	public int getCost() {return 0;} //TO DO
	
}
