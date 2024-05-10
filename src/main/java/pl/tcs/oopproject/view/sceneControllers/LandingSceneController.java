package pl.tcs.oopproject.view.sceneControllers;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import pl.tcs.oopproject.viewmodel.users.Person;
import pl.tcs.oopproject.viewmodel.users.PersonFactory;

import java.time.LocalDate;

public class LandingSceneController {
    public TextField NameTextField;
    public TextField SurnameTextField;
    public DatePicker BirthDatePicker;
    public TextField EmailTextField;
    public TextField PhoneNumberTextField;

    @FXML
    protected void onHelloButtonClick() {
        String name = NameTextField.getText();
        String surname = SurnameTextField.getText();
        LocalDate dateOfBirth = BirthDatePicker.getValue();
        String emailAddress = EmailTextField.getText();
        String phoneNumber = PhoneNumberTextField.getText();

        PersonFactory factory = new PersonFactory();
//        Person p = factory.create();
    }
}