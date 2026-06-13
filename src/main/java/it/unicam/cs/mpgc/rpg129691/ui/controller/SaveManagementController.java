package it.unicam.cs.mpgc.rpg129691.ui.controller;

import it.unicam.cs.mpgc.rpg129691.model.game.GameEngine;
import it.unicam.cs.mpgc.rpg129691.model.game.GameSession;
import it.unicam.cs.mpgc.rpg129691.persistence.GamePersistenceService;
import it.unicam.cs.mpgc.rpg129691.persistence.SaveIndex;
import it.unicam.cs.mpgc.rpg129691.ui.utils.AlertUtils;
import it.unicam.cs.mpgc.rpg129691.ui.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller della schermata di gestione dei salvataggi.
 *
 * Permette all'utente di:
 * <ul>
 *     <li>caricare una partita salvata</li>
 *     <li>eliminare un salvataggio</li>
 *     <li>visualizzare la lista dei salvataggi disponibili</li>
 *     <li>tornare al menu principale o chiudere la finestra</li>
 * </ul>
 *
 * Il comportamento della schermata varia in base alla {@link SaveMode}:
 * <ul>
 *     <li>{@link SaveMode#MENU} → gestione completa dei salvataggi</li>
 *     <li>{@link SaveMode#GAME_LIMIT} → modalità limitata (es. obbligo di cancellazione)</li>
 * </ul>
 */
public class SaveManagementController {

    @FXML public Button loadButton;
    @FXML public Button deleteButton;
    @FXML public Button backButton;
    @FXML private ListView<SaveIndex> saveListView;

    private final GamePersistenceService persistence = new GamePersistenceService();
    private SaveMode mode = SaveMode.MENU;
    private boolean deletedSomething;

    /**
     * Inizializza la lista dei salvataggi disponibili.
     *
     * Viene eseguito automaticamente da JavaFX dopo il caricamento FXML.
     */
    @FXML
    public void initialize() {
        try {
            deletedSomething = false;
            saveListView.getItems().setAll(persistence.listSaves());
        } catch (IOException e) {
            AlertUtils.showError(
                    "Errore caricamento salvataggi",
                    "Impossibile leggere l'elenco dei salvataggi."
            );
        }
    }

    /**
     * Carica la partita selezionata e avvia la scena di gioco.
     *
     * Se nessun salvataggio è selezionato viene mostrato un warning.
     * In caso di conferma, il gioco viene ripristinato tramite {@link GamePersistenceService}.
     */
    @FXML
    private void loadGame() {
        SaveIndex selected = requireSelectedSave();
        if (selected == null) return;
        if (!AlertUtils.showConfirmation(
                "Conferma caricamento",
                "Vuoi davvero caricare la partita \"" + selected.getSaveName() + "\"?")) {
            return;
        }
        try {
            GameEngine game = persistence.loadGame(selected.getSaveName());
            GameSession.getInstance().setGame(game);
            FXMLLoader loader = SceneManager.switchSceneAndGetLoader("/fxml/Game.fxml");
            GameController controller = loader.getController();
            controller.initializeLoadedGame();
        } catch (IOException e) {
            AlertUtils.showError(
                    "Errore caricamento",
                    "Impossibile caricare il salvataggio."
            );
        }
    }

    /**
     * Elimina il salvataggio selezionato.
     *
     * Dopo l'eliminazione aggiorna la lista e imposta il flag interno
     * {@code deletedSomething} a true.
     */
    @FXML
    private void deleteGame() {
        SaveIndex selected = requireSelectedSave();
        if (selected == null) return;
        if (!AlertUtils.showConfirmation(
                "Conferma eliminazione",
                "Vuoi davvero eliminare il salvataggio \"" + selected.getSaveName() + "\"?")) {
            return;
        }
        try {
            persistence.deleteSave(selected.getSaveName());
            saveListView.getItems().remove(selected);
            deletedSomething = true;
            AlertUtils.showInfo(
                    "Salvataggio eliminato",
                    "La partita è stata eliminata con successo."
            );
        } catch (IOException e) {
            AlertUtils.showError(
                    "Errore eliminazione",
                    "Impossibile eliminare il salvataggio."
            );
        }
    }

    /**
     * Gestisce il ritorno alla schermata precedente.
     *
     * - In modalità MENU → torna al menu principale
     * - In modalità GAME_LIMIT → chiude la finestra corrente
     */
    @FXML
    private void goBack() {
        switch (mode) {
            case MENU -> SceneManager.switchScene("/fxml/MainMenu.fxml");
            case GAME_LIMIT -> ((Stage) backButton.getScene().getWindow()).close();
        }
    }

    /**
     * Verifica che un salvataggio sia selezionato nella lista.
     *
     * @return salvataggio selezionato oppure null se non presente
     */
    private SaveIndex requireSelectedSave() {
        SaveIndex selected = saveListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertUtils.showWarning(
                    "Nessun salvataggio selezionato",
                    "Seleziona una partita dalla lista."
            );
        }
        return selected;
    }

    /**
     * Imposta la modalità operativa del controller.
     *
     * La modalità influisce sulle funzionalità abilitate
     * nella schermata (es. possibilità di caricare un salvataggio).
     *
     * @param mode modalità di utilizzo della schermata
     */
    public void setMode(SaveMode mode) {
        this.mode = mode;
        loadButton.setDisable(mode == SaveMode.GAME_LIMIT);
    }

    /**
     * Indica se durante la sessione è stato eliminato almeno un salvataggio.
     *
     * @return true se almeno un salvataggio è stato eliminato
     */
    public boolean isDeletedSomething() {
        return deletedSomething;
    }
}