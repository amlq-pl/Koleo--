package pl.tcs.oopproject.view.sceneControllers;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pl.tcs.oopproject.model.history.History;
import pl.tcs.oopproject.model.ticket.TrainTicket;
import pl.tcs.oopproject.view.componentControllers.CustomFormDate;
import pl.tcs.oopproject.view.componentControllers.CustomFormString;
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
        CustomFormString form = new CustomFormString("Account", AccountName.textProperty());
        showStage(form);
    }

    public void NameChangeButtonClick() {
        CustomFormString form = new CustomFormString("Name", Name.textProperty());
        showStage(form);
    }

    public void SurnameChangeButtonClick() {
        CustomFormString form = new CustomFormString("Surname", Surname.textProperty());
        showStage(form);
    }

    public void DateChangeButtonClick() {
        CustomFormDate form = new CustomFormDate(DateOfBirth.textProperty());
        showStage(form);
    }

    public void EmailChangeButtonClick() {
        CustomFormString form = new CustomFormString("Email", EmailAdress.textProperty());
        showStage(form);
    }

    public void PhoneNumberChangeButtonClick() {

    }

    public void PasswordChangeButtonClick() {

    }
    private void showStage(Parent node) {
        Stage stage = new Stage();
        Scene scene = new Scene(node);
        stage.setScene(scene);
        stage.show();
    }
}
