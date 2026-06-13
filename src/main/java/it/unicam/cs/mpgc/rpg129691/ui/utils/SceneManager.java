package it.unicam.cs.mpgc.rpg129691.ui.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Utility statica responsabile della gestione delle scene JavaFX
 * e dell'applicazione del tema grafico globale.
 *
 * <p>
 * Questa classe centralizza tutte le operazioni legate alla UI,
 * evitando duplicazione del codice nei controller.
 * </p>
 *
 * <p>In particolare gestisce:</p>
 * <ul>
 *     <li>inizializzazione dello {@link javafx.stage.Stage} principale</li>
 *     <li>transizione tra scene FXML</li>
 *     <li>caricamento di viste e relativi controller</li>
 *     <li>applicazione del foglio di stile globale (tema)</li>
 *     <li>apertura di finestre popup modali</li>
 * </ul>
 */
public class SceneManager {

    private static Stage primaryStage;
    /**
     * Percorso del file CSS globale applicato a tutte le scene.
     */
    private static final String THEME = "/css/style.css";

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
     * Sostituisce la scena corrente con una nuova scena caricata da FXML.
     *
     * <p>
     * La nuova scena viene automaticamente inizializzata con il tema grafico
     * definito in {@link #THEME}.
     * </p>
     *
     * @param fxmlPath percorso della risorsa FXML da caricare
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
     * Sostituisce la scena corrente e restituisce l'FXMLLoader associato.
     *
     * <p>
     * Questo metodo è utile quando è necessario accedere al controller
     * della nuova scena dopo il caricamento.
     * </p>
     *
     * @param fxmlPath percorso della risorsa FXML da caricare
     * @return FXMLLoader associato alla scena caricata
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
     * <p>
     * Utile per ottenere il controller associato oppure preparare
     * contenuti da visualizzare in popup.
     * </p>
     *
     * @param fxmlPath percorso della risorsa FXML da caricare
     * @return FXMLLoader già inizializzato
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
     * Mostra una finestra popup modale con il tema grafico applicato.
     *
     * <p>
     * L'esecuzione del thread JavaFX viene sospesa fino alla chiusura
     * della finestra.
     * </p>
     *
     * @param root nodo radice della finestra da mostrare
     * @param title titolo della finestra
     */
    public static void showPopup(Parent root, String title) {
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(createScene(root));
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
     * Imposta una nuova scena nello stage principale applicando il tema globale.
     *
     * @param root nodo radice della scena da visualizzare
     */
    private static void setScene(Parent root) {
        primaryStage.setScene(createScene(root));
        primaryStage.setResizable(false);
    }

    /**
     * Crea una nuova scena JavaFX applicando automaticamente il tema grafico.
     *
     * @param root nodo radice della scena
     * @return scena pronta per essere mostrata
     */
    private static Scene createScene(Parent root) {
        Scene scene = new Scene(root);
        scene.getStylesheets().add(
                Objects.requireNonNull(
                        SceneManager.class.getResource(THEME)
                ).toExternalForm()
        );
        return scene;
    }

}