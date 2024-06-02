package pl.tcs.oopproject.view.sceneControllers;

import pl.tcs.oopproject.view.Basket;

public class BasketSceneController {
    private static Basket basket = new Basket();

    public static void setBasket(Basket newBasket) {
        basket = newBasket;
    }
}
