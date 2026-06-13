package it.unicam.cs.mpgc.rpg129691.ui.controller;

import it.unicam.cs.mpgc.rpg129691.ui.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

/**
 * Controller della scena del menu principale.
 *
 * Gestisce le azioni dell'utente dalla schermata iniziale del gioco,
 * permettendo di:
 * <ul>
 *     <li>iniziare una nuova partita</li>
 *     <li>accedere alla gestione dei salvataggi</li>
 *     <li>visualizzare la guida del gioco</li>
 *     <li>uscire dall'applicazione</li>
 * </ul>
 *
 * Questo controller agisce esclusivamente come livello di UI,
 * delegando la logica di navigazione alla {@link SceneManager}.
 */
public class MainMenuController {

    /**
     * Avvia la procedura di creazione di una nuova partita.
     *
     * Cambia scena verso la selezione della difficoltà.
     */
    @FXML
    private void startNewGame() {
        SceneManager.switchScene("/fxml/DifficultySelection.fxml");
    }

    /**
     * Apre la schermata di gestione dei salvataggi in modalità MENU.
     *
     * Dopo il caricamento della scena, imposta il {@link SaveMode}
     * del controller per abilitare le operazioni standard sui salvataggi.
     */
    @FXML
    private void openSaveManagement() {
        FXMLLoader loader = SceneManager.switchSceneAndGetLoader("/fxml/SaveManagement.fxml");
        SaveManagementController controller = loader.getController();
        controller.setMode(SaveMode.MENU);
    }

    /**
     * Apre la schermata informativa su come giocare.
     */
    @FXML
    private void showHowToPlay() {
        SceneManager.switchScene("/fxml/HowToPlay.fxml");
    }

    /**
     * Termina l'esecuzione dell'applicazione.
     */
    @FXML
    private void exitGame() {
        System.exit(0);
    }
}