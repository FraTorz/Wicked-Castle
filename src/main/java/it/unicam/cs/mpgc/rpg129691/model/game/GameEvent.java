package it.unicam.cs.mpgc.rpg129691.model.game;

import it.unicam.cs.mpgc.rpg129691.model.combat.CombatResult;
import it.unicam.cs.mpgc.rpg129691.model.hint.Hint;
import it.unicam.cs.mpgc.rpg129691.model.room.RoomResultType;

import java.util.Optional;

/**
 * Rappresenta un evento generato durante la partita.
 *
 * Un GameEvent descrive il risultato di una singola azione
 * del giocatore e contiene tutte le informazioni necessarie
 * per aggiornare l'interfaccia utente.
 *
 * A seconda del tipo di evento può includere:
 * <ul>
 *     <li>un messaggio descrittivo</li>
 *     <li>il risultato di un combattimento</li>
 *     <li>un indizio ottenuto</li>
 * </ul>
 */
public class GameEvent {

    private final RoomResultType type;
    private final String message;
    private final CombatResult combatResult;
    private final Hint hint;

    /**
     * Crea un nuovo evento di gioco.
     *
     * @param type tipo di evento
     * @param message messaggio associato all'evento
     * @param combatResult risultato del combattimento, se presente
     * @param hint indizio ottenuto, se presente
     */
    public GameEvent(RoomResultType type, String message, CombatResult combatResult, Hint hint) {
        this.type = type;
        this.message = message;
        this.combatResult = combatResult;
        this.hint = hint;
    }

    /**
     * Restituisce l'indizio associato all'evento.
     *
     * @return Optional contenente l'indizio se presente
     */
    public Optional<Hint> getHint() {
        return Optional.ofNullable(hint);
    }

    /**
     * Restituisce il risultato del combattimento associato
     * all'evento.
     *
     * @return Optional contenente il risultato del combattimento
     * se presente
     */
    public Optional<CombatResult> getCombatResult() {
        return Optional.ofNullable(combatResult);
    }

    /**
     * @return tipo dell'evento
     */
    public RoomResultType getType() {
        return type;
    }

    /**
     * @return messaggio descrittivo dell'evento
     */
    public String getMessage() {
        return message;
    }
}