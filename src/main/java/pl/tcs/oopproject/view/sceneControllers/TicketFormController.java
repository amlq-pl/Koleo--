package pl.tcs.oopproject.view.sceneControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.synedra.validatorfx.Validator;
import pl.tcs.oopproject.App;
import pl.tcs.oopproject.model.exception.*;
import pl.tcs.oopproject.model.users.Person;
import pl.tcs.oopproject.view.componentControllers.TicketItemContainer;
import pl.tcs.oopproject.viewmodel.users.PersonFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TicketFormController extends AnchorPane implements Initializable {
    private final TicketItemContainer item;
    private final Validator validator = new Validator();
    @FXML
    public TextField NameTextField;
    @FXML
    public TextField SurnameTextField;
    @FXML
    public DatePicker BirthDatePicker;
    @FXML
    public TextField EmailTextField;
    @FXML
    public TextField PhoneNumberTextField;
    @FXML
    public Label ErrorLabel;
    @FXML
    public Button BackButton;
    @FXML
    public Button CloseButton;


    public TicketFormController(TicketItemContainer item) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("scenes/ticket-form.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        this.item = item;

        try {
            loader.load();
        } catch (Exception e) {

        }
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
                item.person = newPerson;


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

    @FXML
    public void BackButtonClick() {

    }

    @FXML
    public void ExitButtonClick() {
        Stage thisStage = (Stage) CloseButton.getScene().getWindow();
        thisStage.close();
    }
}
