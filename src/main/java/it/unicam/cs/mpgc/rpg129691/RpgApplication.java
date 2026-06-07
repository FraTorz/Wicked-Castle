package it.unicam.cs.mpgc.rpg129691;

import it.unicam.cs.mpgc.rpg129691.ui.utils.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class RpgApplication extends Application {

    @Override
    public void start(Stage stage) {
        SceneManager.initialize(stage);
        stage.setTitle("Wicked Castle");
        SceneManager.switchScene("/fxml/MainMenu.fxml");
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}