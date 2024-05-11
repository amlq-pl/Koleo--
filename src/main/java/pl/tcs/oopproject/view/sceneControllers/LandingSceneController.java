package pl.tcs.oopproject.view.sceneControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import net.synedra.validatorfx.Validator;
import pl.tcs.oopproject.viewmodel.users.Person;
import pl.tcs.oopproject.viewmodel.users.PersonFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.jar.Attributes;

public class LandingSceneController implements Initializable {
    private final Validator validator = new Validator();
    public TextField NameTextField;
    public TextField SurnameTextField;
    public DatePicker BirthDatePicker;
    public TextField EmailTextField;
    public TextField PhoneNumberTextField;
    public Label EroorLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDate date = LocalDate.now();
        BirthDatePicker.setValue(date);
        validator.createCheck()
                .dependsOn("name", NameTextField.textProperty())
                .withMethod(c -> {
                    String userName = c.get("name");
                    if (userName.isEmpty()) {
                        c.error("Insert user name");
                    }
                })
                .decorates(NameTextField)
                .immediate();

        validator.createCheck()
                .dependsOn("surname", SurnameTextField.textProperty())
                .withMethod(c -> {
                    String userSurname = c.get("surname");
                    if (userSurname.isEmpty()) {
                        c.error("Insert user surname");
                    }
                })
                .decorates(SurnameTextField)
                .immediate();

        validator.createCheck()
                .dependsOn("email", EmailTextField.textProperty())
                .withMethod(c -> {
                    String email = c.get("email");
                    if (email.isEmpty()) {
                        c.error("Insert email address");
                    }
                })
                .decorates(EmailTextField)
                .immediate();
    }
    @FXML
    protected void onHelloButtonClick() {
        if (validator.validate()) {
            if (!EroorLabel.getText().isEmpty()) EroorLabel.setText("");
        } else {
            EroorLabel.setText("Insert correct values");
        }
    }

}