package it.unicam.cs.mpgc.rpg129691.ui.controller;

import it.unicam.cs.mpgc.rpg129691.model.game.GameEngine;
import it.unicam.cs.mpgc.rpg129691.model.hint.Hint;
import it.unicam.cs.mpgc.rpg129691.model.map.DungeonMap;
import it.unicam.cs.mpgc.rpg129691.model.map.Position;
import it.unicam.cs.mpgc.rpg129691.ui.render.MapRenderer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class MapController {

    private GameEngine game;
    private final Map<Position, Hint> hintMap = new HashMap<>();
    @FXML private GridPane mapGrid;
    @FXML private Button backButton;

    public void setGame(GameEngine game) {
        this.game = game;
        MapRenderer.renderFullMap(mapGrid);
    }

    private void renderMap() {
        mapGrid.getChildren().clear();
        DungeonMap map = game.getMap();
        int size = map.getSize();
        hintMap.clear();
        for (Hint hint : game.getPlayer().getHintLog().getHints()) {
            hintMap.put(hint.getSourcePosition(), hint);
        }
        Position playerPos = game.getPlayer().getPosition();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Position pos = new Position(row, col);
                Label cell = new Label();
                cell.setMinSize(40, 40);
                cell.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                cell.setStyle("""
                    -fx-border-color: black;
                    -fx-alignment: center;
                """);
                if (pos.equals(playerPos)) {
                    cell.setText("P");
                }
                else if (hintMap.containsKey(pos)) {
                    Hint hint = hintMap.get(pos);
                    cell.setText("★");
                    cell.setStyle("""
                        -fx-text-fill: gold;
                        -fx-border-color: black;
                        -fx-alignment: center;
                        """);
                    Tooltip.install(cell, new Tooltip(hint.getMessage()));
                    cell.setOnMouseEntered(e ->
                        cell.setStyle("""
                            -fx-text-fill: gold;
                            -fx-background-color: khaki;
                            -fx-border-color: black;
                            -fx-alignment: center;
                        """)
                    );
                    cell.setOnMouseExited(e ->
                            cell.setStyle("""
                                -fx-text-fill: gold;
                                -fx-border-color: black;
                                -fx-alignment: center;
                            """)
                    );
                }
                else if (map.isVisited(pos)) {
                    cell.setText("·");
                }
                else {
                    cell.setText("?");
                }
                mapGrid.add(cell, col, row);
            }
        }
    }

    @FXML
    private void goBack() {
        ((Stage) backButton.getScene().getWindow()).close();
    }

}