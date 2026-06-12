package it.unicam.cs.mpgc.rpg129691.model.entity.enemy;

/**
 * Rappresenta un nemico di tipo Dark Wizard.
 *
 * Il Dark Wizard è un nemico magico con:
 * <ul>
 *     <li>vita elevata</li>
 *     <li>danno elevato e variabile</li>
 * </ul>
 *
 * È una specializzazione concreta della classe Enemy.
 */
public class DarkWizard extends Enemy{

    /**
     * Crea un Dark Wizard con statistiche predefinite.
     *
     * Valori:
     * <ul>
     *     <li>HP: 50</li>
     *     <li>Danno: 10 - 30</li>
     * </ul>
     */
    public DarkWizard() {
        super("Dark Wizard", 50, 10, 30);
    }

    /**
     * Restituisce il percorso dello sprite del Dark Wizard.
     *
     * Utilizzato dal sistema di rendering per rappresentare visivamente
     * il nemico sulla mappa.
     *
     * @return path dell'immagine del Dark Wizard
     */
    @Override
    public String getSpritePath() {
        return "/img/darkWizard.png";
    }
}
