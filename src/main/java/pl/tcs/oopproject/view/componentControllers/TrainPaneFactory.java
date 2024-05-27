package pl.tcs.oopproject.view.componentControllers;

import pl.tcs.oopproject.model.connection.ConnectionWithTransfers;
import pl.tcs.oopproject.view.Basket;
public class TrainPaneFactory {
	public static TrainPane createTrainPane(ConnectionWithTransfers con, Basket basket) {
		return new TrainPane(con, basket);
	}
}
