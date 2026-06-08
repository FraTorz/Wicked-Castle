package it.unicam.cs.mpgc.rpg129691.ui.render;

import it.unicam.cs.mpgc.rpg129691.model.game.GameEngine;
import it.unicam.cs.mpgc.rpg129691.model.game.GameSession;
import it.unicam.cs.mpgc.rpg129691.model.hint.Hint;
import it.unicam.cs.mpgc.rpg129691.model.map.DungeonMap;
import it.unicam.cs.mpgc.rpg129691.model.map.Position;
import it.unicam.cs.mpgc.rpg129691.model.room.Room;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapRenderer {

    private static GameEngine game;
    private static TileFactory tileFactory;
    private static final int TILE_SIZE_VIEWPORT = 128;
    private static final int OVERLAY_SIZE_VIEWPORT = 64;
    private static final int TILE_SIZE_FULLMAP = 64;
    private static final int OVERLAY_SIZE_FULLMAP = 24;

    public static void renderViewPort(GridPane mapGrid){
        game = GameSession.getInstance().getGame();
        tileFactory = new TileFactory(TILE_SIZE_VIEWPORT, OVERLAY_SIZE_VIEWPORT);
        mapGrid.getChildren().clear();
        setupGrid(mapGrid, 3, TILE_SIZE_VIEWPORT);
        Position player = game.getPlayer().getPosition();
        int playerRow = player.getRow();
        int playerCol = player.getColumn();
        for (int r = -1; r <= 1; r++) {
            for (int c = -1; c <= 1; c++) {
                int worldRow = playerRow + r;
                int worldCol = playerCol + c;
                Position pos = new Position(worldRow, worldCol);
                BaseTileType base = getBaseTileType(pos);
                List<SpriteProvider> overlays = getOverlaysFor(pos);
                StackPane tile = tileFactory.createTile(base, overlays);
                setTileSize(tile, TILE_SIZE_VIEWPORT);
                mapGrid.add(tile, c + 1, r + 1);
            }
        }
    }

    public static void renderFullMap(GridPane mapGrid){
        game = GameSession.getInstance().getGame();
        tileFactory = new TileFactory(TILE_SIZE_FULLMAP, OVERLAY_SIZE_FULLMAP);
        mapGrid.getChildren().clear();
        int size = game.getMap().getSize();
        setupGrid(mapGrid, size, TILE_SIZE_FULLMAP);
        Map<Position, Hint> hintMap = new HashMap<>();
        for (Hint hint : game.getPlayer().getHintLog().getHints()) {
            hintMap.put(hint.getSourcePosition(), hint);
        }
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Position pos = new Position(row, col);
                BaseTileType base = getBaseTileType(pos);
                List<SpriteProvider> overlays = getOverlaysFor(pos);
                if(hintMap.containsKey(pos) && !pos.equals(game.getPlayer().getPosition()))
                    overlays.add(FixedSprite.HINT);
                StackPane tile = tileFactory.createTile(base, overlays);
                setTileSize(tile, TILE_SIZE_FULLMAP);
                if(hintMap.containsKey(pos)) installTooltip(tile, hintMap.get(pos).getMessage());
                mapGrid.add(tile, col, row);
            }
        }
    }

    private static BaseTileType getBaseTileType(Position pos) {
        DungeonMap map = game.getMap();
        if (!map.isInside(pos)) return BaseTileType.WALL;
        if (!map.isVisited(pos)) return BaseTileType.UNKNOWN;
        return BaseTileType.FLOOR;
    }

    private static List<SpriteProvider> getOverlaysFor(Position pos) {
        List<SpriteProvider> overlays = new ArrayList<>();
        DungeonMap map = game.getMap();
        boolean isPlayerPosition = pos.equals(game.getPlayer().getPosition());
        if(!map.isInside(pos)) return overlays;
        if (!map.isVisited(pos) && !isPlayerPosition) return overlays;
        if (isPlayerPosition) overlays.add(game.getPlayer());
        Room room = map.getRoom(pos);
        room.getOverlaySprite().ifPresent(overlays::add);
        return overlays;
    }


    private static void setupGrid(GridPane mapGrid, int size, int tileSize) {
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
        for (int i = 0; i < size; i++) {
            ColumnConstraints col = new ColumnConstraints(tileSize);
            RowConstraints row = new RowConstraints(tileSize);
            col.setMinWidth(tileSize);
            col.setPrefWidth(tileSize);
            col.setMaxWidth(tileSize);
            row.setMinHeight(tileSize);
            row.setPrefHeight(tileSize);
            row.setMaxHeight(tileSize);
            mapGrid.getColumnConstraints().add(col);
            mapGrid.getRowConstraints().add(row);
        }
    }

    private static void setTileSize(StackPane tile, int size){
        tile.setMinSize(size, size);
        tile.setPrefSize(size, size);
        tile.setMaxSize(size, size);
    }

    private static void installTooltip(StackPane tile, String message){
        Tooltip tooltip = new Tooltip(message);
        tooltip.getStyleClass().add("custom-tooltip");
        Tooltip.install(tile, tooltip);
        tooltip.setShowDelay(Duration.millis(100));
    }
}
