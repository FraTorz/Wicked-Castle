package it.unicam.cs.mpgc.rpg129691.ui.controller;

import it.unicam.cs.mpgc.rpg129691.model.game.GameSession;
import it.unicam.cs.mpgc.rpg129691.ui.render.MapRenderer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MapController {

    @FXML private GridPane mapGrid;
    @FXML private Button backButton;

    public void initialize() {
        MapRenderer.renderFullMap(mapGrid, GameSession.getInstance().getGame());
    }

    @FXML
    private void goBack() {
        ((Stage) backButton.getScene().getWindow()).close();
    }

}