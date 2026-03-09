package com.p10.game.grid;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.p10.game.ai.PathDefinition;
import com.p10.game.entities.Tower;

/**
 * GridManager handles the tile-based game grid.
 * Converts between pixel coordinates and grid positions,
 * tracks which tiles are buildable/path/occupied,
 * and builds the enemy PathDefinition from the layout.
 *
 * Grid layout values: 0=BUILDABLE, 1=PATH, 2=BLOCKED
 *
 */
public class GridManager {
    private Tile[][] tiles;
    private int tileSize;
    private int gridWidth;
    private int gridHeight;
    private int[][] layout;

    /**
     * @param layout   2D int array defining the grid (0=buildable, 1=path,
     *                 2=blocked)
     * @param tileSize Pixel size of each tile (e.g. 64)
     */
    public GridManager(int[][] layout, int tileSize) {
        // : Store layout, tileSize, calculate gridWidth/gridHeight
        // : Initialize tiles[][] array
        // : For each cell, create a Tile with the correct TileType based on layout
        // value
    }

    public int[] pixelToGrid(float px, float py) {
        // : Convert pixel coordinates to grid [row, col], clamped to bounds
        return new int[] { 0, 0 };
    }

    public Vector2 gridToPixel(int row, int col) {
        // : Convert grid position to pixel center of that tile
        return new Vector2(0, 0);
    }

    public boolean isBuildable(int row, int col) {
        // : Return true if tile at [row, col] is BUILDABLE and within bounds
        return false;
    }

    public void placeTower(int row, int col, Tower tower) {
        // : Set tile type to OCCUPIED, store tower reference
    }

    /**
     * Build PathDefinition by scanning layout for PATH tiles (value=1).
     * Collects path tiles and sorts them left-to-right to create ordered waypoints.
     */
    public PathDefinition buildPath() {
        // : Find all PATH tiles, sort by column then row
        // : Convert each to pixel center and add as waypoint
        return new PathDefinition();
    }

    public void renderGrid(ShapeRenderer renderer) {
        // : Draw each tile as a colored rectangle based on its type
        // PATH=brown, BUILDABLE=green, BLOCKED=dark grey, OCCUPIED=dark blue
    }

    // --- Getters ---
    public int getTileSize() {
        return tileSize;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }
}
