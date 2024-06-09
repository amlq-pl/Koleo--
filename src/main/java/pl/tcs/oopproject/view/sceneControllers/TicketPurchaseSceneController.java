package pl.tcs.oopproject.view.sceneControllers;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.tcs.oopproject.App;
import pl.tcs.oopproject.model.assignedSeat.AssignedSeat;
import pl.tcs.oopproject.model.assignedSeat.TrainsAssignedSeats;
import pl.tcs.oopproject.model.discount.Discount;
import pl.tcs.oopproject.model.discount.Voucher;
import pl.tcs.oopproject.model.ticket.Details;
import pl.tcs.oopproject.model.ticket.SingleJourneyTrainTicket;
import pl.tcs.oopproject.model.users.Person;
import pl.tcs.oopproject.view.Basket;
import pl.tcs.oopproject.view.ViewController;
import pl.tcs.oopproject.view.componentControllers.BasketItem;
import pl.tcs.oopproject.view.componentControllers.TicketItemContainer;
import pl.tcs.oopproject.viewmodel.place.PlaceFactory;
import pl.tcs.oopproject.viewmodel.tickets.TicketFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TicketPurchaseSceneController implements Initializable {
    private final ObservableList<TicketItemContainer> ticketContainers = FXCollections.observableArrayList();
    @FXML
    private VBox TicketContainersBox;
    private final Basket thisBasket;

    public TicketPurchaseSceneController() {
        thisBasket = App.basket;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<ArrayList<TrainsAssignedSeats>> listOfConnections = new ArrayList<>();
        for (BasketItem item : thisBasket.basketItems) {
            try {
                ArrayList<TrainsAssignedSeats> assignedSeats = PlaceFactory.create(item.getConnection(), item.count.getValue());
                listOfConnections.add(assignedSeats);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ticketContainers.addListener((ListChangeListener<? super TicketItemContainer>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    TicketContainersBox.getChildren().addAll(change.getAddedSubList());
                }
                if (change.wasRemoved()) {
                    TicketContainersBox.getChildren().removeAll(change.getRemoved());
                }
            }
        });

        listOfConnections.forEach(c -> {
            for (TrainsAssignedSeats seats : c) {
                TicketItemContainer container = new TicketItemContainer(seats.getConnection(), seats.seatList(), seats);
                ticketContainers.addAll(container);
            }
        });
    }

    public void BuyAction() {
        TicketFactory factory = new TicketFactory();

        for (TicketItemContainer ticketItemContainer : ticketContainers) {
            if (ticketItemContainer.getPerson().equals(Person.DEFAULT)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Wprowadź dane dla wszystkich użytkowników");
                alert.showAndWait();
                return;
            }
        }

        for (TicketItemContainer ticketItemContainer : ticketContainers) {
            for (AssignedSeat seat : ticketItemContainer.getAssignedSeats().seatList()) {
                if (seat == null) {
                    ticketItemContainer.setStyle("-fx-border-color: red");
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("Na ten przejazd nie ma dostępnych miejsc");
                    a.showAndWait();
                    ticketItemContainer.setStyle("-fx-border-color: black");
                    return;
                }
            }
        }

        ArrayList<Discount> discounts = new ArrayList<>();
        ArrayList<Voucher> vouchers = new ArrayList<>();
        ArrayList<Details> details = new ArrayList<>();
        ArrayList<TrainsAssignedSeats> seats = new ArrayList<>();
        ArrayList<Person> persons = new ArrayList<>();

        for (TicketItemContainer ticketItemContainer : ticketContainers) {
            discounts.add(ticketItemContainer.getDiscount());
            vouchers.add(ticketItemContainer.getVoucher());
            details.add(ticketItemContainer.getDetails());
            seats.add(ticketItemContainer.getAssignedSeats());
            persons.add(ticketItemContainer.getPerson());
        }

        try {
            ArrayList<SingleJourneyTrainTicket> ticketList = factory.createSingleJourneyTicket(discounts, vouchers, details, seats, persons);
            FXMLLoader loader = new FXMLLoader(App.class.getResource("scenes/summary-scene.fxml"));
            Parent p = null;

            try {
                p = loader.load();
                Stage thisStage = (Stage) TicketContainersBox.getScene().getWindow();
                thisStage.close();

                SummarySceneController controller = loader.getController();
                controller.setList(ticketList);

                Stage newStage = new Stage();
                Scene scene = new Scene(p);
                newStage.setScene(scene);
                newStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("BILETY ZAKUPIONO POMYŚLNIE");
        a.showAndWait();
        App.basket.basketClear();
    }

    public void BackButtonClick() {
        Stage thisStage = (Stage) TicketContainersBox.getScene().getWindow();
        thisStage.close();
        Stage newStage = new Stage();
        newStage.setScene(ViewController.getBasketScene());
        newStage.show();
    }

    public void ExitButtonClick() {
        Stage thisStage = (Stage) TicketContainersBox.getScene().getWindow();
        thisStage.close();
    }
}
