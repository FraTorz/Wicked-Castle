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

    public CombatResult fight(Player player, Enemy enemy) {
        CombatResult result = new CombatResult(player, enemy);
        while (true) {
            entityAttack(player, enemy, result);
            if (!enemy.isAlive()) {
                result.setWinner(player);
                result.setPlayerFinalHealth(player.getHealth());
                return result;
            }
            entityAttack(enemy, player, result);
            if (!player.isAlive()) {
                result.setWinner(enemy);
                result.setPlayerFinalHealth(0);
                return result;
            }
        }
    }

    private void entityAttack(Entity attacker, Entity defender, CombatResult result) {
        int damage = generateDamage(attacker.getMinDamage(), attacker.getMaxDamage());
        defender.takeDamage(damage);
        result.addEntry(new CombatEntry(attacker, defender, damage));
    }

    private int generateDamage(int min, int max) {
        return random.nextInt(min, max + 1);
    }
}