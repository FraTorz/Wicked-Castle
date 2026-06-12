package it.unicam.cs.mpgc.rpg129691.model.item;

import it.unicam.cs.mpgc.rpg129691.model.game.GameRandom;

/**
 * Factory responsabile della creazione delle armi nel gioco.
 *
 * Fornisce due modalità di creazione:
 * <ul>
 *     <li>Generazione casuale di un'arma (loot system)</li>
 *     <li>Ricostruzione di un'arma a partire dal nome (persistenza)</li>
 * </ul>
 *
 * Questa classe centralizza la logica di istanziazione delle armi
 * per evitare duplicazioni e facilitare eventuali estensioni future.
 */
public class WeaponFactory {

    private final GameRandom random;

    /**
     * Crea una nuova WeaponFactory basata su un generatore di numeri casuali.
     *
     * @param random generatore casuale utilizzato per la creazione delle armi
     */
    public WeaponFactory(GameRandom random) {
        this.random = random;
    }

    /**
     * Genera un'arma casuale con distribuzione probabilistica.
     *
     * Le probabilità attuali sono:
     * <ul>
     *     <li>40% Sword</li>
     *     <li>30% Axe</li>
     *     <li>20% Bow</li>
     *     <li>10% Knife</li>
     * </ul>
     *
     * @return arma generata casualmente
     */
    public Weapon createRandomWeapon() {
        int value = random.nextInt(100);
        if (value < 40) return new Sword();
        if (value < 70) return new Axe();
        if (value < 90) return new Bow();
        return new Knife();
    }

    /**
     * Ricostruisce un'arma a partire dal suo nome.
     *
     * Questo metodo è utilizzato principalmente durante il caricamento
     * di una partita salvata.
     *
     * @param weaponName nome dell'arma da ricreare
     * @return istanza dell'arma corrispondente
     * @throws IllegalArgumentException se il nome non corrisponde a nessuna arma conosciuta
     */
    public static Weapon createWeapon(String weaponName) {
        return switch (weaponName) {
            case "Knife" -> new Knife();
            case "Sword" -> new Sword();
            case "Axe" -> new Axe();
            case "Bow" -> new Bow();
            default -> throw new IllegalArgumentException("Unknown weapon: " + weaponName);
        };
    }
}