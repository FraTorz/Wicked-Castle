package it.unicam.cs.mpgc.rpg129691.model.combat;

import it.unicam.cs.mpgc.rpg129691.model.entity.Entity;

import java.util.Objects;

/**
 * Rappresenta una singola azione di combattimento.
 * Contiene l'attaccante, il difensore e il danno inflitto.
 */
public class CombatEntry {

    private final Entity attacker;
    private final Entity defender;
    private final int damage;

    /**
     * Crea una nuova voce del registro di combattimento.
     *
     * @param attacker entità che esegue l'attacco
     * @param defender entità che subisce l'attacco
     * @param damage danno inflitto
     */
    public CombatEntry(Entity attacker, Entity defender, int damage) {
        this.attacker = Objects.requireNonNull(attacker);
        this.defender = Objects.requireNonNull(defender);
        this.damage = damage;
    }

    /**
     * @return l'entità che ha eseguito l'attacco
     */
    public Entity getAttacker() {
        return attacker;
    }

    /**
     * @return l'entità che ha subito l'attacco
     */
    public Entity getDefender() {
        return defender;
    }

    /**
     * @return il danno inflitto
     */
    public int getDamage(){
        return damage;
    }
}