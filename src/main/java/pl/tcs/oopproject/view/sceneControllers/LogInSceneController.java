package pl.tcs.oopproject.view.sceneControllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.synedra.validatorfx.Validator;
import pl.tcs.oopproject.model.users.Person;
import pl.tcs.oopproject.view.ViewController;
import pl.tcs.oopproject.viewmodel.users.PersonFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class LogInSceneController implements Initializable {
	public TextField LogInInputField;
	public PasswordField PasswordInputField;
	public Label ErrorLabel;
	public Label welcomeText;
	public Button BackButton;
	public Button CloseButton;
	public Button LogInButton;
	Validator validator = new Validator();
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		ErrorLabel.setWrapText(true);
		validator.createCheck()
				.dependsOn("login", LogInInputField.textProperty())
				.withMethod(c -> {
					String login = c.get("login");
					if (login.isEmpty()) {
						c.error("This cannot be empty!");
					}
				})
				.decorates(LogInInputField)
				.immediate();
		
		validator.createCheck()
				.dependsOn("passwd", PasswordInputField.textProperty())
				.withMethod(c -> {
					String password = c.get("passwd");
					if (password.isEmpty()) {
						c.error("Password cannot be empty!");
					}
				})
				.decorates(PasswordInputField)
				.immediate();
	}
	
	
	public void LogInAction() {
		if (validator.validate()) {
			try {
				Person newPerson = PersonFactory.logIn(LogInInputField.getText(), PasswordInputField.getText());
				System.out.println(newPerson);
				LogInInputField.setText("");
				PasswordInputField.setText("");
				Stage thisStage = (Stage) BackButton.getScene().getWindow();
				thisStage.close();
				Stage newStage = new Stage();
				newStage.setScene(ViewController.getLandingScene());
				newStage.show();
			} catch (Exception e) {
				ErrorLabel.setText("Nieprawidłowa nazwa użykownika lub hasło");
			}
		} else {
			ErrorLabel.setText("Spróbuj raz jeszcze");
		}
	}
	
	public void BackButtonClick() {
		Stage thisStage = (Stage) BackButton.getScene().getWindow();
		thisStage.close();
		Stage newStage = new Stage();
		newStage.setScene(ViewController.getLandingScene());
		newStage.show();
	}
	
	public void ExitButtonClick() {
		Stage thisStage = (Stage) CloseButton.getScene().getWindow();
		thisStage.close();
	}
}