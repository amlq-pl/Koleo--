package pl.tcs.oopproject.view.sceneControllers;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import pl.tcs.oopproject.App;

import pl.tcs.oopproject.model.assignedSeat.TrainsAssignedSeats;
import pl.tcs.oopproject.model.ticket.SingleJourneyTrainTicket;
import pl.tcs.oopproject.view.Basket;
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
            } catch (SQLException e) {
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
                TicketItemContainer container = new TicketItemContainer(seats.getConnection(), seats.seatList());
                ticketContainers.addAll(container);
            }
        });
    }

    public void BuyAction() {
        // TODO: check czy wszystkie dane sa w porzadku (czy sa miejsca ktore checmy kupic)

        TicketFactory factory = new TicketFactory();
//        ArrayList<SingleJourneyTrainTicket> ticketList = factory.createSingleJourneyTicket();

        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Ale eza byq dobrze iziziziziz");
        a.showAndWait();
    }
}
