package pl.tcs.oopproject.viewmodel.connection;

import pl.tcs.oopproject.model.connection.ConnectionWithTransfers;

import java.sql.SQLException;
import java.util.List;

public interface FindConnectionInterface {
	List<ConnectionWithTransfers> getRoutes() throws SQLException; //default configuration
	
	List<ConnectionWithTransfers> getCheapRoutes() throws SQLException; //sorted by cost
	
	List<ConnectionWithTransfers> getRoutesWithoutTransfers() throws SQLException;
}
