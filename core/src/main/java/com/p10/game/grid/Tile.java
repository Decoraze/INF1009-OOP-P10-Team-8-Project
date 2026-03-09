package com.p10.game.grid;

import com.p10.game.entities.Tower;

/**
 * Tile represents a single cell in the game grid.
 * Tracks its type (PATH, BUILDABLE, BLOCKED, OCCUPIED) and
 * optionally holds a reference to a placed Tower.
 * 
 */
public class Tile {
    public enum TileType {
        PATH, BUILDABLE, BLOCKED, OCCUPIED
    }

    private int row;
    private int col;
    private TileType type;
    private Tower towerRef;

    public Tile(int row, int col, TileType type) {
        // : Initialize fields, towerRef = null
    }

    // --- Getters/Setters ---
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public Tower getTowerRef() {
        return towerRef;
    }

    public void setTowerRef(Tower t) {
        this.towerRef = t;
    }
}
