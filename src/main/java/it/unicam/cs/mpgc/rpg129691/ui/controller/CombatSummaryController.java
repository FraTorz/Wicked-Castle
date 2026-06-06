package it.unicam.cs.mpgc.rpg129691.ui.controller;

import it.unicam.cs.mpgc.rpg129691.model.combat.CombatResult;
import javafx.fxml.FXML;
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
        enemyNameLabel.setText("Combattimento contro " + combatResult.getEnemy().getName());
        turnsLabel.setText("Turni: " + combatResult.getTurnCount());
        damageDealtLabel.setText("Danni inflitti: " + combatResult.getPlayerDamage());
        damageTakenLabel.setText("Danni subiti: " + combatResult.getEnemyDamage());
        playerHpLabel.setText("HP finali eroe: " + combatResult.getPlayerFinalHealth());
        winnerLabel.setText(combatResult.getWinnerName());
    }

    @FXML
    public void handleShowCombatLog() {

    }

    @FXML
    private void goBack() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
}