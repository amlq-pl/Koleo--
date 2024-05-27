package pl.tcs.oopproject.view.sceneControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.tcs.oopproject.view.ViewController;

public class LandingSceneController {
	public Button SignUpButton;
	public Button LogInButton;
	public TextField fromTextField;
	public TextField toTextField;
	public DatePicker departureDate;
	public Button confirmButton;
	
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
		newStage.show();
	}
}
