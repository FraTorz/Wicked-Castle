package it.unicam.cs.mpgc.rpg129691.ui.controller;

import it.unicam.cs.mpgc.rpg129691.model.game.GameEngine;
import it.unicam.cs.mpgc.rpg129691.persistence.GamePersistenceService;
import it.unicam.cs.mpgc.rpg129691.persistence.SaveIndex;
import it.unicam.cs.mpgc.rpg129691.ui.utils.AlertUtils;
import it.unicam.cs.mpgc.rpg129691.ui.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;

public class SaveManagementController {

    @FXML
    private ListView<SaveIndex> saveListView;
    private final GamePersistenceService persistence = new GamePersistenceService();

    @FXML
    public void initialize() {
        try {
            saveListView.getItems().addAll(persistence.listSaves());
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
        try {
            GameEngine game = persistence.loadGame(selected.getSaveName());
            System.out.println("Partita caricata");
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
        SceneManager.switchScene("/fxml/MainMenu.fxml");
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

}