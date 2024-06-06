package pl.tcs.oopproject.view.sceneControllers;

import javafx.collections.ListChangeListener;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.tcs.oopproject.App;
import pl.tcs.oopproject.view.Basket;
import pl.tcs.oopproject.view.ViewController;
import pl.tcs.oopproject.view.componentControllers.BasketItem;

import java.net.URL;
import java.util.ResourceBundle;

public class BasketSceneController implements Initializable {
    private Basket basket = App.basket;
    public Button GoBackButton;
    public Button CloseButton;
    public Label CostLabel;
    public Button BuyButton;
    public VBox BasketItems;


    public void GoBackButtonClick() {
        Stage thisStage = (Stage) GoBackButton.getScene().getWindow();
        thisStage.close();
        Stage newStage = new Stage();
        newStage.setScene(ViewController.getTrainSearchScene());
        newStage.show();
    }

    public void CloseButtonClick() {
        Stage thisStage = (Stage) CloseButton.getScene().getWindow();
        thisStage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BasketItems.getChildren().setAll(basket.basketItems);

        basket.basketItems.addListener((ListChangeListener<? super BasketItem>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    BasketItems.getChildren().addAll(change.getAddedSubList());
                }
                if (change.wasRemoved()) {
                    BasketItems.getChildren().removeAll(change.getRemoved());
                }
            }
        });
    }

    public void BuyButtonClick() {

    }
}
