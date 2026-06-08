package it.unicam.cs.mpgc.rpg129691.ui.controller;

import it.unicam.cs.mpgc.rpg129691.model.game.*;
import it.unicam.cs.mpgc.rpg129691.model.map.DungeonMap;
import it.unicam.cs.mpgc.rpg129691.model.map.Position;
import it.unicam.cs.mpgc.rpg129691.model.room.Room;
import it.unicam.cs.mpgc.rpg129691.persistence.GamePersistenceService;
import it.unicam.cs.mpgc.rpg129691.ui.render.BaseTileType;
import it.unicam.cs.mpgc.rpg129691.ui.render.SpriteProvider;
import it.unicam.cs.mpgc.rpg129691.ui.render.TileFactory;
import it.unicam.cs.mpgc.rpg129691.ui.utils.AlertUtils;
import it.unicam.cs.mpgc.rpg129691.ui.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

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
    // map render
    private static final int TILE_SIZE = 128;

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
        Position player = game.getPlayer().getPosition();
        setupGrid(3);
        int playerRow = player.getRow();
        int playerCol = player.getColumn();
        for (int r = -1; r <= 1; r++) {
            for (int c = -1; c <= 1; c++) {
                int worldRow = playerRow + r;
                int worldCol = playerCol + c;
                Position pos = new Position(worldRow, worldCol);
                BaseTileType base = getBaseTileType(pos);
                List<SpriteProvider> overlays = getOverlaysFor(pos);
                StackPane tile = TileFactory.createTile(base, overlays);
                tile.setMinSize(TILE_SIZE, TILE_SIZE);
                tile.setPrefSize(TILE_SIZE, TILE_SIZE);
                tile.setMaxSize(TILE_SIZE, TILE_SIZE);
                mapGrid.add(tile, c + 1, r + 1);
            }
        }
    }

    private BaseTileType getBaseTileType(Position pos) {
        DungeonMap map = game.getMap();
        if (!map.isInside(pos)) {
            return BaseTileType.WALL;
        }
        if (!map.isVisited(pos)) {
            return BaseTileType.UNKNOWN;
        }
        return BaseTileType.FLOOR;
    }

    private List<SpriteProvider> getOverlaysFor(Position pos) {
        List<SpriteProvider> overlays = new ArrayList<>();
        DungeonMap map = game.getMap();
        if(!map.isInside(pos))
            return overlays;
        if (!map.isVisited(pos) && !pos.equals(game.getPlayer().getPosition())) {
            return overlays;
        }
        if (pos.equals(game.getPlayer().getPosition())) {
            overlays.add(game.getPlayer());
        }
        Room room = map.getRoom(pos);
        room.getOverlaySprite().ifPresent(overlays::add);
        return overlays;
    }

    private void setupGrid(int size) {
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
        for (int i = 0; i < size; i++) {
            ColumnConstraints col = new ColumnConstraints(TILE_SIZE);
            RowConstraints row = new RowConstraints(TILE_SIZE);
            col.setMinWidth(TILE_SIZE);
            col.setPrefWidth(TILE_SIZE);
            col.setMaxWidth(TILE_SIZE);
            row.setMinHeight(TILE_SIZE);
            row.setPrefHeight(TILE_SIZE);
            row.setMaxHeight(TILE_SIZE);
            mapGrid.getColumnConstraints().add(col);
            mapGrid.getRowConstraints().add(row);
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