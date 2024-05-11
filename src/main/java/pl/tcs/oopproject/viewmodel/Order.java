package pl.tcs.oopproject.viewmodel;

import pl.tcs.oopproject.viewmodel.carriage.CarriageInterface;
import pl.tcs.oopproject.viewmodel.connection.ConnectionWithTransfers;
import pl.tcs.oopproject.viewmodel.seat.SeatInterface;

public abstract class Order {
	ConnectionWithTransfers connectionAbstract;
	int discount;
	
	Order(ConnectionWithTransfers connectionAbstract, int discount) {
		this.connectionAbstract = connectionAbstract;
		this.discount = discount;
	}
	
	abstract CarriageInterface getCarriage(); //might be null
	
	abstract SeatInterface getSeat(); //might be null
	
	abstract String TicketType(); //czy miesięczny, czy jako
	
	public int getCost() {return 0;} //TO DO
	
}
