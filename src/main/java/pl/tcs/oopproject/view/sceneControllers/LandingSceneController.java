package pl.tcs.oopproject.view.sceneControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import pl.tcs.oopproject.App;
import pl.tcs.oopproject.model.ticket.LongTermTicketType;
import pl.tcs.oopproject.model.ticket.TicketFactory;
import pl.tcs.oopproject.view.ViewController;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LandingSceneController implements Initializable {
	private final ObservableList<String> ObservableStations = FXCollections.observableArrayList(App.Stations);
	public Button SignUpButton;
	public Button LogInButton;
	public Button confirmButton;
	public ComboBox<String> DepStation;
	public ComboBox<String> ArrStation;
	public ComboBox<String> HourPicker;
	public javafx.scene.control.DatePicker DatePicker;
	
	@FXML
	protected void SignUpClicked() {
		Stage thisStage = (Stage) SignUpButton.getScene().getWindow();
		thisStage.close();
		Stage newStage = new Stage();
		newStage.setScene(ViewController.getSignUpScene());
		newStage.show();
	}
	
	@FXML
	protected void LogInClicked() {
		Stage thisStage = (Stage) LogInButton.getScene().getWindow();
		thisStage.close();
		Stage newStage = new Stage();
		newStage.setScene(ViewController.getLogInScene());
		newStage.show();
	}
	
	@FXML
	public void confirmButtonClick() {
		Stage thisStage = (Stage) confirmButton.getScene().getWindow();
		thisStage.close();
		Stage newStage = new Stage();
		newStage.setScene(ViewController.getTrainSearchScene());
		TrainSearchSceneController.setInitialData(DepStation.getValue(), ArrStation.getValue(), DatePicker.getValue(), HourPicker.getValue());
		newStage.show();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		ArrayList<String> hours = IntStream.range(0, 24)
				.boxed()
				.flatMap(hour -> Stream.of(
						String.format("%02d:00", hour),  // Full hour
						String.format("%02d:30", hour)
				))
				.collect(Collectors.toCollection(ArrayList::new));
		
		HourPicker.getItems().setAll(hours);
		DepStation.getItems().setAll(App.Stations);
		ArrStation.getItems().setAll(App.Stations);
		DatePicker.setValue(LocalDate.now());
	}
}
