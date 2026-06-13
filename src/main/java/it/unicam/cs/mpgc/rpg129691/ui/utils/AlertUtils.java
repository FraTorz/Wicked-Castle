package it.unicam.cs.mpgc.rpg129691.ui.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Classe di utilità per la visualizzazione di finestre di dialogo JavaFX.
 *
 * Fornisce metodi statici per mostrare messaggi informativi,
 * avvisi, errori e richieste di conferma.
 */
public final class AlertUtils {

    private AlertUtils() {}

    /**
     * Mostra una finestra informativa.
     *
     * @param title titolo della finestra
     * @param message messaggio da visualizzare
     */
    public static void showInfo(String title, String message) {
        show(Alert.AlertType.INFORMATION, title, message);
    }

    /**
     * Mostra una finestra di avviso.
     *
     * @param title titolo della finestra
     * @param message messaggio da visualizzare
     */
    public static void showWarning(String title, String message) {
        show(Alert.AlertType.WARNING, title, message);
    }

    /**
     * Mostra una finestra di errore.
     *
     * @param title titolo della finestra
     * @param message messaggio da visualizzare
     */
    public static void showError(String title, String message) {
        show(Alert.AlertType.ERROR, title, message);
    }

    /**
     * Mostra una finestra di conferma.
     *
     * @param title titolo della finestra
     * @param message messaggio da visualizzare
     * @return true se l'utente conferma premendo OK,
     *         false in caso contrario
     */
    public static boolean showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .isPresent();
    }

    /**
     * Mostra una finestra di dialogo del tipo specificato.
     *
     * @param type tipo di alert da visualizzare
     * @param title titolo della finestra
     * @param message messaggio da visualizzare
     */
    private static void show(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}