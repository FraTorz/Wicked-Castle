package it.unicam.cs.mpgc.rpg129691;

import it.unicam.cs.mpgc.rpg129691.ui.utils.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class RpgApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        SceneManager.initialize(stage);
        SceneManager.switchScene("/fxml/MainMenu.fxml");
        stage.setTitle("Dungeon RPG");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}