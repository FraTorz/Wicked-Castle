package it.unicam.cs.mpgc.rpg129691.ui.controller;

import it.unicam.cs.mpgc.rpg129691.model.game.GameSession;
import it.unicam.cs.mpgc.rpg129691.ui.render.MapRenderer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Controller della finestra di visualizzazione della mappa completa.
 *
 * Questo controller si occupa esclusivamente di:
 * <ul>
 *     <li>renderizzare la mappa completa del dungeon</li>
 *     <li>gestire la chiusura della finestra popup</li>
 * </ul>
 *
 * La mappa viene generata a partire dalla {@link GameSession} corrente
 * e renderizzata tramite {@link MapRenderer}.
 */
public class MapController {

    @FXML private GridPane mapGrid;
    @FXML private Button backButton;

    /**
     * Inizializza la vista della mappa completa.
     *
     * Viene recuperata la partita corrente dalla {@link GameSession}
     * e la mappa viene renderizzata tramite {@link MapRenderer#renderFullMap}.
     */
    public void initialize() {
        MapRenderer.renderFullMap(mapGrid, GameSession.getInstance().getGame());
    }

    /**
     * Chiude la finestra della mappa.
     *
     * Recupera lo {@link Stage} corrente e lo chiude.
     */
    @FXML
    private void goBack() {
        ((Stage) backButton.getScene().getWindow()).close();
    }
}