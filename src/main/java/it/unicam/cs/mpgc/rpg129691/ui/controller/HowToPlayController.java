package it.unicam.cs.mpgc.rpg129691.ui.controller;

import it.unicam.cs.mpgc.rpg129691.ui.utils.SceneManager;
import javafx.fxml.FXML;

public class HowToPlayController {

    @FXML
    private void goBack() {
        SceneManager.switchScene("/fxml/MainMenu.fxml");
    }
}