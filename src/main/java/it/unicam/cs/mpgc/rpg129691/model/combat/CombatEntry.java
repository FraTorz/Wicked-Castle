package it.unicam.cs.mpgc.rpg129691.model.combat;

import it.unicam.cs.mpgc.rpg129691.model.entity.Entity;

public class CombatEntry {

    private final Entity attacker;
    private final Entity defender;
    private final int damage;

    public CombatEntry(Entity attacker, Entity defender, int damage) {
        this.attacker = attacker;
        this.defender = defender;
        this.damage = damage;
    }

    public Entity getAttacker() {
        return attacker;
    }

    public Entity getDefender() {
        return defender;
    }

    public int getDamage() {
        return damage;
    }
}