package pl.tcs.oopproject.view.componentControllers;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import pl.tcs.oopproject.App;
import pl.tcs.oopproject.model.connection.ScheduledTrain;
import pl.tcs.oopproject.model.station.Station;

import java.util.ArrayList;
import java.util.List;

public class StationPane extends AnchorPane {
	@FXML
	private Label TrainInfo;
	@FXML
	private VBox StationsView;
	@FXML
	private final ObservableList<Station> observableList = FXCollections.observableArrayList();
	
	private StationPane(ArrayList<Station> list, ScheduledTrain train) {
		FXMLLoader loader = new FXMLLoader(App.class.getResource("components/station-pane.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
			TrainInfo.textProperty().setValue(train.getCompany() + " " + train.getNumber());
			
			observableList.addListener((ListChangeListener<? super Station>) change -> {
				while (change.next()) {
					if (change.wasAdded()) {
						List<AnchorPane> tempList = new ArrayList<>();
						for (Station s : change.getAddedSubList()) {
							tempList.add(new BasicStationPane(s));
						}
						StationsView.getChildren().addAll(tempList);
					}
				}
			});
			
			observableList.addAll(list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static StationPane createStationPane(ArrayList<Station> list, ScheduledTrain train) {
		return new StationPane(list, train);
	}
}
