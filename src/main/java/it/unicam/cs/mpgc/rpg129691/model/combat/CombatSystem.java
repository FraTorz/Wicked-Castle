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
        System.out.println("A " + enemy.getName() + " appears!");
        while(true) {
            playerAttack(player, enemy);
            if(!enemy.isAlive()) {
                System.out.println(enemy.getName() + " was defeated!");
                return CombatResult.PLAYER_WON;
            }
            enemyAttack(player, enemy);
            if(player.getHealth() <= 0) {
                System.out.println("You were killed by " + enemy.getName());
                return CombatResult.PLAYER_DIED;
            }
        }
    }

    private void playerAttack(Player player, Enemy enemy) {
        Weapon weapon = player.getEquippedWeapon();
        int damage = generateDamage(weapon.getMinDamage(), weapon.getMaxDamage());
        enemy.takeDamage(damage);
        System.out.println("You hit " + enemy.getName() + " for " + damage + " damage.");
    }

    private void enemyAttack(Player player, Enemy enemy) {
        int damage = generateDamage(enemy.getMinDamage(), enemy.getMaxDamage());
        player.takeDamage(damage);
        System.out.println(enemy.getName() + " hits you for " + damage + " damage.");
    }

    private int generateDamage(int min, int max) {
        return random.nextInt(min, max + 1);
    }
}