package it.unicam.cs.mpgc.rpg129691.model.entity.enemy;

import it.unicam.cs.mpgc.rpg129691.model.entity.Entity;

/**
 * Rappresenta un nemico generico del gioco.
 *
 * Un nemico è una Entity caratterizzata da:
 * <ul>
 *     <li>nome e salute (ereditati da Entity)</li>
 *     <li>danno minimo e massimo infliggibile</li>
 *     <li>uno sprite grafico per il rendering</li>
 * </ul>
 *
 * Le sottoclassi definiscono il tipo specifico di nemico.
 */
public abstract class Enemy extends Entity {

    private final int minDamage;
    private final int maxDamage;

    /**
     * Crea un nuovo nemico con valori di base per combattimento.
     *
     * @param name nome del nemico
     * @param health punti vita iniziali
     * @param minDamage danno minimo infliggibile
     * @param maxDamage danno massimo infliggibile
     */
    public Enemy(String name, int health, int minDamage, int maxDamage) {
        super(name, health);
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    /**
     * Restituisce il percorso dell'immagine associata al nemico.
     *
     * Ogni sottoclasse deve definire il proprio sprite.
     *
     * @return path della risorsa grafica del nemico
     */
    public abstract String getSpritePath();

    /**
     * Restituisce il danno minimo che il nemico può infliggere.
     *
     * @return danno minimo
     */
    @Override
    public int getMinDamage(){
        return minDamage;
    }

    /**
     * Restituisce il danno massimo che il nemico può infliggere.
     *
     * @return danno massimo
     */
    @Override
    public int getMaxDamage(){
        return maxDamage;
    }
}