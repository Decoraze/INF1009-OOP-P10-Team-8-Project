package com.p10.game.grid;

import com.p10.core.interfaces.iEntityOps;
import com.p10.core.interfaces.iInput;
import com.p10.game.wave.GameState;

/**
 * TowerPlacer handles mouse-click tower placement during prep phase.
 * Converts screen click to grid position, validates placement, creates Tower
 * entity.
 *
 * 
 */
public class TowerPlacer {
    private String selectedTowerType;
    private int towerCount = 0;

    public TowerPlacer() {
        this.selectedTowerType = null;
    }

    /**
     * Handle a mouse click to place a tower.
     * 
     * @param input        Engine input interface
     * @param grid         GridManager for coordinate conversion and validation
     * @param state        GameState for currency checks and purchases
     * @param entityOps    Engine interface to add new Tower entity
     * @param screenHeight Screen height for Y-coordinate inversion
     * @return true if a tower was successfully placed
     */
    public boolean handleInput(iInput input, GridManager grid, GameState state, iEntityOps entityOps,
            float screenHeight) {
        // : Return false if not in prep phase or no tower type selected or mouse
        // not clicked
        // : Get mouse position, invert Y (worldY = screenHeight - mouseY)
        // : Convert pixel to grid position
        // : Check if tile is buildable and player can afford the tower
        // : Deduct cost via state.purchaseTower()
        // : Create new Tower entity at grid pixel center
        // : Add tower to entityOps and mark tile as occupied in grid
        // : If player can no longer afford this tower type, deselect it
        return false;
    }

    public void setSelectedTowerType(String type) {
        this.selectedTowerType = type;
    }

    public String getSelectedTowerType() {
        return selectedTowerType;
    }
}
