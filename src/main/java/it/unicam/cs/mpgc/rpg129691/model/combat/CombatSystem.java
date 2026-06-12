package it.unicam.cs.mpgc.rpg129691.model.combat;

import it.unicam.cs.mpgc.rpg129691.model.entity.Entity;
import it.unicam.cs.mpgc.rpg129691.model.entity.Player;
import it.unicam.cs.mpgc.rpg129691.model.entity.enemy.Enemy;
import it.unicam.cs.mpgc.rpg129691.model.game.GameRandom;

/**
 * Gestisce la risoluzione dei combattimenti tra il giocatore e un nemico.
 * Il combattimento procede a turni alternati fino alla sconfitta di uno
 * dei due partecipanti.
 */
public class CombatSystem {
    private final GameRandom random;

    /**
     * Crea un nuovo sistema di combattimento.
     *
     * @param random generatore casuale utilizzato per il calcolo dei danni
     */
    public CombatSystem(GameRandom random) {
        this.random = random;
    }

    /**
     * Esegue un combattimento completo tra giocatore e nemico.
     *
     * @param player giocatore coinvolto nello scontro
     * @param enemy nemico coinvolto nello scontro
     * @return risultato dettagliato del combattimento
     */
    public CombatResult fight(Player player, Enemy enemy) {
        CombatResult result = new CombatResult(player, enemy);
        while (player.isAlive() && enemy.isAlive()) {
            entityAttack(player, enemy, result);
            if (enemy.isAlive()) {
                entityAttack(enemy, player, result);
            }
        }
        finalizeResult(result, player, enemy);
        return result;
    }

    /**
     * Imposta il vincitore e i dati finali del combattimento.
     */
    private void finalizeResult(CombatResult result, Player player, Enemy enemy) {
        if (player.isAlive()) {
            result.setWinner(player);
            result.setPlayerFinalHealth(player.getHealth());
        } else {
            result.setWinner(enemy);
            result.setPlayerFinalHealth(0);
        }
    }

    /**
     * Esegue un singolo attacco e registra il risultato.
     */
    private void entityAttack(Entity attacker, Entity defender, CombatResult result) {
        int damage = generateDamage(attacker.getMinDamage(), attacker.getMaxDamage());
        defender.takeDamage(damage);
        result.addEntry(new CombatEntry(attacker, defender, damage));
    }

    /**
     * Genera un valore casuale compreso tra min e max inclusi.
     */
    private int generateDamage(int min, int max) {
        return random.nextInt(min, max + 1);
    }
}