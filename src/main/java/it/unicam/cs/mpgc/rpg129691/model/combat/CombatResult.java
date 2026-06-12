package it.unicam.cs.mpgc.rpg129691.model.combat;

import it.unicam.cs.mpgc.rpg129691.model.entity.Entity;
import it.unicam.cs.mpgc.rpg129691.model.entity.Player;
import it.unicam.cs.mpgc.rpg129691.model.entity.enemy.Enemy;

import java.util.ArrayList;
import java.util.List;

/**
 * Contiene tutte le informazioni relative ad un combattimento concluso.
 *
 * Memorizza:
 * <ul>
 *     <li>Giocatore coinvolto</li>
 *     <li>Nemico affrontato</li>
 *     <li>Storico degli attacchi effettuati</li>
 *     <li>Vincitore del combattimento</li>
 *     <li>Salute finale del giocatore</li>
 * </ul>
 */
public class CombatResult {

    private final Player player;
    private final Enemy enemy;
    private Entity winner;
    private int playerFinalHealth;
    private final List<CombatEntry> entries;

    /**
     * Crea un nuovo risultato di combattimento.
     *
     * @param player giocatore coinvolto
     * @param enemy nemico affrontato
     */
    public CombatResult(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
        this.entries = new ArrayList<>();
    }

    /**
     * Aggiunge una nuova azione di combattimento allo storico.
     *
     * @param entry azione da registrare
     */
    public void addEntry(CombatEntry entry) {
        entries.add(entry);
    }

    /**
     * Restituisce il numero di turni del combattimento.
     * Ogni turno può generare fino a due CombatEntry:
     * uno del giocatore e uno del nemico.
     *
     * @return numero totale di turni
     */
    public int getTurnCount() {
        return (entries.size() + 1) / 2;
    }

    /**
     * Restituisce il danno totale inflitto dal giocatore.
     *
     * @return danno totale inflitto dal giocatore
     */
    public int getPlayerDamage() {
        return getTotalDamageBy(player);
    }

    /**
     * Restituisce il danno totale inflitto dal nemico.
     *
     * @return danno totale inflitto dal nemico
     */
    public int getEnemyDamage() {
        return getTotalDamageBy(enemy);
    }

    /**
     * Calcola il danno totale inflitto da una specifica entità.
     */
    private int getTotalDamageBy(Entity attacker) {
        return entries.stream()
                .filter(entry -> entry.getAttacker() == attacker)
                .mapToInt(CombatEntry::getDamage)
                .sum();
    }

    /**
     * Restituisce il nome visualizzato del vincitore.
     *
     * @return nome del vincitore
     * @throws IllegalStateException se il vincitore non è stato ancora impostato
     */
    public String getWinnerName() {
        if (winner == null) throw new IllegalStateException("Winner not set");
        return winner.getDisplayName();
    }

    /**
     * Imposta il vincitore del combattimento.
     */
    public void setWinner(Entity winner) {
        this.winner = winner;
    }

    /**
     * Imposta la salute finale del giocatore.
     */
    public void setPlayerFinalHealth(int playerFinalHealth) {
        this.playerFinalHealth = playerFinalHealth;
    }

    /**
     * Restituisce il giocatore coinvolto nel combattimento.
     *
     * @return giocatore combattente
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Restituisce il nemico affrontato.
     *
     * @return nemico combattente
     */
    public Enemy getEnemy() {
        return enemy;
    }

    /**
     * Restituisce la salute del giocatore al termine del combattimento.
     *
     * @return salute finale del giocatore
     */
    public int getPlayerFinalHealth() {
        return playerFinalHealth;
    }

    /**
     * Restituisce una copia immutabile dello storico del combattimento.
     *
     * @return lista delle azioni registrate
     */
    public List<CombatEntry> getEntries() {
        return List.copyOf(entries);
    }
}