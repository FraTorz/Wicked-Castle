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

/**
 * Utility statica responsabile del rendering grafico della mappa di gioco.
 *
 * Fornisce metodi per visualizzare:
 * <ul>
 *     <li>la porzione di mappa visibile attorno al giocatore (viewport)</li>
 *     <li>l'intera mappa esplorata durante la partita</li>
 * </ul>
 *
 * La rappresentazione grafica viene costruita tramite
 * {@link TileFactory}, combinando tile di base e overlay
 * associati alle stanze, al giocatore e agli indizi raccolti.
 *
 * Tutti i metodi sono statici e la classe non mantiene stato.
 */
public class MapRenderer {

    private static final int VIEWPORT_SIZE = 3;
    private static final int TILE_SIZE_VIEWPORT = 128;
    private static final int OVERLAY_SIZE_VIEWPORT = 64;
    private static final int TILE_SIZE_FULLMAP = 64;
    private static final int OVERLAY_SIZE_FULLMAP = 24;
    private static final Duration TOOLTIP_DELAY = Duration.millis(100);

    /**
     * Costruttore privato per impedire l'istanziazione
     * di una utility class.
     */
    private MapRenderer() {}

    /**
     * Renderizza la viewport centrata sulla posizione attuale del giocatore.
     *
     * Vengono mostrate solamente le celle adiacenti entro il raggio
     * definito da {@code VIEWPORT_SIZE}.
     *
     * @param mapGrid griglia JavaFX da aggiornare
     * @param game partita corrente
     */
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

    /**
     * Renderizza l'intera mappa del dungeon.
     *
     * Le celle visitate vengono mostrate normalmente, mentre quelle
     * ancora inesplorate risultano nascoste.
     * Gli indizi raccolti vengono evidenziati e associati a un tooltip.
     *
     * @param mapGrid griglia JavaFX da aggiornare
     * @param game partita corrente
     */
    public static void renderFullMap(GridPane mapGrid, GameEngine game){
        mapGrid.getChildren().clear();
        int size = game.getMap().getSize();
        setupGrid(mapGrid, size, TILE_SIZE_FULLMAP);
        TileFactory tileFactory = new TileFactory(TILE_SIZE_FULLMAP, OVERLAY_SIZE_FULLMAP);
        Map<Position, Hint> hintMap = buildHintMap(game);
        renderFullMapTiles(mapGrid, game, tileFactory, hintMap, size);
    }

    /**
     * Costruisce una mappa che associa ogni posizione visitata
     * all'indizio generato in quella posizione.
     *
     * @param game partita corrente
     * @return mappa posizione-indizio
     */
    private static Map<Position, Hint> buildHintMap(GameEngine game) {
        Map<Position, Hint> hintMap = new HashMap<>();
        for (Hint hint : game.getPlayer().getHintLog().getHints()) {
            hintMap.put(hint.getSourcePosition(), hint);
        }
        return hintMap;
    }

