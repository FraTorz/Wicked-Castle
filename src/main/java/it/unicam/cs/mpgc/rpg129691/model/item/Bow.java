package it.unicam.cs.mpgc.rpg129691.model.item;

/**
 * Rappresenta un'arma di tipo Bow.
 *
 * Il Bow è un'arma a distanza con un alto potenziale di danno,
 * caratterizzata da un intervallo di danno elevato rispetto
 * alle altre armi del gioco.
 */
public class Bow extends Weapon {

    /**
     * Crea una nuova istanza di Bow con valori di danno predefiniti.
     *
     * Il Bow infligge un danno compreso tra 15 e 40.
     */
    public Bow() {
        super("Bow", 15, 40);
    }

    /**
     * Restituisce il percorso dello sprite associato al Bow.
     *
     * Utilizzato dal sistema di rendering per rappresentare
     * visivamente l'arma nell'interfaccia grafica.
     *
     * @return path dell'immagine del Bow
     */
    @Override
    public String getSpritePath() {
        return "/img/bow.png";
    }
}