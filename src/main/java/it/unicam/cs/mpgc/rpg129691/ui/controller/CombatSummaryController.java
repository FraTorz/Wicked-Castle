package it.unicam.cs.mpgc.rpg129691.ui.controller;

import it.unicam.cs.mpgc.rpg129691.model.combat.CombatResult;
import it.unicam.cs.mpgc.rpg129691.ui.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controller della finestra di riepilogo del combattimento.
 *
 * Mostra un riassunto dell'ultimo combattimento effettuato dal giocatore,
 * includendo statistiche e informazioni sul risultato dello scontro.
 *
 * Permette inoltre di visualizzare il log dettagliato del combattimento
 * tramite una finestra dedicata.
 */
public class CombatSummaryController {

    @FXML public Button backButton;
    @FXML private Label enemyNameLabel;
    @FXML private Label turnsLabel;
    @FXML private Label damageDealtLabel;
    @FXML private Label damageTakenLabel;
    @FXML private Label playerHpLabel;
    @FXML private Label winnerLabel;

    /**
     * Risultato del combattimento da visualizzare.
     */
    private CombatResult combatResult;

    /**
     * Imposta il risultato del combattimento e aggiorna la UI.
     *
     * @param combatResult risultato del combattimento da mostrare
     */
    public void setCombatResult(CombatResult combatResult) {
        this.combatResult = combatResult;
        updateView();
    }

    /**
     * Aggiorna la vista con i dati del combattimento.
     *
     * Popola le etichette con:
     * <ul>
     *     <li>nome del nemico</li>
     *     <li>numero di turni</li>
     *     <li>danni inflitti e subiti</li>
     *     <li>HP finali del giocatore</li>
     *     <li>vincitore dello scontro</li>
     * </ul>
     */
    private void updateView() {
        enemyNameLabel.setText("Combattimento contro " + combatResult.getEnemy().getDisplayName());
        turnsLabel.setText("Turni: " + combatResult.getTurnCount());
        damageDealtLabel.setText("Danni inflitti: " + combatResult.getPlayerDamage());
        damageTakenLabel.setText("Danni subiti: " + combatResult.getEnemyDamage());
        playerHpLabel.setText("HP finali eroe: " + combatResult.getPlayerFinalHealth());
        winnerLabel.setText(combatResult.getWinnerName());
    }

    /**
     * Apre la finestra con il log dettagliato del combattimento.
     *
     * Recupera la lista degli eventi dal {@link CombatResult}
     * e la passa al controller del log.
     */
    @FXML
    private void handleShowCombatLog() {
        FXMLLoader loader = SceneManager.loadFXML("/fxml/CombatLog.fxml");
        CombatLogController controller = loader.getController();
        controller.setCombatLog(combatResult.getEntries());
        SceneManager.showPopup(loader.getRoot(), "Log combattimento");
    }

    /**
     * Chiude la finestra di riepilogo del combattimento.
     */
    @FXML
    private void goBack() {
        ((Stage) backButton.getScene().getWindow()).close();
    }
}