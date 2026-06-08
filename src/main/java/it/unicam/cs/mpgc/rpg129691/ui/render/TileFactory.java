package it.unicam.cs.mpgc.rpg129691.ui.render;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.List;
import java.util.Objects;

public class TileFactory {

    private static final int TILE_SIZE = 128;
    private static final int OVERLAY_SIZE = 48;

    private TileFactory() {}

    public static StackPane createTile(BaseTileType base, List<SpriteProvider> overlays) {
        StackPane tile = new StackPane();
        ImageView background = createImage(getBaseImage(base), TILE_SIZE);
        tile.getChildren().add(background);
        for (SpriteProvider overlay : overlays) {
            ImageView icon = createImage(overlay.getSpritePath(), OVERLAY_SIZE);
            if (overlay.isPlayer()) {
                StackPane.setAlignment(icon, Pos.CENTER_LEFT);
                icon.setTranslateX(12);
            } else {
                StackPane.setAlignment(icon, Pos.CENTER_RIGHT);
                icon.setTranslateX(-12);
            }
            tile.getChildren().add(icon);
        }
        return tile;
    }

    private static ImageView createImage(String path, int size) {
        ImageView imageView = new ImageView(
                new Image(Objects.requireNonNull(TileFactory.class.getResourceAsStream(path)))
        );
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    private static String getBaseImage(BaseTileType type) {
        return switch (type) {
            case FLOOR -> "/img/floor.png";
            case WALL -> "/img/wall.png";
            case UNKNOWN -> "/img/unknown.png";
        };
    }
}