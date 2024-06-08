package pl.tcs.oopproject.view.sceneControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import net.synedra.validatorfx.Validator;
import pl.tcs.oopproject.model.exception.*;
import pl.tcs.oopproject.model.users.Person;
import pl.tcs.oopproject.view.ViewController;
import pl.tcs.oopproject.viewmodel.users.ActiveUser;
import pl.tcs.oopproject.viewmodel.users.PersonFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TicketFormController implements Initializable {
    private Person toChange = ActiveUser.getPerson();
    private final Validator validator = new Validator();
    public TextField NameTextField;
    public TextField SurnameTextField;
    public DatePicker BirthDatePicker;
    public TextField EmailTextField;
    public TextField PhoneNumberTextField;
    public Label ErrorLabel;
    public Button BackButton;
    public Button CloseButton;

    public TicketFormController() {
    }

    public void setPerson(Person person) {
        this.toChange = person;
    }

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

    private void resetToInitial() {
        NameTextField.setText("");
        SurnameTextField.setText("");
        BirthDatePicker.setValue(LocalDate.now());
        EmailTextField.setText("");
        PhoneNumberTextField.setText("");
        ErrorLabel.setText("");
        ErrorLabel.setStyle("-fx-text-fill: red");
    }

    @FXML
    protected void onHelloButtonClick() {
        if (validator.validate()) {
            if (!ErrorLabel.getText().isEmpty()) ErrorLabel.setText("");
            try {
                Person newPerson = PersonFactory.create(NameTextField.getText(),
                        SurnameTextField.getText(),
                        BirthDatePicker.getValue(),
                        EmailTextField.getText(),
                        PhoneNumberTextField.getText());
                ErrorLabel.setStyle("-fx-text-fill: green");
                ErrorLabel.setText("Użytkownik utworzony pomyślnie");
                resetToInitial();

                toChange = newPerson;

                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setContentText("ZAREJESTROWANO POMYŚLNIE");
                a.showAndWait();

                Stage thisStage = (Stage) NameTextField.getScene().getWindow();
                thisStage.close();
            } catch (InvalidNameOrSurnameException e) {
                ErrorLabel.setText("Podaj porawne imię");
            } catch (InvalidEmailException e) {
                ErrorLabel.setText("Taki email nie istnieje");
            } catch (InvalidTelephoneNumberException e) {
                ErrorLabel.setText("Taki numer telefonu nie istnieje");
            } catch (InvalidPasswordException e) {
                ErrorLabel.setText("Niepoprawne hasło");
            } catch (InvalidDateOfBirthException e) {
                ErrorLabel.setText("Niepoprawna data urodzenia");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            ErrorLabel.setText("Zaznaczone pola nie mogą być puste");
        }
    }

    public void BackButtonClick() {

    }

    public void ExitButtonClick() {
        Stage thisStage = (Stage) CloseButton.getScene().getWindow();
        thisStage.close();
    }
}
