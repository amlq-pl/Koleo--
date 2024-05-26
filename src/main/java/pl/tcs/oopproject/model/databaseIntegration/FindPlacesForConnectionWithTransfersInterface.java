package pl.tcs.oopproject.model.databaseIntegration;

import pl.tcs.oopproject.viewmodel.connection.ConnectionWithTransfers;
import pl.tcs.oopproject.viewmodel.place.Place;

public interface FindPlacesForConnectionWithTransfersInterface {
    Place findPlacesForConnectionWithTransfers(ConnectionWithTransfers connectionWithTransfers);
}
