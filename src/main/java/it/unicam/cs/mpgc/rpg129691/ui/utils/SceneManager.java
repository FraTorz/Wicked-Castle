package it.unicam.cs.mpgc.rpg129691.ui.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Utility statica responsabile della gestione delle scene JavaFX.
 *
 * Centralizza le operazioni di:
 * <ul>
 *     <li>inizializzazione dello stage principale</li>
 *     <li>cambio scena dell'applicazione</li>
 *     <li>caricamento e visualizzazione di viste FXML</li>
 *     <li>apertura di finestre popup modali</li>
 * </ul>
 */
public class SceneManager {

    private static Stage primaryStage;

    /**
     * Costruttore privato per impedire l'istanziazione
     * di una utility class.
     */
    private SceneManager() {}

    /**
     * Inizializza lo stage principale dell'applicazione.
     *
     * Questo metodo deve essere invocato una sola volta
     * all'avvio dell'applicazione JavaFX.
     *
     * @param stage stage principale dell'applicazione
     */
    public static void initialize(Stage stage) {
        primaryStage = stage;
        primaryStage.setResizable(false);
    }

    /**
     * Carica un file FXML e sostituisce la scena corrente.
     *
     * @param fxmlPath percorso della risorsa FXML da caricare
     *
     * @throws RuntimeException se il file FXML non può essere caricato
     */
    public static void switchScene(String fxmlPath) {
        try {
            FXMLLoader loader = createLoader(fxmlPath);
            setScene(loadRoot(loader));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Carica un file FXML, sostituisce la scena corrente
     * e restituisce il relativo FXMLLoader.
     *
     * Utile quando è necessario accedere al controller
     * della nuova scena dopo il caricamento.
     *
     * @param fxmlPath percorso della risorsa FXML da caricare
     * @return FXMLLoader associato alla scena caricata
     *
     * @throws RuntimeException se il file FXML non può essere caricato
     */
    public static FXMLLoader switchSceneAndGetLoader(String fxmlPath) {
        try {
            FXMLLoader loader = createLoader(fxmlPath);
            setScene(loadRoot(loader));
            return loader;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Carica un file FXML senza modificarne la scena corrente.
     *
     * Utile per ottenere il controller associato oppure
     * per preparare contenuti da visualizzare successivamente.
     *
     * @param fxmlPath percorso della risorsa FXML da caricare
     * @return FXMLLoader già inizializzato
     *
     * @throws RuntimeException se il file FXML non può essere caricato
     */
    public static FXMLLoader loadFXML(String fxmlPath) {
        try {
            FXMLLoader loader = createLoader(fxmlPath);
            loadRoot(loader);
            return loader;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Mostra una finestra popup modale.
     *
     * L'esecuzione viene sospesa fino alla chiusura della finestra.
     *
     * @param root nodo radice della finestra da mostrare
     * @param title titolo della finestra
     */
    public static void showPopup(Parent root, String title) {
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.showAndWait();
    }

    /**
     * Crea un FXMLLoader associato alla risorsa FXML specificata.
     *
     * @param fxmlPath percorso della risorsa FXML
     * @return loader configurato per il file richiesto
     */
    private static FXMLLoader createLoader(String fxmlPath) {
        return new FXMLLoader(SceneManager.class.getResource(fxmlPath));
    }

    /**
     * Esegue il caricamento del contenuto FXML.
     *
     * @param loader loader da utilizzare
     * @return nodo radice generato dal file FXML
     *
     * @throws RuntimeException se il caricamento fallisce
     */
    private static Parent loadRoot(FXMLLoader loader) {
        try {
            return loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Imposta una nuova scena nello stage principale.
     *
     * @param root nodo radice della scena da visualizzare
     */
    private static void setScene(Parent root) {
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
    }

}