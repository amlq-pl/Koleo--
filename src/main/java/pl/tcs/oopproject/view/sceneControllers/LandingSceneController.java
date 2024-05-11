package pl.tcs.oopproject.view.sceneControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import pl.tcs.oopproject.viewmodel.users.Person;
import pl.tcs.oopproject.viewmodel.users.PersonFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class LandingSceneController implements Initializable {
    public TextField NameTextField;
    public TextField SurnameTextField;
    public DatePicker BirthDatePicker;
    public TextField EmailTextField;
    public TextField PhoneNumberTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDate date = LocalDate.now();
        BirthDatePicker.setValue(date);
    }
    @FXML
    protected void onHelloButtonClick() {
        String name = NameTextField.getText();
        String surname = SurnameTextField.getText();
        LocalDate dateOfBirth = BirthDatePicker.getValue();
        String emailAddress = EmailTextField.getText();
        String phoneNumber = PhoneNumberTextField.getText();

        if (name.isEmpty()) {
            NameTextField.setStyle("-fx-background-color: red");
        }
        if (surname.isEmpty()) {
            SurnameTextField.setStyle("-fx-background-color: red");
        }
        else {
            PersonFactory factory = new PersonFactory();
        }
//        Person p = factory.create();
    }

    public void onNameClicked() {
        NameTextField.setStyle("-fx-background-color: white");
        SurnameTextField.setStyle("-fx-background-color: white");
    }

    public void onSurnameClicked() {
        NameTextField.setStyle("-fx-background-color: white");
        SurnameTextField.setStyle("-fx-background-color: white");
    }

}