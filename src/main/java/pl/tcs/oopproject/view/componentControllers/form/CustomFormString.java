package pl.tcs.oopproject.view.componentControllers.form;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pl.tcs.oopproject.App;
import pl.tcs.oopproject.model.history.UserProfileEditor;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomFormString extends AnchorPane implements Initializable {
    private final StringProperty property;
    private final String type;
    private final UserProfileEditor editor = new UserProfileEditor();
    @FXML
    private Label CustomText;
    @FXML
    private TextField NewInfo;

    public CustomFormString(String type, StringProperty property) {
        this.type = type;
        this.property = property;

        FXMLLoader loader = new FXMLLoader(App.class.getResource("components/custom-form-string.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void ConfirmButtonClick() {
        String info = NewInfo.getText();
        try {
            switch (type) {
                case "Account" -> editor.changeLogin(info);
                case "Name" -> editor.changeName(info);
                case "Surname" -> editor.changeSurname(info);
                case "Email" -> editor.changeEmail(info);
                case "PhoneNumber" -> editor.changeTelephoneNumber(info);
            }

            property.setValue(info);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Niepoprawne dane. Spróbuj ponownie");
            a.showAndWait();
        }

        Stage thiStage = (Stage) CustomText.getScene().getWindow();
        thiStage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        switch (type) {
            case "Account" -> CustomText.textProperty().setValue("Ustaw nowy login");
            case "Name" -> CustomText.textProperty().setValue("Ustaw nowe imię");
            case "Surname" -> CustomText.textProperty().setValue("Ustaw nowe nazwisko");
            case "Email" -> CustomText.textProperty().setValue("Ustaw nowy e-mail");
            case "PhoneNumber" -> CustomText.textProperty().set("Ustaw nowy numer telefonu");
        }
    }
}
