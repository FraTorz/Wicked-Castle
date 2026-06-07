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

public class SaveManagementController {

    @FXML public Button loadButton;
    @FXML public Button deleteButton;
    @FXML public Button backButton;
    @FXML private ListView<SaveIndex> saveListView;
    private final GamePersistenceService persistence = new GamePersistenceService();
    private SaveMode mode = SaveMode.MENU;
    private boolean deletedSomething;

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

    @FXML
    private void loadGame() {
        SaveIndex selected = requireSelectedSave();
        if(selected == null) return;
        if(!AlertUtils.showConfirmation(
                "Conferma caricamento" ,
                "Vuoi davvero caricare la partita \"" + selected.getSaveName() + "\"?")){
            return;
        }
        try {
            GameEngine game = persistence.loadGame(selected.getSaveName());
            GameSession.getInstance().setGame(game);
            FXMLLoader loader = SceneManager.switchSceneAndGetLoader("/fxml/Game.fxml");
            GameController controller = loader.getController();
            controller.initializeLoadedGame();
        } catch(IOException e) {
            AlertUtils.showError(
                    "Errore caricamento",
                    "Impossibile caricare il salvataggio."
            );
        }
    }

    @FXML
    private void deleteGame() {
        SaveIndex selected = requireSelectedSave();
        if(selected == null) return;
        if(!AlertUtils.showConfirmation(
                "Conferma eliminazione" ,
                "Vuoi davvero eliminare il savataggio \"" + selected.getSaveName() + "\"?")){
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
        } catch(IOException e) {
            AlertUtils.showError(
                    "Errore eliminazione",
                    "Impossibile eliminare il salvataggio."
            );
        }
    }

    @FXML
    private void goBack() {
        switch (mode) {
            case MENU -> SceneManager.switchScene("/fxml/MainMenu.fxml");
            case GAME_LIMIT -> ((Stage) backButton.getScene().getWindow()).close();
        }
    }

    private SaveIndex requireSelectedSave() {
        SaveIndex selected = saveListView.getSelectionModel().getSelectedItem();
        if(selected == null) {
            AlertUtils.showWarning(
                    "Nessun salvataggio selezionato",
                    "Seleziona una partita dalla lista."
            );
        }
        return selected;
    }

    public void setMode(SaveMode mode) {
        this.mode = mode;
        loadButton.setDisable(mode == SaveMode.GAME_LIMIT);
    }

    public boolean isDeletedSomething() {
        return deletedSomething;
    }
}