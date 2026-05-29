package it.unicam.cs.mpgc.rpg129691.model.combat;

import it.unicam.cs.mpgc.rpg129691.model.character.Player;
import it.unicam.cs.mpgc.rpg129691.model.enemy.Enemy;
import it.unicam.cs.mpgc.rpg129691.model.item.Weapon;

import java.util.Random;

public class CombatSystem {
    private final Random random;
    public CombatSystem() {
        this.random = new Random();
    }
    public CombatResult fight(Player player, Enemy enemy) {
        CombatLog combatLog = new CombatLog();
        combatLog.addMessage("A " + enemy.getName() + " appears!");
        while(true) {
            playerAttack(player, enemy, combatLog);
            if(!enemy.isAlive()) {
                combatLog.addMessage(enemy.getName() + " was defeated!");
                return new CombatResult(true, combatLog);
            }
            enemyAttack(player, enemy, combatLog);
            if(player.getHealth() <= 0) {
                combatLog.addMessage("You were killed by " + enemy.getName());
                return new CombatResult(false, combatLog);
            }
        }
    }

    private void playerAttack(Player player, Enemy enemy, CombatLog combatLog) {
        Weapon weapon = player.getEquippedWeapon();
        int damage = generateDamage(weapon.getMinDamage(), weapon.getMaxDamage());
        enemy.takeDamage(damage);
        combatLog.addMessage("You hit " + enemy.getName() + " for " + damage + " damage.");
    }

    private void enemyAttack(Player player, Enemy enemy, CombatLog combatLog) {
        int damage = generateDamage(enemy.getMinDamage(), enemy.getMaxDamage());
        player.takeDamage(damage);
        combatLog.addMessage(enemy.getName() + " hits you for " + damage + " damage.");
    }

    private int generateDamage(int min, int max) {
        return random.nextInt(min, max + 1);
    }
}