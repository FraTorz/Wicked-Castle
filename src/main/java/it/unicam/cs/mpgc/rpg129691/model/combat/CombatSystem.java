package it.unicam.cs.mpgc.rpg129691.model.combat;

import it.unicam.cs.mpgc.rpg129691.model.entity.Entity;
import it.unicam.cs.mpgc.rpg129691.model.entity.Player;
import it.unicam.cs.mpgc.rpg129691.model.entity.enemy.Enemy;
import it.unicam.cs.mpgc.rpg129691.model.game.GameRandom;
import it.unicam.cs.mpgc.rpg129691.model.item.Weapon;

import java.util.Random;

public class CombatSystem {
    private final GameRandom random;

    public CombatSystem(GameRandom random) {
        this.random = random;
    }

    public CombatLog fight(Player player, Enemy enemy) {
        CombatLog combatLog = new CombatLog();
        combatLog.addMessage("A " + enemy.getName() + " appears!");
        while(true) {
            entityAttack(player, enemy, combatLog);
            if(!enemy.isAlive()) {
                combatLog.addMessage(enemy.getName() + " was defeated!");
                return combatLog;
            }
            entityAttack(enemy, player, combatLog);
            if(!player.isAlive()) {
                combatLog.addMessage("You were killed by " + enemy.getName());
                return combatLog;
            }
        }
    }

    private void entityAttack(Entity attacker, Entity defender, CombatLog combatLog) {
        int damage = generateDamage(attacker.getMinDamage(), attacker.getMaxDamage());
        defender.takeDamage(damage);
        combatLog.addMessage(attacker.getClass().getSimpleName() + " hits for " + damage + " damage.");
    }

    private int generateDamage(int min, int max) {
        return random.nextInt(min, max + 1);
    }
}