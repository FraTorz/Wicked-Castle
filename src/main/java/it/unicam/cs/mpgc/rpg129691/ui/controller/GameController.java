package it.unicam.cs.mpgc.rpg129691.ui.controller;

import it.unicam.cs.mpgc.rpg129691.model.game.*;
import it.unicam.cs.mpgc.rpg129691.model.map.DungeonMap;
import it.unicam.cs.mpgc.rpg129691.model.map.Position;
import it.unicam.cs.mpgc.rpg129691.persistence.GamePersistenceService;
import it.unicam.cs.mpgc.rpg129691.ui.utils.AlertUtils;
import it.unicam.cs.mpgc.rpg129691.ui.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;

public class GameController {

    private final GamePersistenceService persistence = new GamePersistenceService();
    private GameEngine game;
    private GameEvent lastEvent;
    // left Panel
    @FXML private Label difficultyLabel;
    @FXML private Label hpLabel;
    @FXML private Label weaponLabel;
    // bottom Panel
    @FXML private TextArea eventLogArea;
    @FXML private Button combatDetailsButton;
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

    public void initializeGame() {
        this.game = GameSession.getInstance().getGame();
        if(game == null) {
            throw new IllegalStateException("Nessuna partita attiva");
        }
        refresh();
    }

    public void initializeNewGame() {
        initializeGame();
        appendLog("🎮 Partita iniziata");
        appendLog("Ti trovi nella stanza iniziale.");
    }

    public void initializeLoadedGame() {
        initializeGame();
        appendLog("💾 Partita caricata con successo.");
        appendLog("Esplorazione ripresa");
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
        lastEvent = game.movePlayer(direction);
        appendLog(format(lastEvent));
        lastEvent.getHint().ifPresent(
                h -> appendLog("Hai vinto il combattimento!\n🔎 Indizio ottenuto: " + h.getMessage()));
        combatDetailsButton.setVisible(lastEvent.getCombatResult().isPresent());
        refresh();
        checkGameState();
    }


    private String format(GameEvent event) {
        return switch (event.getType()) {
            case COMBAT -> "⚔️ " + event.getMessage();
            case TREASURE_FOUND -> "💰" + event.getMessage();
            case PLAYER_HEALED -> "❤️" + event.getMessage();
            case PLAYER_DAMAGED -> "☠️" + event.getMessage();
            case PLAYER_ESCAPED -> "🚪" + event.getMessage();
            case INVALID_MOVE -> "❌" + event.getMessage();
            case NOTHING -> event.getMessage();
        };
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
        FXMLLoader loader = SceneManager.loadFXML("/fxml/Map.fxml");
        MapController controller = loader.getController();
        controller.setGame(game);
        SceneManager.showPopup(loader.getRoot(), "Mappa");
    }

    @FXML
    private void showCombatDetails() {
        FXMLLoader loader = SceneManager.loadFXML("/fxml/CombatSummary.fxml");
        CombatSummaryController controller = loader.getController();
        lastEvent.getCombatResult().ifPresent(controller::setCombatResult);
        SceneManager.showPopup(loader.getRoot(), "Dettagli combattimento");
    }

    @FXML
    private void handleSaveGame() {
        String name = askSaveName();
        if (name == null) return;
        if (name.trim().isBlank()) {
            AlertUtils.showWarning("Attenzione", "Nome non valido");
            return;
        }
        try {
            persistence.saveGame(game, name);
            AlertUtils.showInfo("OK", "Salvataggio completato");
        }
        catch (IllegalStateException e) {
            SaveManagementController controller = handleSaveLimitReached();
            if(!controller.isDeletedSomething()){
                AlertUtils.showWarning("Attenzione", "Partita non salvata " +
                        "causa spazio insufficiente.");
                return;
            }
            try {
                persistence.saveGame(game, name);
                AlertUtils.showInfo("OK", "Salvataggio completato dopo eliminazione");
            } catch (Exception ex) {
                AlertUtils.showError("Errore", "Impossibile salvare dopo eliminazione");
            }
        } catch (IllegalArgumentException e) {
            AlertUtils.showError("Errore", "Nome già esistente");
        } catch (Exception e) {
            AlertUtils.showError("Errore", "Errore durante il salvataggio");
        }
    }

    private SaveManagementController handleSaveLimitReached(){
        AlertUtils.showWarning("Attenzione", "Limite salvataggi raggiunto. " +
                "Elimina almeno una partita per salvare quella corrente.");
        FXMLLoader loader = SceneManager.loadFXML("/fxml/SaveManagement.fxml");
        SaveManagementController controller = loader.getController();
        controller.setMode(SaveMode.GAME_LIMIT);
        SceneManager.showPopup(loader.getRoot(), "Gestione salvataggi");
        return controller;
    }

    private String askSaveName() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Salvataggio partita");
        dialog.setHeaderText("Inserisci il nome del salvataggio");
        dialog.setContentText("Nome:");
        return dialog.showAndWait().orElse(null);
    }

    private void appendLog(String message) {
        eventLogArea.appendText(message + "\n");
    }
}