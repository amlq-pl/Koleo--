package pl.tcs.oopproject.viewmodel.connection;

import java.util.List;

public interface FindConnectionInterface {
	List<ConnectionWithTransfers> getRoutes(); //default configuration
	
	List<ConnectionWithTransfers> getCheapRoutes(); //sorted by cost
	
	List<ConnectionWithTransfers> getRoutesWithoutTransfers();
}
