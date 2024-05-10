package pl.tcs.oopproject.viewmodel.findconnection;

import java.util.List;

public interface FindConnection {
	List<Connection> getRoutes(); //default configuration
	
	List<Connection> getCheapRoutes(); //sorted by cost
	
	List<Connection> getRoutesWithoutTranfers();
}
