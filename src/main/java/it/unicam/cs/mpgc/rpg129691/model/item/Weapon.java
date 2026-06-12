package it.unicam.cs.mpgc.rpg129691.model.item;

import it.unicam.cs.mpgc.rpg129691.ui.render.SpriteProvider;

/**
 * Rappresenta un'arma utilizzabile nel gioco.
 *
 * Ogni arma definisce un intervallo di danno (minimo e massimo)
 * e un nome identificativo.
 *
 * Le armi sono utilizzate dal Player per determinare
 * il danno inflitto durante il combattimento.
 *
 * È una classe astratta per permettere la creazione di
 * tipologie diverse di armi.
 */
public abstract class Weapon implements SpriteProvider {

    private final String name;
    private final int minDamage;
    private final int maxDamage;

    /**
     * Costruisce una nuova arma.
     *
     * @param name nome dell'arma
     * @param minDamage danno minimo infliggibile
     * @param maxDamage danno massimo infliggibile
     */
    protected Weapon(String name, int minDamage, int maxDamage) {
        if (minDamage < 0 || maxDamage < 0)
            throw new IllegalArgumentException("Damage cannot be negative");
        if (minDamage > maxDamage)
            throw new IllegalArgumentException("minDamage cannot be greater than maxDamage");
        this.name = name;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    /**
     * Restituisce il nome dell'arma.
     *
     * @return nome arma
     */
    public String getName() {
        return name;
    }

    /**
     * Restituisce il danno minimo dell'arma.
     *
     * @return danno minimo
     */
    public int getMinDamage() {
        return minDamage;
    }

    /**
     * Restituisce il danno massimo dell'arma.
     *
     * @return danno massimo
     */
    public int getMaxDamage() {
        return maxDamage;
    }

}