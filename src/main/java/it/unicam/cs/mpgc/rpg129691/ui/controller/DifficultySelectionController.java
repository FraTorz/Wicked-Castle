package it.unicam.cs.mpgc.rpg129691.ui.controller;

import it.unicam.cs.mpgc.rpg129691.model.game.Difficulty;
import it.unicam.cs.mpgc.rpg129691.model.game.GameEngine;
import it.unicam.cs.mpgc.rpg129691.model.game.GameFactory;
import it.unicam.cs.mpgc.rpg129691.ui.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class DifficultySelectionController {

    private final GameFactory gameFactory = new GameFactory();

    @FXML
    private void startEasyGame() {
        startGame(Difficulty.EASY);
    }
    @FXML
    private void startMediumGame() {
        startGame(Difficulty.MEDIUM);
    }
    @FXML
    private void startHardGame() {
        startGame(Difficulty.HARD);
    }

    @FXML
    private void goBack() {
        SceneManager.switchScene("/fxml/MainMenu.fxml");
    }

    private void startGame(Difficulty difficulty) {
        GameEngine game = gameFactory.createNewGame(difficulty);
        FXMLLoader loader = SceneManager.loadScene("/fxml/Game.fxml");
        GameController controller = loader.getController();
        controller.setGame(game);
    }
}