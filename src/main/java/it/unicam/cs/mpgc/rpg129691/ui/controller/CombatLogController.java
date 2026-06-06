package it.unicam.cs.mpgc.rpg129691.ui.controller;

import it.unicam.cs.mpgc.rpg129691.model.combat.CombatEntry;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.util.List;

public class CombatLogController {

    @FXML private TextArea logArea;

    public void setCombatLog(List<CombatEntry> entries) {
        StringBuilder sb = new StringBuilder();
        int turn = 1;
        for (int i = 0; i < entries.size(); i += 2) {
            sb.append("Turno ").append(turn++).append("\n");
            CombatEntry playerEntry = entries.get(i);
            sb.append(format(playerEntry)).append("\n");
            if (i + 1 < entries.size()) {
                CombatEntry enemyEntry = entries.get(i + 1);
                sb.append(format(enemyEntry)).append("\n");
            }
            sb.append("\n");
        }
        logArea.setText(sb.toString());
    }
    private String format(CombatEntry e) {
        return "- " + e.getAttacker().getDisplayName()
                + " -> "
                + e.getDamage()
                + " danni a "
                + e.getDefender().getDisplayName();
    }

    @FXML
    private void goBack() {
        ((Stage) logArea.getScene().getWindow()).close();
    }
}