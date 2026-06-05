package it.unicam.cs.mpgc.rpg129691.model.game;

import it.unicam.cs.mpgc.rpg129691.model.combat.CombatLog;

public class GameEvent {
    private final String message;
    private final CombatLog combatLog;

    public GameEvent(String message, CombatLog combatLog) {
        this.message = message;
        this.combatLog = combatLog;
    }

    public String getMessage() {
        return message;
    }

    public CombatLog getCombatLog() {
        return combatLog;
    }
}