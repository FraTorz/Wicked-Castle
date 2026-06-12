package it.unicam.cs.mpgc.rpg129691.ui.controller;

import it.unicam.cs.mpgc.rpg129691.model.game.*;
import it.unicam.cs.mpgc.rpg129691.persistence.GamePersistenceService;
import it.unicam.cs.mpgc.rpg129691.ui.render.MapRenderer;
import it.unicam.cs.mpgc.rpg129691.ui.utils.AlertUtils;
import it.unicam.cs.mpgc.rpg129691.ui.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class GameController {

    private final GamePersistenceService persistence = new GamePersistenceService();
    private GameEngine game;
    private GameEvent gameEvent;
    // left Panel
    @FXML private Label difficultyLabel;
    @FXML private Label hpLabel;
    @FXML private Label weaponLabel;
    // bottom Panel
    @FXML public ScrollPane logScrollPane;
    @FXML public TextFlow eventLogFlow;
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
    @FXML private Button endButton;

    public void initializeGame() {
        this.game = GameSession.getInstance().getGame();
        refreshLabels();
        refreshViewPort();
    }

    public void initializeNewGame() {
        initializeGame();
        appendLog("🎮", "Partita iniziata", "#FFFFFF");
        appendLog("•", "Ti trovi nella stanza iniziale.", "#FFFFFF");
    }

    public void initializeLoadedGame() {
        initializeGame();
        appendLog("💾", "Partita caricata con successo.", "#FFFFFF");
        appendLog("•", "Esplorazione ripresa", "#FFFFFF");
    }

    @FXML private void moveNorth() {
        move(Direction.NORTH);
    }
    @FXML private void moveSouth() {
        move(Direction.SOUTH);
    }
    @FXML private void moveEast() {
        move(Direction.EAST);
    }
    @FXML private void moveWest() {
        move(Direction.WEST);
    }

    private void move(Direction direction) {
        gameEvent = game.movePlayer(direction);
        refresh();
    }

    private void refresh(){
        refreshLogFlow();
        refreshButtons();
        refreshLabels();
        refreshViewPort();
    }

    private void refreshLogFlow() {
        logSeparator();
        logGameState();
        logGameEventMessage();
    }

    private void logSeparator(){
        eventLogFlow.getChildren().addFirst(createDottedSeparator());
        eventLogFlow.getChildren().addFirst(new Text("\n"));
    }

    private void logGameState() {
        switch (game.getGameState()) {
            case PLAYER_WON -> appendLog("🎉", "Hai trovato l'uscita! Hai vinto!", "#FFD700");
            case PLAYER_LOST -> appendLog("💀", "Sei morto. Game Over.", "#FF0000");
            default -> {}
        }
    }

    private void logGameEventMessage() {
        gameEvent.getHint().ifPresent(h -> {
            appendLog("🔎",
                    "Hai vinto il combattimento! Indizio ottenuto: " + h.getMessage(),
                    "#FFD700");
        });
        String message = gameEvent.getMessage();
        switch (gameEvent.getType()) {
            case COMBAT -> appendLog("⚔️", message, "#E57373");         // Rosso chiaro
            case TREASURE_FOUND -> appendLog("💰", message, "#4FC3F7"); // Azzurro cura
            case PLAYER_HEALED -> appendLog("❤️", message, "#81C784");  // Verde chiaro
            case PLAYER_DAMAGED -> appendLog("☠️", message, "#FF8A65"); // Arancione scuro
            case PLAYER_ESCAPED -> appendLog("🚪", message, "#BA68C8");  // Viola fuga
            case INVALID_MOVE -> appendLog("❌", message, "#FFB74D");   // Arancione errore
            case NOTHING -> appendLog("•", message, "#FFFFFF");         // Bianco normale
        }
    }

    private void refreshButtons() {
        if(game.getGameState() != GameState.RUNNING) disableInput();
        combatDetailsButton.setVisible(gameEvent.getCombatResult().isPresent());
    }

    private void disableInput() {
        northButton.setDisable(true);
        southButton.setDisable(true);
        eastButton.setDisable(true);
        westButton.setDisable(true);
        saveButton.setDisable(true);
        endButton.setText("Torna al menu");
    }

    private void refreshLabels() {
        difficultyLabel.setText(game.getMap().getDifficulty().toString());
        hpLabel.setText(String.valueOf(game.getPlayer().getHealth()));
        weaponLabel.setText(game.getPlayer().getEquippedWeapon().getName());
    }

    private void refreshViewPort(){
        MapRenderer.renderViewPort(mapGrid, game);
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
            GameSession.getInstance().clear();
            SceneManager.switchScene("/fxml/MainMenu.fxml");
        }
    }

    @FXML
    private void handleMap() {
        FXMLLoader loader = SceneManager.loadFXML("/fxml/Map.fxml");
        SceneManager.showPopup(loader.getRoot(), "Mappa");
    }

    @FXML
    private void showCombatDetails() {
        FXMLLoader loader = SceneManager.loadFXML("/fxml/CombatSummary.fxml");
        CombatSummaryController controller = loader.getController();
        gameEvent.getCombatResult().ifPresent(controller::setCombatResult);
        SceneManager.showPopup(loader.getRoot(), "Dettagli combattimento");
    }

    @FXML
    private void handleSaveGame() {
        String name = askSaveName();
        if (!isValidSaveName(name)) return;
        saveGame(name);
    }

    private String askSaveName() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Salvataggio partita");
        dialog.setHeaderText("Inserisci il nome del salvataggio");
        dialog.setContentText("Nome:");
        return dialog.showAndWait().orElse(null);
    }

    private boolean isValidSaveName(String name) {
        if (name == null) return false;
        if (name.isBlank()) {
            AlertUtils.showWarning("Attenzione", "Nome non valido");
            return false;
        }
        return true;
    }

    private void saveGame(String name) {
        try {
            persistence.saveGame(game, name);
            showSaveSuccess();
        }
        catch (IllegalStateException e) {
            handleSaveLimit(name);
        }
        catch (IllegalArgumentException e) {
            AlertUtils.showError("Errore", "Nome già esistente");
        }
        catch (Exception e) {
            AlertUtils.showError("Errore", "Errore durante il salvataggio");
        }
    }

    private void showSaveSuccess() {
        AlertUtils.showInfo("OK", "Salvataggio completato");
    }

    private void handleSaveLimit(String name) {
        SaveManagementController controller = handleSaveLimitReached();
        if (!controller.isDeletedSomething()) {
            AlertUtils.showWarning("Attenzione", "Partita non salvata causa spazio insufficiente.");
            return;
        }
        try {
            persistence.saveGame(game, name);
            AlertUtils.showInfo("OK", "Salvataggio completato dopo eliminazione");
        } catch (Exception e) {
            AlertUtils.showError("Errore", "Impossibile salvare dopo eliminazione");
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

    private void appendLog(String emoji, String message, String colorHex) {
        Text emojiText = new Text(emoji + " ");
        emojiText.setStyle("-fx-font-family: 'Segoe UI Emoji'; -fx-font-size: 18px;");
        Text messageText = new Text(message + "\n");
        messageText.setStyle("-fx-fill: " + colorHex + "; -fx-font-size: 15px; -fx-font-weight: bold;");
        eventLogFlow.getChildren().addFirst(messageText);
        eventLogFlow.getChildren().addFirst(emojiText);
        logScrollPane.setVvalue(0.0);
    }

    private Line createDottedSeparator() {
        Line line = new Line();
        line.endXProperty().bind(eventLogFlow.widthProperty().subtract(20)); // Sottrae 20px per tenere conto del padding
        line.setStyle(
                "-fx-stroke: #555555; " +          // Colore della linea (grigio scuro)
                        "-fx-stroke-width: 2; " +         // Spessore della linea
                        "-fx-stroke-dash-array: 6 6; " +  // Configura il tratteggio: 6px linea, 6px vuoto
                        "-fx-stroke-line-cap: round;"     // Arrotonda i singoli tratti per una resa più morbida
        );
        return line;
    }


}