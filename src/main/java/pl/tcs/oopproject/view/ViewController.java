package pl.tcs.oopproject.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import pl.tcs.oopproject.App;

import java.io.IOException;

public class ViewController {
    private static final Scene landingScene;
    private static final Scene signUpScene;
    private static final Scene logInScene;
    private static final Scene trainSearchScene;

    private static Scene createScene(String name) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(name));
            return new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    static {
        landingScene = createScene("scenes/landing-scene.fxml");
    }

    static {
        signUpScene = createScene("scenes/signup-scene.fxml");
    }

    static {
        logInScene = createScene("scenes/login-scene.fxml");
    }

    static {
        trainSearchScene = createScene("scenes/train-search-scene.fxml");
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
}
