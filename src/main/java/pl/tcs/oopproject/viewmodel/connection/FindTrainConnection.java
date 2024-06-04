package pl.tcs.oopproject.viewmodel.connection;

import pl.tcs.oopproject.model.connection.MultiStopRoute;

import java.sql.SQLException;
import java.util.List;

public interface FindTrainConnection {
	List<MultiStopRoute> findTrainRoutes() throws SQLException; //default configuration
	
	List<MultiStopRoute> findCheapestTrainRoutes() throws SQLException; //sorted by cost
	
	List<MultiStopRoute> getDirectTrainRoutes() throws SQLException;
}
