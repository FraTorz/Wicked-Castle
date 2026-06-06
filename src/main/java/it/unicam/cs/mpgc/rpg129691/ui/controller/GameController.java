package it.unicam.cs.mpgc.rpg129691.ui.controller;

import it.unicam.cs.mpgc.rpg129691.model.game.Direction;
import it.unicam.cs.mpgc.rpg129691.model.game.GameEngine;
import it.unicam.cs.mpgc.rpg129691.model.game.GameEvent;
import it.unicam.cs.mpgc.rpg129691.model.game.GameState;
import it.unicam.cs.mpgc.rpg129691.model.map.DungeonMap;
import it.unicam.cs.mpgc.rpg129691.model.map.Position;
import it.unicam.cs.mpgc.rpg129691.ui.utils.AlertUtils;
import it.unicam.cs.mpgc.rpg129691.ui.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class GameController {

    private GameEngine game;
    // left Panel
    @FXML private Label difficultyLabel;
    @FXML private Label hpLabel;
    @FXML private Label weaponLabel;
    // bottom Panel
    @FXML private TextArea eventLogArea;
    // center Panel
    @FXML private GridPane mapGrid;
    // right Panel
    @FXML private Button northButton;
    @FXML private Button southButton;
    @FXML private Button eastButton;
    @FXML private Button westButton;
    // north Panel
    @FXML private Button saveButton;
    @FXML private Button mapButton;
    @FXML private Button endButton;

    public void setGame(GameEngine game) {
        this.game = game;
        refresh();
        appendLog("Partita iniziata.");
        appendLog("Ti trovi nella stanza iniziale.");
    }

    private void refresh() {
        difficultyLabel.setText(game.getMap().getDifficulty().toString());
        hpLabel.setText(String.valueOf(game.getPlayer().getHealth()));
        weaponLabel.setText(game.getPlayer().getEquippedWeapon().getName());
        renderMap();
    }

    private void renderMap() {
        mapGrid.getChildren().clear();
        int playerRow = game.getPlayer().getPosition().getRow();
        int playerCol = game.getPlayer().getPosition().getColumn();
        DungeonMap map = game.getMap();
        for (int r = -1; r <= 1; r++) {
            for (int c = -1; c <= 1; c++) {
                int worldRow = playerRow + r;
                int worldCol = playerCol + c;
                Label cell = new Label();
                cell.setMinSize(40, 40);
                cell.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                cell.setStyle("""
                    -fx-border-color: black;
                    -fx-alignment: center;
                """);
                if (r == 0 && c == 0) {
                    cell.setText("P");
                } else if (map.isInside(new Position(worldRow, worldCol))) {
                    if (map.isVisited(new Position(worldRow, worldCol))) {
                        cell.setText("·");
                    } else {
                        cell.setText("?");
                    }
                } else {
                    cell.setText("X");
                }
                mapGrid.add(cell, c + 1, r + 1);
            }
        }
    }

    @FXML
    private void moveNorth() {
        move(Direction.NORTH);
    }
    @FXML
    private void moveSouth() {
        move(Direction.SOUTH);
    }
    @FXML
    private void moveEast() {
        move(Direction.EAST);
    }
    @FXML
    private void moveWest() {
        move(Direction.WEST);
    }

    private void move(Direction direction) {
        boolean moved = game.movePlayer(direction);
        if(!moved) {
            appendLog("Non puoi andare in quella direzione.");
            return;
        }
        appendLog("Ti sei mosso verso " + direction);
        appendLog(
                "Posizione attuale: ("
                        + game.getPlayer().getPosition().getRow()
                        + ", "
                        + game.getPlayer().getPosition().getColumn()
                        + ")"
        );
        GameEvent event = game.getLastEvent();
        if (event != null) {
            appendLog(event.getMessage());
            if (event.getCombatLog() != null) {
                event.getCombatLog()
                        .getMessages()
                        .forEach(this::appendLog);
            }
            if (game.getLastHint() != null) {
                appendLog("🔎 Indizio ottenuto:");
                appendLog(game.getLastHint().getMessage());
            }
        }
        refresh();
        checkGameState();
    }

    private void checkGameState() {
        switch (game.getGameState()) {
            case PLAYER_WON -> {
                appendLog("🎉 Hai trovato l'uscita! Hai vinto!");
                disableInput();
            }
            case PLAYER_LOST -> {
                appendLog("💀 Sei morto. Game Over.");
                disableInput();
            }
            default -> { }
        }
    }

    private void disableInput() {
        northButton.setDisable(true);
        southButton.setDisable(true);
        eastButton.setDisable(true);
        westButton.setDisable(true);
        saveButton.setDisable(true);
        mapButton.setDisable(true);
        endButton.setText("Torna al menu");
    }

    @FXML
    private void handleEndGame() {
        String title;
        String message;
        if (game.getGameState() == GameState.RUNNING) {
            title = "Termina partita";
            message = "Sei sicuro di voler abbandonare la partita?";
        } else {
            title = "Partita terminata";
            message = "Sei sicuro di voler uscire dalla partita?";
        }
        if (AlertUtils.showConfirmation(title, message)) {
            SceneManager.switchScene("/fxml/MainMenu.fxml");
        }
    }

    @FXML
    private void handleMap() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Map.fxml"));
            Parent mapRoot = loader.load();
            MapController controller = loader.getController();
            controller.setGame(game);
            SceneManager.setRoot(mapRoot);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void appendLog(String message) {
        eventLogArea.appendText(message + "\n");
    }

}