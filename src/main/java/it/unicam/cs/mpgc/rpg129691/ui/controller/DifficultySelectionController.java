package it.unicam.cs.mpgc.rpg129691.ui.controller;

import it.unicam.cs.mpgc.rpg129691.model.game.Difficulty;
import it.unicam.cs.mpgc.rpg129691.model.game.GameEngine;
import it.unicam.cs.mpgc.rpg129691.model.game.GameFactory;
import it.unicam.cs.mpgc.rpg129691.model.game.GameSession;
import it.unicam.cs.mpgc.rpg129691.ui.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

/**
 * Controller della schermata di selezione difficoltà.
 *
 * Questa classe gestisce la creazione di una nuova partita
 * in base al livello di difficoltà scelto dall'utente.
 *
 * Si occupa esclusivamente di:
 * <ul>
 *     <li>creare una nuova istanza di gioco</li>
 *     <li>salvare la sessione corrente in {@link GameSession}</li>
 *     <li>transitare verso la scena di gioco</li>
 * </ul>
 */
public class DifficultySelectionController {

    private final GameFactory gameFactory = new GameFactory();

    /**
     * Avvia una nuova partita in modalità EASY.
     */
    @FXML
    private void startEasyGame() {
        startGame(Difficulty.EASY);
    }

    /**
     * Avvia una nuova partita in modalità MEDIUM.
     */
    @FXML
    private void startMediumGame() {
        startGame(Difficulty.MEDIUM);
    }

    /**
     * Avvia una nuova partita in modalità HARD.
     */
    @FXML
    private void startHardGame() {
        startGame(Difficulty.HARD);
    }

    /**
     * Torna al menu principale.
     */
    @FXML
    private void goBack() {
        SceneManager.switchScene("/fxml/MainMenu.fxml");
    }

    /**
     * Crea e avvia una nuova partita con la difficoltà selezionata.
     *
     * Questo metodo:
     * <ol>
     *     <li>crea un nuovo {@link GameEngine} tramite {@link GameFactory}</li>
     *     <li>salva la partita nella {@link GameSession}</li>
     *     <li>carica la scena di gioco</li>
     *     <li>inizializza il {@link GameController}</li>
     * </ol>
     *
     * @param difficulty livello di difficoltà selezionato
     */
    private void startGame(Difficulty difficulty) {
        GameEngine game = gameFactory.createNewGame(difficulty);
        GameSession.getInstance().setGame(game);
        FXMLLoader loader = SceneManager.switchSceneAndGetLoader("/fxml/Game.fxml");
        GameController controller = loader.getController();
        controller.initializeNewGame();
    }
}