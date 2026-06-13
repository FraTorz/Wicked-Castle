package it.unicam.cs.mpgc.rpg129691.ui.render;

/**
 * Interfaccia che rappresenta un'entità in grado di fornire uno sprite grafico.
 *
 * Viene utilizzata dal sistema di rendering per associare
 * un'immagine a elementi della mappa come:
 * <ul>
 *     <li>giocatore</li>
 *     <li>nemici</li>
 *     <li>oggetti</li>
 *     <li>stanze speciali</li>
 * </ul>
 *
 * Implementa un livello di astrazione tra modello di gioco
 * e rappresentazione grafica.
 */
public interface SpriteProvider {

    /**
     * Restituisce il percorso della risorsa grafica associata.
     *
     * Il path deve essere compatibile con il classpath
     * (es. {@code /img/player.png}).
     *
     * @return path dello sprite
     */
    String getSpritePath();

    /**
     * Indica se lo sprite rappresenta il giocatore.
     *
     * Questo valore viene utilizzato dal sistema di rendering
     * per gestire la priorità nella sovrapposizione degli sprite
     * (il giocatore viene sempre disegnato sopra altri elementi).
     *
     * @return true se lo sprite è il player, false altrimenti
     */
    default boolean isPlayer(){
        return false;
    }

}
