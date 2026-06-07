package it.unicam.cs.mpgc.rpg129691.ui.controller;

import it.unicam.cs.mpgc.rpg129691.ui.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class MainMenuController {

    @FXML
    private void startNewGame() {
        SceneManager.switchScene("/fxml/DifficultySelection.fxml");
    }

    @FXML
    private void openSaveManagement() {
        FXMLLoader loader = SceneManager.switchSceneAndGetLoader("/fxml/SaveManagement.fxml");
        SaveManagementController controller = loader.getController();
        controller.setMode(SaveMode.MENU);
    }

    @FXML
    private void showHowToPlay() {
        SceneManager.switchScene("/fxml/HowToPlay.fxml");
    }

    @FXML
    private void exitGame() {
        System.exit(0);
    }
}