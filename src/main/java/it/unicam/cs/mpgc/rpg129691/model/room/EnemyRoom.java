package it.unicam.cs.mpgc.rpg129691.model.room;

import it.unicam.cs.mpgc.rpg129691.model.entity.enemy.Enemy;
import it.unicam.cs.mpgc.rpg129691.model.entity.Player;
import it.unicam.cs.mpgc.rpg129691.ui.render.SpriteProvider;

import java.util.Optional;

/**
 * Rappresenta una stanza contenente un nemico.
 *
 * Quando il giocatore entra in questa stanza viene generato
 * un evento di combattimento che viene poi gestito dal GameEngine
 * tramite il CombatSystem.
 *
 * La EnemyRoom non gestisce direttamente la logica del combattimento,
 * ma si limita a descrivere l'evento che deve avvenire.
 */
public class EnemyRoom implements Room {

    private final Enemy enemy;

    /**
     * Crea una stanza contenente un nemico specifico.
     *
     * @param enemy nemico presente nella stanza
     */
    public EnemyRoom(Enemy enemy) {
        this.enemy = enemy;
    }

    /**
     * Attiva l'evento di combattimento quando il giocatore entra nella stanza.
     *
     * Non viene eseguita logica di combattimento qui:
     * viene semplicemente restituito un RoomResult che
     * verrà elaborato dal GameEngine.
     *
     * @param player il giocatore che entra nella stanza
     * @return risultato di tipo COMBAT contenente il nemico
     */
    @Override
    public RoomResult enter(Player player) {
        return new RoomResult(
                RoomResultType.COMBAT,
                enemy,
                "Hai incontrato un " + enemy.getDisplayName() + "."
        );
    }

    /**
     * Restituisce lo sprite del nemico per la visualizzazione nella mappa.
     *
     * @return sprite del nemico presente nella stanza
     */
    @Override
    public Optional<SpriteProvider> getOverlaySprite() {
        return Optional.of(enemy);
    }
}