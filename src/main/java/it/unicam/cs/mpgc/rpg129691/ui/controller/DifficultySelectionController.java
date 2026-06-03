package it.unicam.cs.mpgc.rpg129691.ui.controller;

import it.unicam.cs.mpgc.rpg129691.ui.SceneManager;
import javafx.fxml.FXML;

public class DifficultySelectionController {

    @FXML
    private void startEasyGame() {
        System.out.println("EASY");
    }

    @FXML
    private void startMediumGame() {
        System.out.println("MEDIUM");
    }

    @FXML
    private void startHardGame() {
        System.out.println("HARD");
    }

    @FXML
    private void goBack() {
        SceneManager.switchScene("/fxml/MainMenu.fxml");
    }
}