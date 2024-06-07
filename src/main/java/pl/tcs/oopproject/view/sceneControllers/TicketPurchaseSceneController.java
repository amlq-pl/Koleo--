package pl.tcs.oopproject.view.sceneControllers;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import pl.tcs.oopproject.App;
import pl.tcs.oopproject.view.Basket;
import pl.tcs.oopproject.view.componentControllers.BasketItem;
import pl.tcs.oopproject.view.componentControllers.TicketItemContainer;

import java.net.URL;
import java.util.ResourceBundle;

public class TicketPurchaseSceneController implements Initializable {
    private ObservableList<TicketItemContainer> ticketContainers;
    public VBox TicketContainersBox;
    private Basket thisBasket;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        thisBasket = App.basket;

        thisBasket.basketItems.addListener((ListChangeListener<? super BasketItem>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (BasketItem i : change.getAddedSubList()) {
                        for (int j = 0; j < i.count.getValue(); j++) {
                            TicketItemContainer container = new TicketItemContainer(i.connection);
                        }
                    }
                }
                if (change.wasRemoved()) {

                }
            }
        });

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
    }
}
