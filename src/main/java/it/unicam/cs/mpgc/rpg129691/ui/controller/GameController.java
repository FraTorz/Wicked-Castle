package it.unicam.cs.mpgc.rpg129691.ui.controller;

import it.unicam.cs.mpgc.rpg129691.model.entity.Player;
import it.unicam.cs.mpgc.rpg129691.model.game.*;
import it.unicam.cs.mpgc.rpg129691.model.hint.Hint;
import it.unicam.cs.mpgc.rpg129691.persistence.GamePersistenceService;
import it.unicam.cs.mpgc.rpg129691.ui.render.MapRenderer;
import it.unicam.cs.mpgc.rpg129691.ui.render.PlayerVisualState;
import it.unicam.cs.mpgc.rpg129691.ui.utils.AlertUtils;
import it.unicam.cs.mpgc.rpg129691.ui.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Controller principale della scena di gioco.
 *
 * Gestisce:
 * <ul>
 *     <li>movimento del player</li>
 *     <li>rendering della mappa</li>
 *     <li>log eventi di gioco</li>
 *     <li>interazione con UI (salvataggi, combattimenti, mappe)</li>
 * </ul>
 *
 * È il punto centrale tra:
 * <ul>
 *     <li>GameEngine (logica)</li>
 *     <li>UI JavaFX</li>
 *     <li>Sistema di persistenza</li>
 * </ul>
 */
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

    /**
     * Inizializza la partita corrente dalla {@link GameSession}.
     *
     * Aggiorna UI e stato interno del controller.
     */
    public void initializeGame() {
        this.game = GameSession.getInstance().getGame();
        refreshLabels();
        refreshViewPort();
    }

    /**
     * Inizializza una nuova partita e stampa i messaggi iniziali nel log.
     */
    public void initializeNewGame() {
        initializeGame();
        appendLog("🎮", "Partita iniziata", "#FFFFFF");
        appendLog("•", "Ti trovi nella stanza iniziale.", "#FFFFFF");
    }

    /**
     * Inizializza una partita caricata da salvataggio.
     */
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

    /**
     * Esegue il movimento del giocatore nella direzione indicata.
     *
     * Aggiorna lo stato del gioco e rigenera la UI.
     *
     * @param direction direzione del movimento
     */
    private void move(Direction direction) {
        gameEvent = game.movePlayer(direction);
        refresh();
    }

    /**
     * Aggiorna completamente l'interfaccia grafica della partita.
     *
     * Sincronizza:
     * <ul>
     *     <li>log eventi</li>
     *     <li>stato dei pulsanti</li>
     *     <li>etichette informative</li>
     *     <li>vista della mappa</li>
     * </ul>
     */
    private void refresh(){
        updatePlayerVisualState();
        refreshLogFlow();
        refreshButtons();
        refreshLabels();
        refreshViewPort();
    }

    private void updatePlayerVisualState() {
        Player player = game.getPlayer();
        switch (game.getGameState()) {
            case PLAYER_WON -> player.setVisualState(PlayerVisualState.WIN);
            case PLAYER_LOST -> player.setVisualState(PlayerVisualState.DEAD);
            default -> player.setVisualState(PlayerVisualState.NORMAL);
        }
    }

    /**
     * Aggiorna il flusso del log eventi.
     *
     * Inserisce un separatore e stampa:
     * <ul>
     *     <li>eventuale stato della partita</li>
     *     <li>ultimo evento di gioco</li>
     * </ul>
     */
    private void refreshLogFlow() {
        logSeparator();
        logGameState();
        logGameEventMessage();
    }

    /**
     * Inserisce un separatore visivo nel log degli eventi.
     *
     * Utilizzato per migliorare la leggibilità della cronologia eventi.
     */
    private void logSeparator(){
        eventLogFlow.getChildren().addFirst(createDottedSeparator());
        eventLogFlow.getChildren().addFirst(new Text("\n"));
    }

    /**
     * Registra nel log lo stato corrente della partita.
     *
     * Mostra messaggi di vittoria o sconfitta se la partita è terminata.
     */
    private void logGameState() {
        switch (game.getGameState()) {
            case PLAYER_WON -> appendLog("🎉", "Hai trovato l'uscita! Hai vinto!", "#FFD700");
            case PLAYER_LOST -> appendLog("💀", "Sei morto. Game Over.", "#FF0000");
            default -> {}
        }
    }

    /**
     * Registra nel log il messaggio relativo all'ultimo evento di gioco.
     *
     * Se presente, include anche un eventuale {@link Hint} ottenuto dal combattimento.
     * Il messaggio viene formattato in base al tipo di evento.
     */
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

    /**
     * Aggiorna lo stato dei pulsanti della UI.
     *
     * Disabilita i controlli se la partita non è più in corso
     * e mostra il pulsante dei dettagli del combattimento se disponibile.
     */
    private void refreshButtons() {
        if(game.getGameState() != GameState.RUNNING) disableInput();
        combatDetailsButton.setVisible(gameEvent.getCombatResult().isPresent());
    }

    /**
     * Disabilita tutti i controlli di movimento e interazione.
     *
     * Utilizzato quando la partita è terminata.
     */
    private void disableInput() {
        northButton.setDisable(true);
        southButton.setDisable(true);
        eastButton.setDisable(true);
        westButton.setDisable(true);
        saveButton.setDisable(true);
        endButton.setText("Torna al menu");
    }

    /**
     * Aggiorna le etichette informative del giocatore.
     *
     * Mostra:
     * <ul>
     *     <li>difficoltà corrente</li>
     *     <li>punti vita del giocatore</li>
     *     <li>arma equipaggiata</li>
     * </ul>
     */
    private void refreshLabels() {
        difficultyLabel.setText(game.getMap().getDifficulty().toString());
        hpLabel.setText(String.valueOf(game.getPlayer().getHealth()));
        weaponLabel.setText(game.getPlayer().getEquippedWeapon().getName());
    }

    /**
     * Aggiorna la visualizzazione della mappa centrata sul giocatore.
     *
     * Utilizza {@link MapRenderer} per disegnare la viewport.
     */
    private void refreshViewPort(){
        MapRenderer.renderViewPort(mapGrid, game);
    }

    /**
     * Gestisce la chiusura della partita.
     *
     * Se la partita è in corso richiede conferma all'utente.
     * Altrimenti termina direttamente la sessione.
     */
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

    /**
     * Apre una finestra popup contenente la mappa completa del dungeon.
     */
    @FXML
    private void handleMap() {
        FXMLLoader loader = SceneManager.loadFXML("/fxml/Map.fxml");
        SceneManager.showPopup(loader.getRoot(), "Mappa");
    }

    /**
     * Mostra i dettagli dell'ultimo combattimento in una finestra popup.
     *
     * Se presente, passa il risultato del combattimento al controller dedicato.
     */
    @FXML
    private void showCombatDetails() {
        FXMLLoader loader = SceneManager.loadFXML("/fxml/CombatSummary.fxml");
        CombatSummaryController controller = loader.getController();
        gameEvent.getCombatResult().ifPresent(controller::setCombatResult);
        SceneManager.showPopup(loader.getRoot(), "Dettagli combattimento");
    }

    /**
     * Avvia il processo di salvataggio della partita corrente.
     *
     * Richiede un nome di salvataggio e gestisce errori
     * come duplicati o limite massimo raggiunto.
     */
    @FXML
    private void handleSaveGame() {
        String name = askSaveName();
        if (!isValidSaveName(name)) return;
        saveGame(name);
    }

    /**
     * Richiede all'utente il nome del salvataggio tramite dialog.
     *
     * @return nome inserito oppure null se annullato
     */
    private String askSaveName() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Salvataggio partita");
        dialog.setHeaderText("Inserisci il nome del salvataggio");
        dialog.setContentText("Nome:");
        return dialog.showAndWait().orElse(null);
    }

    /**
     * Valida il nome del salvataggio inserito dall'utente.
     *
     * @param name nome da validare
     * @return true se il nome è valido
     */
    private boolean isValidSaveName(String name) {
        if (name == null) return false;
        if (name.isBlank()) {
            AlertUtils.showWarning("Attenzione", "Nome non valido");
            return false;
        }
        return true;
    }

    /**
     * Esegue il salvataggio della partita corrente.
     *
     * Gestisce:
     * <ul>
     *     <li>nome duplicato</li>
     *     <li>limite massimo di salvataggi</li>
     *     <li>errori generici di persistenza</li>
     * </ul>
     *
     * @param name nome del salvataggio
     */
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

    /**
     * Mostra un messaggio di conferma per il salvataggio completato.
     */
    private void showSaveSuccess() {
        AlertUtils.showInfo("OK", "Salvataggio completato");
    }

    /**
     * Gestisce il caso in cui il limite massimo di salvataggi sia stato raggiunto.
     *
     * Permette all'utente di eliminare un vecchio salvataggio
     * e riprovare a salvare la partita corrente.
     *
     * @param name nome del nuovo salvataggio
     */
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

    /**
     * Apre la finestra di gestione dei salvataggi quando viene raggiunto il limite massimo.
     *
     * @return controller della finestra di gestione salvataggi
     */
    private SaveManagementController handleSaveLimitReached(){
        AlertUtils.showWarning("Attenzione", "Limite salvataggi raggiunto. " +
                "Elimina almeno una partita per salvare quella corrente.");
        FXMLLoader loader = SceneManager.loadFXML("/fxml/SaveManagement.fxml");
        SaveManagementController controller = loader.getController();
        controller.setMode(SaveMode.GAME_LIMIT);
        SceneManager.showPopup(loader.getRoot(), "Gestione salvataggi");
        return controller;
    }

    /**
     * Aggiunge un messaggio al log eventi della partita.
     *
     * Il messaggio viene formattato con:
     * <ul>
     *     <li>icona emoji</li>
     *     <li>testo colorato</li>
     * </ul>
     *
     * @param emoji icona rappresentativa dell'evento
     * @param message testo del messaggio
     * @param colorHex colore del testo in formato esadecimale
     */
    private void appendLog(String emoji, String message, String colorHex) {
        Text emojiText = new Text(emoji + " ");
        emojiText.setStyle("-fx-font-family: 'Segoe UI Emoji'; -fx-font-size: 18px;");
        Text messageText = new Text(message + "\n");
        messageText.setStyle("-fx-fill: " + colorHex + "; -fx-font-size: 15px; -fx-font-weight: bold;");
        eventLogFlow.getChildren().addFirst(messageText);
        eventLogFlow.getChildren().addFirst(emojiText);
        logScrollPane.setVvalue(0.0);
    }

    /**
     * Crea una linea tratteggiata utilizzata come separatore nel log eventi.
     *
     * @return nodo Line configurato graficamente
     */
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