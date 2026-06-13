package it.unicam.cs.mpgc.rpg129691.ui.render;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.util.List;
import java.util.Objects;

/**
 * Factory responsabile della costruzione grafica dei tile della mappa.
 *
 * Un tile è composto da:
 * <ul>
 *     <li>un'immagine di base ({@link BaseTileType})</li>
 *     <li>zero o più overlay ({@link SpriteProvider})</li>
 * </ul>
 *
 * La factory viene utilizzata dal sistema di rendering
 * per creare le celle visualizzate nella viewport e nella mappa completa.
 */
public class TileFactory {

    private final int tileSize;
    private final int overlaySize;
    private static final double OVERLAY_SPACING_RATIO = 12.0 / 128.0;

    /**
     * Crea una nuova factory per la generazione dei tile.
     *
     * @param tileSize dimensione del tile di base in pixel
     * @param overlaySize dimensione degli overlay in pixel
     */
    public TileFactory(int tileSize, int overlaySize) {
        this.tileSize = tileSize;
        this.overlaySize = overlaySize;
    }

    /**
     * Crea la rappresentazione grafica completa di una cella.
     *
     * Il tile risultante contiene:
     * <ul>
     *     <li>l'immagine di sfondo associata al tipo di terreno</li>
     *     <li>gli eventuali overlay da visualizzare sopra il terreno</li>
     * </ul>
     *
     * @param base tipo di tile di base
     * @param overlays overlay da sovrapporre al tile
     * @return nodo grafico rappresentante la cella
     */
    public StackPane createTile(BaseTileType base, List<SpriteProvider> overlays) {
        StackPane tile = new StackPane();
        tile.setPrefSize(tileSize, tileSize);
        tile.setMinSize(tileSize, tileSize);
        tile.setMaxSize(tileSize, tileSize);
        ImageView background = createImage(getBaseImage(base), tileSize);
        tile.getChildren().add(background);
        if (overlays != null) {
            switch(overlays.size()) {
                case 0 -> {}
                case 1 -> addSingleOverlay(tile, overlays.getFirst());
                default -> addMultipleOverlay(tile, overlays);
            }
        }
        return tile;
    }

    /**
     * Aggiunge un singolo overlay centrato sul tile.
     *
     * @param tile tile destinatario
     * @param overlay sprite da visualizzare
     */
    private void addSingleOverlay(StackPane tile, SpriteProvider overlay){
        ImageView icon = createImage(overlay.getSpritePath(), overlaySize);
        StackPane.setAlignment(icon, Pos.CENTER);
        tile.getChildren().add(icon);
    }

    /**
     * Aggiunge più overlay al tile.
     *
     * Gli overlay vengono disposti orizzontalmente
     * e centrati all'interno della cella.
     *
     * Quando presente, il giocatore viene sempre mostrato
     * come primo elemento della sequenza.
     *
     * @param tile tile destinatario
     * @param overlays overlay da visualizzare
     */
    private void addMultipleOverlay(StackPane tile, List<SpriteProvider> overlays) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        double spacing = tileSize * OVERLAY_SPACING_RATIO;
        hbox.setSpacing(spacing);
        for (SpriteProvider overlay : overlays) {
            ImageView icon = createImage(overlay.getSpritePath(), overlaySize);
            if (overlay.isPlayer()) {
                hbox.getChildren().addFirst(icon);
            } else {
                hbox.getChildren().add(icon);
            }
        }
        StackPane.setAlignment(hbox, Pos.CENTER);
        tile.getChildren().add(hbox);
    }

    /**
     * Carica un'immagine dalle risorse e crea il relativo ImageView.
     *
     * L'immagine viene ridimensionata mantenendo le proporzioni originali.
     *
     * @param path percorso della risorsa
     * @param size dimensione desiderata in pixel
     * @return ImageView configurato
     */
    private ImageView createImage(String path, int size) {
        ImageView imageView = new ImageView(
                new Image(Objects.requireNonNull(TileFactory.class.getResourceAsStream(path)))
        );
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    /**
     * Restituisce il percorso dell'immagine associata
     * a un determinato tipo di tile.
     *
     * @param type tipo di tile
     * @return percorso della risorsa grafica
     */
    private String getBaseImage(BaseTileType type) {
        return switch (type) {
            case FLOOR -> "/img/floor.png";
            case WALL -> "/img/wall.png";
            case UNKNOWN -> "/img/unknown.png";
        };
    }
}
