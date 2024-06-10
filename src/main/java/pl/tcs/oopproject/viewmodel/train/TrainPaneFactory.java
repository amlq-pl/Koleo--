package pl.tcs.oopproject.viewmodel.train;

import pl.tcs.oopproject.model.connection.MultiStopRoute;
import pl.tcs.oopproject.view.componentControllers.train.TrainPane;

public class TrainPaneFactory {
    public static TrainPane createTrainPane(MultiStopRoute con) {
        return new TrainPane(con);
    }
}
