package it.unicam.cs.mpgc.rpg129691.ui.controller;

import it.unicam.cs.mpgc.rpg129691.ui.utils.SceneManager;
import javafx.fxml.FXML;

/**
 * Controller della schermata "How To Play".
 *
 * Questa schermata ha esclusivamente lo scopo di mostrare
 * le istruzioni di gioco all'utente.
 *
 * Il controller gestisce unicamente la navigazione di ritorno
 * al menu principale.
 */
public class HowToPlayController {

    /**
     * Torna al menu principale.
     *
     * Questo metodo viene invocato dal pulsante "Back"
     * definito nel file FXML.
     */
    @FXML
    private void goBack() {
        SceneManager.switchScene("/fxml/MainMenu.fxml");
    }
}