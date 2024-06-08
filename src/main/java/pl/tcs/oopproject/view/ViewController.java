package pl.tcs.oopproject.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import pl.tcs.oopproject.App;
import pl.tcs.oopproject.view.sceneControllers.TicketPurchaseSceneController;

import java.io.IOException;

public class ViewController {
	private static final Scene landingScene;
	private static final Scene signUpScene;
	private static final Scene logInScene;
	private static final Scene trainSearchScene;
	private static final Scene basketScene;
	private static final Scene longTermTicketBuyScene;
	
	static {
		landingScene = createScene("scenes/landing-scene.fxml");

		signUpScene = createScene("scenes/signup-scene.fxml");

		logInScene = createScene("scenes/login-scene.fxml");

		trainSearchScene = createScene("scenes/train-search-scene.fxml");

		basketScene = createScene("scenes/basket-scene.fxml");

		longTermTicketBuyScene = createScene("scenes/long-term-ticket-scene.fxml");
	}
	
	private static Scene createScene(String name) {
		try {
			FXMLLoader loader = new FXMLLoader(App.class.getResource(name));
			return new Scene(loader.load());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Scene getLandingScene() {
		return landingScene;
	}
	
	public static Scene getSignUpScene() {
		return signUpScene;
	}
	
	public static Scene getLogInScene() {
		return logInScene;
	}
	
	public static Scene getTrainSearchScene() {
		return trainSearchScene;
	}

	public static Scene getBasketScene() {
		return basketScene;
	}

	public static Scene getLongTermTicketBuyScene() {
		return longTermTicketBuyScene;
	}
}
