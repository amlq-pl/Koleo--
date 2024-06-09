package pl.tcs.oopproject.view.componentControllers.form;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pl.tcs.oopproject.App;
import pl.tcs.oopproject.model.history.UserProfileEditor;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class CustomFormDate extends AnchorPane implements Initializable {
    private final StringProperty text;
    private final UserProfileEditor editor = new UserProfileEditor();
    @FXML
    private DatePicker NewDate;

    public CustomFormDate(StringProperty text) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("components/custom-form-date.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        this.text = text;
        try {
            loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void ReadyButtonClick() {
        String temp = NewDate.getValue().format(DateTimeFormatter.ofPattern("dd:MM:yyyy"));

        try {
            editor.changeDateOfBirth(NewDate.getValue());
            text.setValue(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Stage thisStage = (Stage) NewDate.getScene().getWindow();
        thisStage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
