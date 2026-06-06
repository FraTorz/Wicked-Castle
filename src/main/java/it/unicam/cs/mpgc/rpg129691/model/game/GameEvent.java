package it.unicam.cs.mpgc.rpg129691.model.game;

import it.unicam.cs.mpgc.rpg129691.model.combat.CombatLog;

public class GameEvent {

    private final EventType type;
    private final String message;
    private final CombatLog combatLog;

    public GameEvent(EventType type, String message, CombatLog combatLog) {
        this.type = type;
        this.message = message;
        this.combatLog = combatLog;
    }

    public EventType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public CombatLog getCombatLog() {
        return combatLog;
    }
}