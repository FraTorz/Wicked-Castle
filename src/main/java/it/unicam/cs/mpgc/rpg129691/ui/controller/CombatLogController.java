package it.unicam.cs.mpgc.rpg129691.ui.controller;

import it.unicam.cs.mpgc.rpg129691.model.combat.CombatEntry;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.util.List;

/**
 * Controller della finestra di log del combattimento.
 *
 * Mostra in formato testuale lo svolgimento completo del combattimento,
 * organizzato per turni e azioni dei partecipanti.
 */
public class CombatLogController {

    @FXML private TextArea logArea;

    /**
     * Imposta e visualizza il log del combattimento.
     *
     * Gli eventi vengono raggruppati per turno e formattati
     * in modo leggibile per l'utente.
     *
     * @param entries lista degli eventi di combattimento
     */
    public void setCombatLog(List<CombatEntry> entries) {
        logArea.setText(buildLog(entries));
    }

    /**
     * Costruisce la rappresentazione testuale del combattimento.
     *
     * Ogni coppia di entry rappresenta un turno:
     * <ul>
     *     <li>azione del giocatore</li>
     *     <li>azione del nemico (se presente)</li>
     * </ul>
     *
     * @param entries lista degli eventi di combattimento
     * @return stringa formattata del log
     */
    private String buildLog(List<CombatEntry> entries) {
        StringBuilder sb = new StringBuilder();
        int turn = 1;
        for (int i = 0; i < entries.size(); i += 2) {
            sb.append("Turno ").append(turn++).append("\n");
            appendEntry(sb, entries.get(i));
            if (i + 1 < entries.size()) {
                appendEntry(sb, entries.get(i + 1));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Aggiunge una singola entry di combattimento al log.
     *
     * @param sb builder del log
     * @param e evento di combattimento
     */
    private void appendEntry(StringBuilder sb, CombatEntry e) {
        sb.append(format(e)).append("\n");
    }

    /**
     * Formatta una singola azione di combattimento.
     *
     * @param e evento di combattimento
     * @return stringa leggibile per UI
     */
    private String format(CombatEntry e) {
        return "- " + e.getAttacker().getDisplayName()
                + " infligge "
                + e.getDamage()
                + " danni a "
                + e.getDefender().getDisplayName();
    }

    /**
     * Chiude la finestra del log combattimento.
     */
    @FXML
    private void goBack() {
        ((Stage) logArea.getScene().getWindow()).close();
    }
}