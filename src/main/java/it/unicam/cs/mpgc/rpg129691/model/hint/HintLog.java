package it.unicam.cs.mpgc.rpg129691.model.hint;

import java.util.HashSet;
import java.util.Set;

/**
 * Gestisce lo storico degli indizi ottenuti dal giocatore.
 *
 * Questa classe funge da contenitore (log) per tutti gli Hint
 * raccolti durante la partita.
 *
 * È utilizzata per:
 * <ul>
 *     <li>memorizzare gli indizi ottenuti</li>
 *     <li>evitare duplicati</li>
 *     <li>supportare il salvataggio e ripristino della partita</li>
 * </ul>
 */
public class HintLog {

    private final Set<Hint> hints;

    /**
     * Crea un nuovo registro degli indizi vuoto.
     */
    public HintLog() {
        this.hints = new HashSet<>();
    }

    /**
     * Aggiunge un nuovo indizio al registro.
     *
     * Se un indizio con la stessa posizione è già presente,
     * non verrà duplicato (comportamento del Set).
     *
     * @param hint indizio da aggiungere
     */
    public void add(Hint hint) {
        hints.add(hint);
    }

    /**
     * Restituisce una vista immutabile degli indizi raccolti.
     *
     * @return insieme immutabile degli indizi
     */
    public Set<Hint> getHints() {
        return Set.copyOf(hints);
    }

    /**
     * Ripristina il registro degli indizi da uno stato salvato.
     *
     * Utilizzato durante il caricamento di una partita.
     *
     * @param hints insieme degli indizi da ripristinare
     */
    public void restore(Set<Hint> hints) {
        this.hints.clear();
        this.hints.addAll(hints);
    }
}