package it.unicam.cs.mpgc.rpg129691.model.combat;

import it.unicam.cs.mpgc.rpg129691.model.entity.Entity;
import it.unicam.cs.mpgc.rpg129691.model.entity.Player;
import it.unicam.cs.mpgc.rpg129691.model.entity.enemy.Enemy;

import java.util.ArrayList;
import java.util.List;

public class CombatResult {

    private final Player player;
    private final Enemy enemy;
    private Entity winner;
    private int playerFinalHealth;
    private final List<CombatEntry> entries;

    public CombatResult(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
        this.entries = new ArrayList<>();
    }

    public void addEntry(CombatEntry entry) {
        entries.add(entry);
    }

    public int getTurnCount() {
        return (entries.size() + 1) / 2;
    }

    public int getPlayerDamage() {
        return getTotalDamageBy(player);
    }

    public int getEnemyDamage() {
        return getTotalDamageBy(enemy);
    }

    private int getTotalDamageBy(Entity attacker) {
        int total = 0;
        for (CombatEntry entry : entries) {
            if (entry.getAttacker() == attacker) {
                total += entry.getDamage();
            }
        }
        return total;
    }

    public String getWinnerName() {
        return winner == player ? "EROE" : enemy.getDisplayName();
    }

    public void setWinner(Entity winner) {
        this.winner = winner;
    }

    public void setPlayerFinalHealth(int playerFinalHealth) {
        this.playerFinalHealth = playerFinalHealth;
    }

    public Player getPlayer() {
        return player;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public Entity getWinner() {
        return winner;
    }

    public int getPlayerFinalHealth() {
        return playerFinalHealth;
    }

    public List<CombatEntry> getEntries() {
        return List.copyOf(entries);
    }
}