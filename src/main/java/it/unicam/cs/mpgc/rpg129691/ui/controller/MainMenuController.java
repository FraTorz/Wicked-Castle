package it.unicam.cs.mpgc.rpg129691.ui.controller;

import it.unicam.cs.mpgc.rpg129691.ui.SceneManager;
import javafx.fxml.FXML;

public class MainMenuController {

    @FXML
    private void startNewGame() {
        SceneManager.switchScene("/fxml/DifficultySelection.fxml");
    }

    @FXML
    private void openSaveManagement() {
        SceneManager.switchScene("/fxml/SaveManagement.fxml");
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