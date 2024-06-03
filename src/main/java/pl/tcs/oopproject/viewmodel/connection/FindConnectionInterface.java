package pl.tcs.oopproject.viewmodel.connection;

import pl.tcs.oopproject.model.connection.MultiStopRoute;

import java.sql.SQLException;
import java.util.List;

public interface FindConnectionInterface {
	List<MultiStopRoute> getRoutes() throws SQLException; //default configuration
	
	List<MultiStopRoute> getCheapRoutes() throws SQLException; //sorted by cost
	
	List<MultiStopRoute> getRoutesWithoutTransfers() throws SQLException;
}
