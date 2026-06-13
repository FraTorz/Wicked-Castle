package it.unicam.cs.mpgc.rpg129691.ui.render;

/**
 * Sprite statici utilizzati dal sistema di rendering.
 *
 * Questa enum implementa {@link SpriteProvider} e rappresenta
 * elementi grafici fissi del gioco (non dinamici come player o nemici).
 *
 * Ogni valore contiene il percorso dell'immagine associata.
 */
public enum FixedSprite implements SpriteProvider {

    /**
     * Sprite del protagonista (giocatore).
     */
    HERO("/img/hero.png"),

    /**
     * Sprite della pozione di guarigione.
     */
    POTION("/img/potion.png"),

    /**
     * Sprite della trappola.
     */
    TRAP("/img/trap.png"),

    /**
     * Sprite della stanza di uscita del dungeon.
     */
    EXIT("/img/door.png"),

    /**
     * Sprite utilizzato per indicare la presenza di un indizio.
     */
    HINT("/img/hint.png");

    private final String path;

    /**
     * Costruisce uno sprite fisso associando il relativo file immagine.
     *
     * @param path percorso della risorsa grafica
     */
    FixedSprite(String path) {
        this.path = path;
    }

    /**
     * Restituisce il percorso del file immagine associato allo sprite.
     *
     * @return path della risorsa grafica
     */
    @Override
    public String getSpritePath() {
        return path;
    }
}