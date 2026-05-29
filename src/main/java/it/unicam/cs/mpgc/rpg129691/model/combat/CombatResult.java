package it.unicam.cs.mpgc.rpg129691.model.combat;

public class CombatResult {
    private final boolean playerWon;
    private final CombatLog combatLog;

    public CombatResult(boolean playerWon, CombatLog combatLog) {
        this.playerWon = playerWon;
        this.combatLog = combatLog;
    }

    public boolean hasPlayerWon() {
        return playerWon;
    }

    public CombatLog getCombatLog() {
        return combatLog;
    }
}
