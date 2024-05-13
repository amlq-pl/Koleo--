package pl.tcs.oopproject.view.sceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import net.synedra.validatorfx.Validator;
import pl.tcs.oopproject.viewmodel.users.Person;
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
    Validator validator = new Validator();
    public Button LogInButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        validator.createCheck()
                .dependsOn("login", LogInInputField.textProperty())
                .withMethod(c-> {
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


    public void LogInAction(ActionEvent actionEvent) {
        if (validator.validate()) {
            try {
                Person newPerson = PersonFactory.logIn(LogInInputField.getText(), PasswordInputField.getText());
                newPerson.display();
            } catch (Exception e) {
                System.out.println("wrong username or passwd");
            }
        } else  {
            ErrorLabel.setText("Spróbuj raz jeszcze");
        }
    }

    public void BackButtonClick() {
    }

    public void ExitButtonClick() {
    }
}