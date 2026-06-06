package it.unicam.cs.mpgc.rpg129691.ui.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {

    private static Stage primaryStage;

    private SceneManager() {}

    public static void initialize(Stage stage) {
        primaryStage = stage;
    }

    public static void setRoot(Parent root) {
        primaryStage.setScene(new Scene(root));
    }

    public static void switchScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static FXMLLoader loadScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
            return loader;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}