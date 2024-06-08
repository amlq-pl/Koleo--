package pl.tcs.oopproject.view.componentControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pl.tcs.oopproject.App;
import pl.tcs.oopproject.model.history.UserProfileEditor;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomFormPassword extends AnchorPane implements Initializable {
    private UserProfileEditor editor = new UserProfileEditor();
    @FXML
    private Label CustomText;
    @FXML
    private PasswordField OldPassword;
    @FXML
    private PasswordField NewPassword;
    public CustomFormPassword() {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("components/custom-form-password.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void ReadyButtonClick() {
        try {
            editor.changePassword(OldPassword.getText(), NewPassword.getText());
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Niepoprawne hasło, spróbuj ponownie");
            a.showAndWait();
        }

        Stage thisStage = (Stage) OldPassword.getScene().getWindow();
        thisStage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
