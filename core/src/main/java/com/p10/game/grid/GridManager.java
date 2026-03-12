package com.p10.game.grid;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.p10.game.ai.PathDefinition;
import com.p10.game.entities.Tower;
import com.p10.game.grid.Tile.TileType;

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
    	this.layout = new int[gridHeight][gridWidth];
    	this.tileSize = tileSize;
    	this.tiles = new Tile[gridHeight][gridWidth];
    }

    public int[] pixelToGrid(float px, float py) {
        // : Convert pixel coordinates to grid [row, col], clamped to bounds
    	// pixel / tileSize brings you to which grid the cursor is hovering over
        return new int[] {(int) (py / tileSize), (int) (px / tileSize)};
    }

    public Vector2 gridToPixel(int row, int col) {
        // : Convert grid position to pixel center of that tile
    	// row * tileSize brings you to the pixel the tile starts on
    	// tileSize / 2 is the middle of the tile
        return new Vector2((float) (col * tileSize + (tileSize / 2f)), (float) (row * tileSize + (tileSize / 2f)));
    }

    public boolean isBuildable(int row, int col) {
        // : Return true if tile at [row, col] is BUILDABLE and within bounds
    	if ((row >= 0 && row <= gridHeight) && (col >= 0 && col <= gridWidth)) // OOB check
    		return false;
        return tiles[row][col].getType() == TileType.BUILDABLE; // returns accordingly if BUILDABLE
    }

    public void placeTower(int row, int col, Tower tower) {
        // : Set tile type to OCCUPIED, store tower reference
    	tiles[row][col].setType(TileType.OCCUPIED);
    	tiles[row][col].setTowerRef(tower);
    }

    /**
     * Build PathDefinition by scanning layout for PATH tiles (value=1).
     * Collects path tiles and sorts them left-to-right to create ordered waypoints.
     */
    public PathDefinition buildPath() {
        // : Find all PATH tiles, sort by column then row
        // : Convert each to pixel center and add as waypoint
    	PathDefinition path = new PathDefinition();
    	for (int col = 0; col < layout.length; col++)
    	{
    		for (int row = 0; row < layout[col].length; row++) 
    		{
    			if (layout[col][row] == 1)
    			{
	    			Vector2 waypoint = gridToPixel(col, row);
    				path.addWaypoint(waypoint.x, waypoint.y);	// add to waypoint ArrayList
    			}
    		}
    	}
        return path;
    }

    public void renderGrid(ShapeRenderer renderer) {
        // : Draw each tile as a colored rectangle based on its type
        // PATH=brown, BUILDABLE=green, BLOCKED=dark grey, OCCUPIED=dark blue
    	
    	// Draw solid shapes first
    	renderer.set(ShapeRenderer.ShapeType.Filled);
    	// Loops through the entire grid
    	for (int col = 0; col < gridWidth; col ++)
    	{
    		for (int row = 0; row < gridHeight; row++) 
    		{
    			Tile.TileType type = tiles[row][col].getType();
    			// Switch case assigns colors according to tile type
    			switch (type) 
    			{
    			case PATH:
    				renderer.setColor(Color.TAN);
    				break;
    			case BUILDABLE:
    				renderer.setColor(Color.GREEN);
    				break;
    			case BLOCKED:
    				renderer.setColor(Color.DARK_GRAY);
    				break;
    			case OCCUPIED:
    				renderer.setColor(Color.ROYAL);
    				break;
    			}
    			renderer.rect(col * tileSize, row * tileSize, tileSize, tileSize);
    		}
    	}
    	
    	// Change to Line to show outline of each single tile
    	renderer.set(ShapeRenderer.ShapeType.Line);
    	renderer.setColor(Color.BLACK);
    	
    	// Vertical line for grid
    	for (int col = 0; col <= gridWidth; col++) 
    	{
    		renderer.line(col * tileSize, 0, col * tileSize, gridHeight * tileSize);
    	}
    	
    	// Horizontal line for grid
    	for (int row = 0; row <= gridHeight; row++)
    	{
    		renderer.line(0, row * tileSize, gridWidth * tileSize, row * tileSize);
    	}
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