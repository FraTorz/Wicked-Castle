package it.unicam.cs.mpgc.rpg129691.model.entity;

import it.unicam.cs.mpgc.rpg129691.ui.render.SpriteProvider;

/**
 * Rappresenta una generica entità combattente del gioco.
 *
 * Fornisce la gestione della salute e delle informazioni
 * necessarie al sistema di combattimento.
 */
public abstract class Entity implements SpriteProvider {

    private final String displayName;
    private int health;

    /**
     * Crea una nuova entità.
     *
     * @param displayName nome visualizzato
     * @param health salute iniziale
     */
    protected Entity(String displayName, int health) {
        this.displayName = displayName;
        this.health = health;
    }

    /**
     * @return nome visualizzato dell'entità
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @return salute corrente
     */
    public int getHealth() {
        return health;
    }

    /**
     * Riduce la salute dell'entità.
     *
     * La salute non può scendere sotto zero.
     *
     * @param damage danno subito
     */
    public void takeDamage(int damage){
        health = Math.max(0, health - damage);
    }

    /**
     * Verifica se l'entità è ancora viva.
     *
     * @return true se la salute è maggiore di zero
     */
    public boolean isAlive(){
        return health > 0;
    }

    /**
     * Restituisce il danno minimo che l'entità può infliggere.
     *
     * @return danno minimo
     */
    public abstract int getMinDamage();

    /**
     * Restituisce il danno massimo che l'entità può infliggere.
     *
     * @return danno massimo
     */
    public abstract int getMaxDamage();

    /**
     * Incrementa la salute dell'entità.
     *
     * @param amount quantità da aggiungere
     */
    protected void increaseHealth(int amount){
        if(amount <= 0) return;
        this.health += amount;
    }

    /**
     * Imposta direttamente la salute dell'entità.
     *
     * Utilizzato principalmente durante operazioni di ripristino
     * dello stato di gioco.
     *
     * @param health nuova salute
     */
    protected void setHealth(int health){
        if(health < 0)
            throw new IllegalArgumentException("Health cannot be negative");
        this.health = health;
    }
}