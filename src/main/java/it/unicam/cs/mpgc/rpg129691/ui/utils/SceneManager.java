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

    public static void switchScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static FXMLLoader switchSceneAndGetLoader(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
            return loader;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static FXMLLoader loadFXML(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            loader.load();
            return loader;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void showPopup(Parent root, String title) {
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }
}