package pl.tcs.oopproject.view.sceneControllers;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.tcs.oopproject.App;
import pl.tcs.oopproject.model.history.History;
import pl.tcs.oopproject.model.history.HistoryLongTermTicket;
import pl.tcs.oopproject.model.history.HistorySingleJourneyTicket;
import pl.tcs.oopproject.view.ViewController;
import pl.tcs.oopproject.view.componentControllers.form.CustomFormDate;
import pl.tcs.oopproject.view.componentControllers.form.CustomFormPassword;
import pl.tcs.oopproject.view.componentControllers.form.CustomFormString;
import pl.tcs.oopproject.view.componentControllers.ticket.HistoryLongTicketPane;
import pl.tcs.oopproject.view.componentControllers.ticket.HistorySingleTicketPane;
import pl.tcs.oopproject.viewmodel.users.ActiveUser;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AccountSceneController implements Initializable {
    public final History history = new History();
    ArrayList<HistorySingleJourneyTicket> activeTickets = new ArrayList<>();
    ArrayList<HistorySingleJourneyTicket> nonActiveTickets = new ArrayList<>();
    ArrayList<HistoryLongTermTicket> activeLongTerm = new ArrayList<>();
    ArrayList<HistoryLongTermTicket> nonActiveLongTerm = new ArrayList<>();
    public Label Name;
    public Label Surname;
    public Label DateOfBirth;
    public Label PhoneNumber;
    public Label EmailAdress;
    public Label AccountName;
    public VBox ActiveTickets;
    public VBox NonActiveTickets;
    public VBox ActiveLongTerm;
    public VBox NonActiveLongTerm;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AccountName.textProperty().setValue(ActiveUser.getActiveUser());
        Name.textProperty().setValue(ActiveUser.getPerson().getName());
        Surname.textProperty().setValue(ActiveUser.getPerson().getSurname());
        DateOfBirth.textProperty().setValue(ActiveUser.getPerson().getDateOfBirth().format(DateTimeFormatter.ofPattern("dd:MM:yyyy")));
        PhoneNumber.textProperty().setValue(ActiveUser.getPerson().getTelephoneNumber());
        EmailAdress.textProperty().setValue(ActiveUser.getPerson().getEmailAddress());

        reload();
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
        CustomFormString form = new CustomFormString("PhoneNumber", PhoneNumber.textProperty());
        showStage(form);
    }

    public void PasswordChangeButtonClick() {
        CustomFormPassword form = new CustomFormPassword();
        showStage(form);
    }

    private void showStage(Parent node) {
        Stage stage = new Stage();
        Scene scene = new Scene(node);
        stage.setScene(scene);
        stage.show();
    }

    public void reload() {
        try {
            activeTickets = history.activeSingleJourneyTickets();
            nonActiveTickets = history.archivedSingleJourneyTickets();
            activeLongTerm = history.activeLongTermTickets();
            nonActiveLongTerm = history.archivedLongTermTickets();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ActiveTickets.getChildren().clear();
        NonActiveTickets.getChildren().clear();
        ActiveLongTerm.getChildren().clear();
        NonActiveLongTerm.getChildren().clear();

        activeTickets.forEach(c -> {
            HistorySingleTicketPane pane = new HistorySingleTicketPane(c, this, true);
            ActiveTickets.getChildren().addAll(pane);
        });

        activeLongTerm.forEach(c -> {
            HistoryLongTicketPane pane = new HistoryLongTicketPane(c, this, true);
            ActiveLongTerm.getChildren().addAll(pane);
        });

        nonActiveTickets.forEach(c -> {
            HistorySingleTicketPane pane = new HistorySingleTicketPane(c, this, false);
            NonActiveTickets.getChildren().addAll(pane);
        });

        nonActiveLongTerm.forEach(c -> {
            HistoryLongTicketPane pane = new HistoryLongTicketPane(c, this, false);
            NonActiveLongTerm.getChildren().addAll(pane);
        });
    }

    public void BackButtonClick() {
        Stage thisStage = (Stage) Name.getScene().getWindow();
        thisStage.close();
        Stage newStage = new Stage();
        newStage.setScene(ViewController.getTrainSearchScene());
        newStage.show();
    }

    public void CloseButtonClick() {
        Stage thisStage = (Stage) Name.getScene().getWindow();
        thisStage.close();
    }

    public void LogOutButtonClick() {
        Stage thisStage = (Stage) Name.getScene().getWindow();
        thisStage.close();
        App.basket.basketClear();
        ActiveUser.logOut();
        Stage newStage = new Stage();
        newStage.setScene(ViewController.getLandingScene());
        newStage.show();
    }

}
