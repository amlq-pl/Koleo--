package pl.tcs.oopproject.view.componentControllers;

import pl.tcs.oopproject.model.connection.MultiStopRoute;
import pl.tcs.oopproject.view.Basket;

public class TrainPaneFactory {
	public static TrainPane createTrainPane(MultiStopRoute con) {
		return new TrainPane(con);
	}
}
