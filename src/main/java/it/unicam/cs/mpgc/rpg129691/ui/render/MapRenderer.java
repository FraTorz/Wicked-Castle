package it.unicam.cs.mpgc.rpg129691.ui.render;

import it.unicam.cs.mpgc.rpg129691.model.game.GameEngine;
import it.unicam.cs.mpgc.rpg129691.model.hint.Hint;
import it.unicam.cs.mpgc.rpg129691.model.map.DungeonMap;
import it.unicam.cs.mpgc.rpg129691.model.map.Position;
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

    private static final int VIEWPORT_SIZE = 3;
    private static final int TILE_SIZE_VIEWPORT = 128;
    private static final int OVERLAY_SIZE_VIEWPORT = 64;
    private static final int TILE_SIZE_FULLMAP = 64;
    private static final int OVERLAY_SIZE_FULLMAP = 24;
    private static final Duration TOOLTIP_DELAY = Duration.millis(100);

    public static void renderViewPort(GridPane mapGrid, GameEngine game){
        mapGrid.getChildren().clear();
        setupGrid(mapGrid, VIEWPORT_SIZE, TILE_SIZE_VIEWPORT);
        TileFactory tileFactory = new TileFactory(TILE_SIZE_VIEWPORT, OVERLAY_SIZE_VIEWPORT);
        Position player = game.getPlayer().getPosition();
        int radius = VIEWPORT_SIZE / 2;
        int playerRow = player.getRow(), playerCol = player.getColumn();
        for (int r = -radius; r <= radius; r++) {
            for (int c = -radius; c <= radius; c++) {
                Position pos = new Position(playerRow + r, playerCol + c);
                StackPane tile = createTile(game, tileFactory, pos, false);
                mapGrid.add(tile, c + radius, r + radius);
            }
        }
    }

    public static void renderFullMap(GridPane mapGrid, GameEngine game){
        mapGrid.getChildren().clear();
        int size = game.getMap().getSize();
        setupGrid(mapGrid, size, TILE_SIZE_FULLMAP);
        TileFactory tileFactory = new TileFactory(TILE_SIZE_FULLMAP, OVERLAY_SIZE_FULLMAP);
        Map<Position, Hint> hintMap = buildHintMap(game);
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Position pos = new Position(row, col);
                Hint hint = hintMap.get(pos);
                boolean isHintPosition = hint != null && !pos.equals(game.getPlayer().getPosition());
                StackPane tile = createTile(game, tileFactory, pos, isHintPosition);
                if (isHintPosition) installTooltip(tile, hint.getMessage());
                mapGrid.add(tile, col, row);
            }
        }
    }

    private static Map<Position, Hint> buildHintMap(GameEngine game) {
        Map<Position, Hint> hintMap = new HashMap<>();
        for (Hint hint : game.getPlayer().getHintLog().getHints()) {
            hintMap.put(hint.getSourcePosition(), hint);
        }
        return hintMap;
    }

    private static StackPane createTile(GameEngine game, TileFactory tileFactory, Position pos, boolean isHintPosition) {
        BaseTileType base = getBaseTileType(game.getMap(), pos);
        List<SpriteProvider> overlays = getOverlaysFor(game, pos);
        if (isHintPosition) overlays.add(FixedSprite.HINT);
        return tileFactory.createTile(base, overlays);
    }

    private static BaseTileType getBaseTileType(DungeonMap map, Position pos) {
        if (!map.isInside(pos)) return BaseTileType.WALL;
        if (!map.isVisited(pos)) return BaseTileType.UNKNOWN;
        return BaseTileType.FLOOR;
    }

    private static List<SpriteProvider> getOverlaysFor(GameEngine game, Position pos) {
        List<SpriteProvider> overlays = new ArrayList<>();
        DungeonMap map = game.getMap();
        boolean isPlayerPosition = pos.equals(game.getPlayer().getPosition());
        boolean visible = map.isVisited(pos) || isPlayerPosition;
        if(!map.isInside(pos) || !visible) return overlays;
        if (isPlayerPosition) overlays.add(game.getPlayer());
        map.getRoom(pos).getOverlaySprite().ifPresent(overlays::add);
        return overlays;
    }

    private static void setupGrid(GridPane mapGrid, int size, int tileSize) {
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
        for (int i = 0; i < size; i++) {
            mapGrid.getColumnConstraints().add(createColumn(tileSize));
            mapGrid.getRowConstraints().add(createRow(tileSize));
        }
    }

    private static ColumnConstraints createColumn(int size){
        ColumnConstraints col = new ColumnConstraints(size);
        col.setMinWidth(size);
        col.setPrefWidth(size);
        col.setMaxWidth(size);
        return col;
    }

    private static RowConstraints createRow(int size){
        RowConstraints row = new RowConstraints(size);
        row.setMinHeight(size);
        row.setPrefHeight(size);
        row.setMaxHeight(size);
        return row;
    }

    private static void installTooltip(StackPane tile, String message){
        Tooltip tooltip = new Tooltip(message);
        tooltip.getStyleClass().add("custom-tooltip");
        Tooltip.install(tile, tooltip);
        tooltip.setShowDelay(TOOLTIP_DELAY);
    }
}
