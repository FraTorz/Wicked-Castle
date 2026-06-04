package it.unicam.cs.mpgc.rpg129691.ui.controller;

import it.unicam.cs.mpgc.rpg129691.model.game.GameEngine;
import it.unicam.cs.mpgc.rpg129691.persistence.GamePersistenceService;
import it.unicam.cs.mpgc.rpg129691.persistence.SaveIndex;
import it.unicam.cs.mpgc.rpg129691.ui.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
            showError(
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
            showError(
                    "Errore caricamento",
                    "Impossibile caricare il salvataggio."
            );
        }
    }

    @FXML
    private void deleteGame() {
        SaveIndex selected = requireSelectedSave();
        if(selected == null) return;
        if(!confirmDeletion(selected.getSaveName())) {
            return;
        }
        try {
            persistence.deleteSave(selected.getSaveName());
            saveListView.getItems().remove(selected);
            showInfo(
                    "Salvataggio eliminato",
                    "La partita è stata eliminata con successo."
            );
        } catch(IOException e) {
            showError(
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
            showWarning(
                    "Nessun salvataggio selezionato",
                    "Seleziona una partita dalla lista."
            );
        }
        return selected;
    }

    private void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean confirmDeletion(String saveName) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conferma eliminazione");
        alert.setHeaderText(null);
        alert.setContentText("Vuoi davvero eliminare il salvataggio \"" + saveName + "\"?");
        return alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .isPresent();
    }
}