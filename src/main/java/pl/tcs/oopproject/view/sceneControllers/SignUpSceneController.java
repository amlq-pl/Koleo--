package pl.tcs.oopproject.view.sceneControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import net.synedra.validatorfx.Validator;
import pl.tcs.oopproject.viewmodel.exception.*;
import pl.tcs.oopproject.viewmodel.users.PersonFactory;
import pl.tcs.oopproject.viewmodel.users.Person;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SignUpSceneController implements Initializable {
    private final Validator validator = new Validator();
    public TextField NameTextField;
    public TextField SurnameTextField;
    public DatePicker BirthDatePicker;
    public TextField EmailTextField;
    public TextField PhoneNumberTextField;
    public Label EroorLabel;
    public TextField LoginTextField;
    public PasswordField PasswordInputField;

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

        validator.createCheck()
                .dependsOn("login", LoginTextField.textProperty())
                .withMethod(c -> {
                    String login = c.get("login");
                    if (login.isEmpty()) {
                        c.error("Insert login");
                    }
                })
                .decorates(LoginTextField)
                .immediate();

        validator.createCheck()
                .dependsOn("password", PasswordInputField.textProperty())
                .withMethod(c -> {
                    String passwd = c.get("password");
                    if (passwd.isEmpty()) {
                        c.error("Insert password");
                    }
                })
                .decorates(PasswordInputField)
                .immediate();
    }
    @FXML
    protected void onHelloButtonClick() {
        if (validator.validate()) {
            if (!EroorLabel.getText().isEmpty()) EroorLabel.setText("");
            try {
                PersonFactory personFactory = new PersonFactory();
                Person newPerson = personFactory.create(NameTextField.getText(),
                        SurnameTextField.getText(),
                        BirthDatePicker.getValue(),
                        EmailTextField.getText(),
                        PhoneNumberTextField.getText());

                System.out.println("User created");
                System.out.println(newPerson.toString());
            } catch(InvalidNameOrSurnameException e) {
                System.out.println("Invalid name");
            } catch (InvalidEmailException e) {
                System.out.println("Invalid email");
            } catch (InvalidTelephoneNumberException e) {
                System.out.println("Invalid phone number");
            } catch (InvalidUsernameOrPasswordException e) {
                System.out.println("Funny cats");
            } catch (InvalidPasswordException e) {
                System.out.println("chairChallenge");
            } catch (InvalidDateOfBirthException e) {
                System.out.println("Subway surfers");
            } catch (ExistingUserException e) {
                System.out.println("eo");
            }
        } else {
            EroorLabel.setText("Insert correct values");
        }
    }
}