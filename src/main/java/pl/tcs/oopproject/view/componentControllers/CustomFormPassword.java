package pl.tcs.oopproject.view.componentControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
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
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
