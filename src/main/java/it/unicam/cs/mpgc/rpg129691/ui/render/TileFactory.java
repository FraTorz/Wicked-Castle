package it.unicam.cs.mpgc.rpg129691.ui.render;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.util.List;
import java.util.Objects;

public class TileFactory {

    private final int tileSize;
    private final int overlaySize;

    public TileFactory(int tileSize, int overlaySize) {
        this.tileSize = tileSize;
        this.overlaySize = overlaySize;
    }

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

    private void addSingleOverlay(StackPane tile, SpriteProvider overlay){
        ImageView icon = createImage(overlay.getSpritePath(), overlaySize);
        StackPane.setAlignment(icon, Pos.CENTER);
        tile.getChildren().add(icon);
    }

    private void addMultipleOverlay(StackPane tile, List<SpriteProvider> overlays) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        double spacing = (double) tileSize * 12 / 128;
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

    private ImageView createImage(String path, int size) {
        ImageView imageView = new ImageView(
                new Image(Objects.requireNonNull(TileFactory.class.getResourceAsStream(path)))
        );
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    private String getBaseImage(BaseTileType type) {
        return switch (type) {
            case FLOOR -> "/img/floor.png";
            case WALL -> "/img/wall.png";
            case UNKNOWN -> "/img/unknown.png";
        };
    }
}
