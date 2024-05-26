package pl.tcs.oopproject.view.componentControllers;

import pl.tcs.oopproject.viewmodel.connection.ConnectionWithTransfers;

public class TrainPaneFactory {
    public static TrainPane createTrainPane(ConnectionWithTransfers con) {
        return new TrainPane(con);
    }
}
