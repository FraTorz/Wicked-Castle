package it.unicam.cs.mpgc.rpg129691.model.game;

import it.unicam.cs.mpgc.rpg129691.model.combat.CombatResult;
import it.unicam.cs.mpgc.rpg129691.model.hint.Hint;
import it.unicam.cs.mpgc.rpg129691.model.hint.HintGenerator;
import it.unicam.cs.mpgc.rpg129691.model.entity.Player;
import it.unicam.cs.mpgc.rpg129691.model.combat.CombatSystem;
import it.unicam.cs.mpgc.rpg129691.model.map.DungeonMap;
import it.unicam.cs.mpgc.rpg129691.model.map.Position;
import it.unicam.cs.mpgc.rpg129691.model.room.*;

/**
 * Gestisce la logica principale della partita.
 *
 * Coordina:
 * <ul>
 *     <li>movimento del giocatore</li>
 *     <li>interazione con le stanze</li>
 *     <li>combattimenti</li>
 *     <li>generazione degli indizi</li>
 *     <li>aggiornamento dello stato della partita</li>
 * </ul>
 *
 * Rappresenta il punto centrale del model durante una sessione di gioco.
 */
public class GameEngine {
    private final DungeonMap map;
    private final Player player;
    private final CombatSystem combatSystem;
    private final HintGenerator hintGenerator;
    private GameState gameState;

    /**
     * Crea una nuova istanza del motore di gioco.
     *
     * @param map mappa della partita
     * @param player giocatore controllato dall'utente
     * @param combatSystem sistema utilizzato per risolvere i combattimenti
     */
    public GameEngine(DungeonMap map, Player player, CombatSystem combatSystem) {
        this.map = map;
        this.player = player;
        this.combatSystem = combatSystem;
        this.hintGenerator = new HintGenerator();
        this.gameState = GameState.RUNNING;
    }

    /**
     * Esegue il tentativo di movimento del giocatore nella
     * direzione indicata.
     *
     *  Se il movimento non è valido viene restituito un evento
     *  di tipo {@code INVALID_MOVE} senza modificare lo stato
     *  della partita. Se il movimento è valido:
     * <ul>
     *     <li>aggiorna la posizione del giocatore</li>
     *     <li>segna la stanza come visitata</li>
     *     <li>attiva l'effetto della stanza</li>
     *     <li>aggiorna lo stato della partita</li>
     * </ul>
     *
     * @param direction direzione scelta dal giocatore
     * @return evento generato dall'azione eseguita
     */
    public GameEvent movePlayer(Direction direction) {
        consumeCurrentRoom();
        Position next = nextPosition(direction);
        if (!map.isInside(next)) return invalidMove();
        player.moveTo(next);
        map.visit(next);
        Room room = map.getRoom(next);
        RoomResult result = room.enter(player);
        GameEvent event = handleRoomResult(result);
        updateGameState(result.getType());
        return event;
    }

    /**
     * Crea l'evento associato ad un tentativo di movimento
     * verso una posizione esterna alla mappa.
     *
     * @return evento di movimento non valido
     */
    private GameEvent invalidMove(){
        return new GameEvent(
                RoomResultType.INVALID_MOVE,
                "Non puoi andare in quella direzione.",
                null,
                null
        );
    }

    /**
     * Consuma la stanza attualmente occupata dal giocatore.
     *
     * Le stanze consumabili vengono sostituite con una
     * EmptyRoom dopo essere state utilizzate.
     */
    private void consumeCurrentRoom(){
        Position current = player.getPosition();
        if(map.getRoom(current).isConsumable())
            map.setRoom(current, new EmptyRoom());
    }

    /**
     * Calcola la posizione adiacente nella direzione indicata.
     *
     * @param direction direzione di movimento
     * @return nuova posizione teorica del giocatore
     */
    private Position nextPosition(Direction direction){
        Position current = player.getPosition();
        return switch(direction) {
            case NORTH -> new Position(current.getRow() - 1, current.getColumn());
            case SOUTH -> new Position(current.getRow() + 1, current.getColumn());
            case EAST -> new Position(current.getRow(), current.getColumn() + 1);
            case WEST -> new Position(current.getRow(), current.getColumn() - 1);
        };
    }

    /**
     * Converte il risultato prodotto da una stanza
     * in un GameEvent utilizzabile dall'interfaccia.
     *
     * @param result risultato dell'interazione con la stanza
     * @return evento generato
     */
    private GameEvent handleRoomResult(RoomResult result) {
        if(result.getType() == RoomResultType.COMBAT) {
            return handleCombat(result);
        }
         return new GameEvent(result.getType(), result.getMessage(), null, null);
    }

    /**
     * Risolve un combattimento e genera l'eventuale indizio
     * ottenuto dopo la vittoria.
     *
     * @param result risultato della EnemyRoom
     * @return evento contenente il resoconto del combattimento
     */
    private GameEvent handleCombat(RoomResult result){
        CombatResult combatResult = combatSystem.fight(player, result.getEnemy());
        Hint hint = null;
        if(player.isAlive()){
            hint = hintGenerator.generate(player.getPosition(), map.getExitPosition());
            player.addHint(hint);
        }
        return new GameEvent(result.getType(), result.getMessage(), combatResult, hint);
    }

    /**
     * Aggiorna lo stato della partita in base all'esito
     * dell'ultima azione eseguita.
     *
     * @param result tipo di risultato ottenuto
     */
    private void updateGameState(RoomResultType result) {
        if(!player.isAlive()) {
            gameState = GameState.PLAYER_LOST;
            return;
        }
        if(result == RoomResultType.PLAYER_ESCAPED) {
            gameState = GameState.PLAYER_WON;
        }
    }

    /**
     * @return mappa della partita
     */
    public DungeonMap getMap(){
        return map;
    }

    /**
     * @return giocatore corrente
     */
    public Player getPlayer(){
        return player;
    }

    /**
     * @return stato attuale della partita
     */
    public GameState getGameState(){
        return gameState;
    }

}
