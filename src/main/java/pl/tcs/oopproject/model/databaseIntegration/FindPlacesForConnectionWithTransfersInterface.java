package pl.tcs.oopproject.model.databaseIntegration;

import pl.tcs.oopproject.model.connection.MultiStopRoute;
import pl.tcs.oopproject.model.place.Place;

import java.sql.SQLException;

public interface FindPlacesForConnectionWithTransfersInterface {
	Place findPlacesForConnectionWithTransfers(MultiStopRoute multiStopRoute) throws SQLException;
}
