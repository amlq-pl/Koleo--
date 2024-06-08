package pl.tcs.oopproject.view.sceneControllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import pl.tcs.oopproject.model.history.History;
import pl.tcs.oopproject.viewmodel.users.ActiveUser;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AccountSceneController implements Initializable {
    public Label Name;
    public Label Surname;
    public Label DateOfBirth;
    public Label PhoneNumber;
    public Label EmailAdress;
    public Label AccountName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AccountName.textProperty().setValue(ActiveUser.getActiveUser());
        Name.textProperty().setValue(ActiveUser.getPerson().getName());
        Surname.textProperty().setValue(ActiveUser.getPerson().getSurname());
        DateOfBirth.textProperty().setValue(ActiveUser.getPerson().getDateOfBirth().format(DateTimeFormatter.ofPattern("dd:MM:yyyy")));
        PhoneNumber.textProperty().setValue(ActiveUser.getPerson().getTelephoneNumber());
        EmailAdress.textProperty().setValue(ActiveUser.getPerson().getEmailAddress());


    }

    public void AccountChangeButtonClick() {
    }

    public void NameChangeButtonClick() {
    }

    public void SurnameChangeButtonClick() {
    }

    public void DateChangeButtonClick() {
    }

    public void EmailChangeButtonClick() {
    }

    public void PhoneNumberChangeButtonClick() {
    }

    public void PasswordChangeButtonClick() {
    }

}
