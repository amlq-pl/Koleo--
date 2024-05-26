package pl.tcs.oopproject.postgresDatabaseIntegration;

import pl.tcs.oopproject.model.databaseIntegration.FindPlacesForConnectionWithTransfersInterface;
import pl.tcs.oopproject.viewmodel.connection.ConnectionWithTransfers;
import pl.tcs.oopproject.viewmodel.connection.DirectConnection;
import pl.tcs.oopproject.viewmodel.place.Place;
import pl.tcs.oopproject.viewmodel.place.SpecificSeat;

import java.util.ArrayList;

public class FindPlacesForConnectionWithTransfers implements FindPlacesForConnectionWithTransfersInterface {
    @Override
    public Place findPlacesForConnectionWithTransfers(ConnectionWithTransfers connectionWithTransfers) {
        ArrayList<SpecificSeat> places = new ArrayList<>();
        for(int i=0;i<connectionWithTransfers.getTrains().size();i++) {
            places.add(findSpecificSeat(connectionWithTransfers.getTrains().get(i)));
        }
        return new Place(connectionWithTransfers,places);
    }

    private SpecificSeat findSpecificSeat(DirectConnection directConnection) {
        return null;
    }
}
