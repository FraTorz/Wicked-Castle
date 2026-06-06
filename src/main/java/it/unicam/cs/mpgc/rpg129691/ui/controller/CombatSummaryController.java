package it.unicam.cs.mpgc.rpg129691.ui.controller;

import it.unicam.cs.mpgc.rpg129691.model.combat.CombatResult;
import it.unicam.cs.mpgc.rpg129691.ui.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CombatSummaryController {

    @FXML public Button backButton;
    @FXML private Label enemyNameLabel;
    @FXML private Label turnsLabel;
    @FXML private Label damageDealtLabel;
    @FXML private Label damageTakenLabel;
    @FXML private Label playerHpLabel;
    @FXML private Label winnerLabel;
    private CombatResult combatResult;

    public void setCombatResult(CombatResult combatResult) {
        this.combatResult = combatResult;
        updateView();
    }

    private void updateView() {
        enemyNameLabel.setText("Combattimento contro " + combatResult.getEnemy().getDisplayName());
        turnsLabel.setText("Turni: " + combatResult.getTurnCount());
        damageDealtLabel.setText("Danni inflitti: " + combatResult.getPlayerDamage());
        damageTakenLabel.setText("Danni subiti: " + combatResult.getEnemyDamage());
        playerHpLabel.setText("HP finali eroe: " + combatResult.getPlayerFinalHealth());
        winnerLabel.setText(combatResult.getWinnerName());
    }

    @FXML
    private void handleShowCombatLog() {
        FXMLLoader loader = SceneManager.loadFXML("/fxml/CombatLog.fxml");
        CombatLogController controller = loader.getController();
        controller.setCombatLog(combatResult.getEntries());
        SceneManager.showPopup(loader.getRoot(), "Log combattimento");
    }

    @FXML
    private void goBack() {
        ((Stage) backButton.getScene().getWindow()).close();
    }
}