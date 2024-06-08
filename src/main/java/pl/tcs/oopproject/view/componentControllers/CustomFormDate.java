package pl.tcs.oopproject.view.componentControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import pl.tcs.oopproject.App;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomFormDate extends AnchorPane implements Initializable {
    private String type;
    @FXML
    private Label CustomText;
    public CustomFormDate(String type) {
        this.type = type;
        FXMLLoader loader = new FXMLLoader(App.class.getResource("components/custom-form-string.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (type.equals("Account")) {

        } else if (type.equals("Name")) {

        } else if (type.equals("Surname")) {

        } else if (type.equals("Date")) {

        } else if (type.equals("Email")) {

        } else if (type.equals("PhoneNumber")) {

        } else if (type.equals("Password")) {

        }
    }
}