    /**
     * Renderizza tutte le celle della mappa completa.
     *
     * Itera sull'intera griglia del dungeon delegando
     * la creazione grafica di ogni singola cella al metodo
     * {@link #renderTile(GridPane, GameEngine, TileFactory, Map, int, int)}.
     *
     * @param mapGrid griglia JavaFX da aggiornare
     * @param game partita corrente
     * @param tileFactory factory utilizzata per creare i tile
     * @param hintMap mappa degli indizi associati alle posizioni
     * @param size dimensione della mappa
     */
    private static void renderFullMapTiles(GridPane mapGrid, GameEngine game, TileFactory tileFactory,
                                           Map<Position, Hint> hintMap, int size) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                renderTile(mapGrid, game, tileFactory, hintMap, row, col);
            }
        }
    }

    /**
     * Renderizza una singola cella della mappa completa.
     *
     * Se la posizione contiene un indizio già scoperto,
     * viene aggiunto l'apposito overlay e associato un tooltip
     * contenente il messaggio dell'indizio.
     *
     * @param mapGrid griglia JavaFX destinataria
     * @param game partita corrente
     * @param tileFactory factory utilizzata per creare il tile
     * @param hintMap mappa degli indizi associati alle posizioni
     * @param row riga della cella
     * @param col colonna della cella
     */
    private static void renderTile(GridPane mapGrid, GameEngine game, TileFactory tileFactory,
                                   Map<Position, Hint> hintMap, int row, int col) {
        Position pos = new Position(row, col);
        Hint hint = hintMap.get(pos);
        boolean isHintPosition = hint != null && !pos.equals(game.getPlayer().getPosition());
        StackPane tile = createTile(game, tileFactory, pos, isHintPosition);
        if(isHintPosition) installTooltip(tile, hint.getMessage());
        mapGrid.add(tile, col, row);
    }

    /**
     * Crea la rappresentazione grafica di una singola cella.
     *
     * Combina il tile di base con gli eventuali overlay
     * presenti nella posizione specificata.
     *
     * @param game partita corrente
     * @param tileFactory factory utilizzata per la creazione dei tile
     * @param pos posizione da rappresentare
     * @param isHintPosition true se la posizione contiene un indizio
     * @return nodo grafico della cella
     */
    private static StackPane createTile(GameEngine game, TileFactory tileFactory, Position pos, boolean isHintPosition) {
        BaseTileType base = getBaseTileType(game.getMap(), pos);
        List<SpriteProvider> overlays = getOverlaysFor(game, pos);
        if (isHintPosition) overlays.add(FixedSprite.HINT);
        return tileFactory.createTile(base, overlays);
    }

    /**
     * Determina il tipo di tile base da visualizzare
     * per una determinata posizione.
     *
     * @param map mappa del dungeon
     * @param pos posizione da analizzare
     * @return WALL, UNKNOWN oppure FLOOR
     */
    private static BaseTileType getBaseTileType(DungeonMap map, Position pos) {
        if (!map.isInside(pos)) return BaseTileType.WALL;
        if (!map.isVisited(pos)) return BaseTileType.UNKNOWN;
        return BaseTileType.FLOOR;
    }

    /**
     * Restituisce gli overlay da visualizzare in una posizione.
     *
     * Gli overlay possono includere il giocatore, nemici,
     * tesori, trappole, uscita e altri elementi presenti
     * nella stanza.
     *
     * @param game partita corrente
     * @param pos posizione da analizzare
     * @return lista degli overlay da visualizzare
     */
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

    /**
     * Configura righe e colonne della griglia utilizzata
     * per il rendering della mappa.
     *
     * @param mapGrid griglia da configurare
     * @param size numero di righe e colonne
     * @param tileSize dimensione delle celle
     */
    private static void setupGrid(GridPane mapGrid, int size, int tileSize) {
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
        for (int i = 0; i < size; i++) {
            mapGrid.getColumnConstraints().add(createColumn(tileSize));
            mapGrid.getRowConstraints().add(createRow(tileSize));
        }
    }

    /**
     * Crea un vincolo di colonna a dimensione fissa.
     *
     * @param size larghezza della colonna
     * @return vincolo configurato
     */
    private static ColumnConstraints createColumn(int size){
        ColumnConstraints col = new ColumnConstraints(size);
        col.setMinWidth(size);
        col.setPrefWidth(size);
        col.setMaxWidth(size);
        return col;
    }

    /**
     * Crea un vincolo di riga a dimensione fissa.
     *
     * @param size altezza della riga
     * @return vincolo configurato
     */
    private static RowConstraints createRow(int size){
        RowConstraints row = new RowConstraints(size);
        row.setMinHeight(size);
        row.setPrefHeight(size);
        row.setMaxHeight(size);
        return row;
    }

    /**
     * Associa un tooltip a una cella della mappa.
     *
     * @param tile nodo grafico destinatario
     * @param message testo del tooltip
     */
    private static void installTooltip(StackPane tile, String message){
        Tooltip tooltip = new Tooltip(message);
        tooltip.getStyleClass().add("custom-tooltip");
        Tooltip.install(tile, tooltip);
        tooltip.setShowDelay(TOOLTIP_DELAY);
    }
}
