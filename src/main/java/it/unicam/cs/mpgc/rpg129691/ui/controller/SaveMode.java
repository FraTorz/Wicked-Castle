package it.unicam.cs.mpgc.rpg129691.ui.controller;

/**
 * Modalità operative utilizzate dal sistema di gestione dei salvataggi.
 *
 * Questa enum viene utilizzata principalmente dal {@code SaveManagementController}
 * per distinguere il contesto in cui viene aperta la schermata dei salvataggi.
 *
 * Le modalità influenzano il comportamento della UI:
 * <ul>
 *     <li>{@link #MENU} → accesso ai salvataggi dal menu principale</li>
 *     <li>{@link #GAME_LIMIT} → accesso forzato quando viene raggiunto il limite di salvataggi</li>
 * </ul>
 */
public enum SaveMode {

    /**
     * Modalità di accesso ai salvataggi dal menu principale del gioco.
     * Permette operazioni standard come caricare, eliminare o creare nuovi salvataggi.
     */
    MENU,

    /**
     * Modalità attivata quando il giocatore ha raggiunto il numero massimo di salvataggi.
     *
     * In questo caso la UI potrebbe limitare le azioni disponibili,
     * ad esempio obbligando l'eliminazione di un salvataggio esistente.
     */
    GAME_LIMIT
}