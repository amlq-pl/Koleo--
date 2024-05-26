package pl.tcs.oopproject.view.componentControllers;

import pl.tcs.oopproject.viewmodel.connection.ConnectionWithTransfers;
import pl.tcs.oopproject.viewmodel.discount.Price;

import java.time.LocalDateTime;

public class TrainPaneFactory {
    public static TrainPane createTrainPane(ConnectionWithTransfers con) {
        return new TrainPane(con);
    }
}
