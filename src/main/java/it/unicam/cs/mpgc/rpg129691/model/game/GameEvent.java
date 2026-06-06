package it.unicam.cs.mpgc.rpg129691.model.game;

import it.unicam.cs.mpgc.rpg129691.model.combat.CombatResult;
import it.unicam.cs.mpgc.rpg129691.model.hint.Hint;
import it.unicam.cs.mpgc.rpg129691.model.room.RoomResultType;

import java.util.Optional;

public class GameEvent {

    private final RoomResultType type;
    private final String message;
    private final CombatResult combatResult;
    private final Hint hint;

    public GameEvent(RoomResultType type, String message, CombatResult combatResult, Hint hint) {
        this.type = type;
        this.message = message;
        this.combatResult = combatResult;
        this.hint = hint;
    }

    public Optional<Hint> getHint() {
        return Optional.ofNullable(hint);
    }

    public Optional<CombatResult> getCombatResult() {
        return Optional.ofNullable(combatResult);
    }

    public RoomResultType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}