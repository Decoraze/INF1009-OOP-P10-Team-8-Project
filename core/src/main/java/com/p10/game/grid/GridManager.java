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
 * Y-axis is flipped so row 0 of layout renders at TOP of grid area.
 * Grid is offset to sit between HUD bar (top) and shop bar (bottom).
 */
public class GridManager {
	private Tile[][] tiles;
	private int tileSize;
	private int gridWidth; // columns
	private int gridHeight; // rows
	private int[][] layout;

	// Pixel offset so grid sits between HUD and shop bars
	private float offsetX;
	private float offsetY;

	/**
	 * @param layout   2D int array defining the grid (0=buildable, 1=path,
	 *                 2=blocked)
	 * @param tileSize Pixel size of each tile (e.g. 48)
	 */
	public GridManager(int[][] layout, int tileSize) {
		this.gridHeight = layout.length;
		this.gridWidth = layout[0].length;
		this.layout = layout;
		this.tileSize = tileSize;
		this.tiles = new Tile[gridHeight][gridWidth];

		// Center grid horizontally, offset vertically above shop bar (90px from bottom)
		this.offsetX = (800 - gridWidth * tileSize) / 2f;
		this.offsetY = 90;

		for (int row = 0; row < gridHeight; row++) {
			for (int col = 0; col < gridWidth; col++) {
				TileType type = layout[row][col] == 1 ? TileType.PATH
						: layout[row][col] == 2 ? TileType.BLOCKED : TileType.BUILDABLE;
				tiles[row][col] = new Tile(row, col, type);
			}
		}
	}

	/**
	 * Convert pixel screen coords to grid [row, col].
	 * Accounts for offset and Y-flip.
	 */
	public int[] pixelToGrid(float px, float py) {
		// Undo offset first
		float localX = px - offsetX;
		float localY = py - offsetY;
		// Y-flip: top of grid = row 0, bottom of grid = last row
		int col = (int) (localX / tileSize);
		int row = gridHeight - 1 - (int) (localY / tileSize);
		// Clamp to bounds
		row = Math.max(0, Math.min(gridHeight - 1, row));
		col = Math.max(0, Math.min(gridWidth - 1, col));
		return new int[] { row, col };
	}

	/**
	 * Convert grid [row, col] to pixel position (top-left corner of tile).
	 * Row 0 renders at TOP of grid area, row 7 at bottom.
	 */
	public Vector2 gridToPixel(int row, int col) {
		// Y-flip: row 0 at top → higher pixel Y, row 7 at bottom → lower pixel Y
		float px = offsetX + col * tileSize;
		float py = offsetY + (gridHeight - 1 - row) * tileSize;
		return new Vector2(px, py);
	}

	public boolean isBuildable(int row, int col) {
		if (row < 0 || row >= gridHeight || col < 0 || col >= gridWidth)
			return false;
		return tiles[row][col].getType() == TileType.BUILDABLE;
	}

	public void placeTower(int row, int col, Tower tower) {
		tiles[row][col].setType(TileType.OCCUPIED);
		tiles[row][col].setTowerRef(tower);
	}

	/**
	 * Build PathDefinition by following connected PATH tiles.
	 * Starts from leftmost path tile, follows neighbors in order.
	 * Uses pixel positions from gridToPixel (which handles Y-flip + offset).
	 */
	public PathDefinition buildPath() {
		PathDefinition path = new PathDefinition();
		boolean[][] visited = new boolean[gridHeight][gridWidth];

		// Find starting path tile (scan left-to-right, top-to-bottom in layout)
		int startRow = -1, startCol = -1;
		outer: for (int c = 0; c < gridWidth; c++) {
			for (int r = 0; r < gridHeight; r++) {
				if (layout[r][c] == 1) {
					startRow = r;
					startCol = c;
					break outer;
				}
			}
		}
		if (startRow == -1)
			return path; // no path tiles

		// Follow connected path tiles using neighbor-walk
		int cr = startRow, cc = startCol;
		while (cr >= 0 && cr < gridHeight && cc >= 0 && cc < gridWidth
				&& layout[cr][cc] == 1 && !visited[cr][cc]) {
			visited[cr][cc] = true;
			Vector2 wp = gridToPixel(cr, cc);
			// Add waypoint at CENTER of tile for smoother enemy movement
			path.addWaypoint(wp.x + tileSize / 2f, wp.y + tileSize / 2f);

			// Check 4 neighbors: right, down, left, up
			int[][] dirs = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
			boolean moved = false;
			for (int[] d : dirs) {
				int nr = cr + d[0], nc = cc + d[1];
				if (nr >= 0 && nr < gridHeight && nc >= 0 && nc < gridWidth
						&& layout[nr][nc] == 1 && !visited[nr][nc]) {
					cr = nr;
					cc = nc;
					moved = true;
					break;
				}
			}
			if (!moved)
				break;
		}
		return path;
	}

	public void renderGrid(ShapeRenderer renderer) {
		// Draw each tile with Y-flip: row 0 at top, row 7 at bottom
		for (int row = 0; row < gridHeight; row++) {
			for (int col = 0; col < gridWidth; col++) {
				Tile.TileType type = tiles[row][col].getType();
				switch (type) {
					case PATH:
						renderer.setColor(Color.TAN);
						break;
					case BUILDABLE:
						renderer.setColor(0.2f, 0.55f, 0.2f, 1f); // darker green, easier on the eyes
						break;
					case BLOCKED:
						renderer.setColor(Color.DARK_GRAY);
						break;
					case OCCUPIED:
						renderer.setColor(0.2f, 0.3f, 0.6f, 1f); // muted blue
						break;
				}
				// Y-flip: row 0 at top of grid area
				float px = offsetX + col * tileSize;
				float py = offsetY + (gridHeight - 1 - row) * tileSize;
				renderer.rect(px, py, tileSize, tileSize);
			}
		}

		// Grid lines for clarity
		renderer.setColor(0.15f, 0.15f, 0.15f, 1f);
		for (int col = 0; col <= gridWidth; col++) {
			float x = offsetX + col * tileSize;
			renderer.rectLine(x, offsetY, x, offsetY + gridHeight * tileSize, 1);
		}
		for (int row = 0; row <= gridHeight; row++) {
			float y = offsetY + row * tileSize;
			renderer.rectLine(offsetX, y, offsetX + gridWidth * tileSize, y, 1);
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

	public float getOffsetX() {
		return offsetX;
	}

	public float getOffsetY() {
		return offsetY;
	}

	public Tile getTile(int row, int col) {
		return tiles[row][col];
	}
}
