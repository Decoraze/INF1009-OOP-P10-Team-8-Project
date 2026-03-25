package com.p10.game.grid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.p10.core.interfaces.iEntityOps;
import com.p10.core.interfaces.iInput;
import com.p10.game.entities.Tower;
import com.p10.game.grid.Tile.TileType;
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
    private boolean mouseActuatedLastFrame = false; // Check if mouse is being held down
    // Drag-and-drop fields
    private boolean isDragging = false;
    private float dragX, dragY;
    private String dragTowerType = null;

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
        if (selectedTowerType == null)
            return false;
        // : Return false if not in prep phase or no tower type selected or mouse
        // not clicked
        // : Get mouse position, invert Y (worldY = screenHeight - mouseY)
        // : Convert pixel to grid position
        // : Check if tile is buildable and player can afford the tower
        // : Deduct cost via state.purchaseTower()
        // : Create new Tower entity at grid pixel center
        // : Add tower to entityOps and mark tile as occupied in grid
        // : If player can no longer afford this tower type, deselect it

        if (input.isMouseButtonPressed(0) && !mouseActuatedLastFrame) // On mouse left click
        {
            Vector2 mousePos = input.getMousePosition();
            float worldY = com.badlogic.gdx.Gdx.graphics.getHeight() - mousePos.y; // use actual screen height for
                                                                                   // resize support // ← Y INVERSION
            int[] gridPos = grid.pixelToGrid(mousePos.x, worldY); // Convert mousePos to selected grid
            if (grid.isBuildable(gridPos[0], gridPos[1]) && state.canAfford(selectedTowerType)) // Check if valid build
            {
                state.purchaseTower(selectedTowerType);
                Vector2 towerPos = grid.gridToPixel(gridPos[0], gridPos[1]); // Get position of middle of selected grid
                // Create tower at tile corner position, size = one tile
                Tower newTower = new Tower("Tower" + towerCount++, towerPos.x, towerPos.y,
                        grid.getTileSize(), grid.getTileSize(), selectedTowerType);
                entityOps.addEntity(newTower); // Add newly created entity to entity list
                grid.placeTower(gridPos[0], gridPos[1], newTower); // Place newTower at y and x
                if (!state.canAfford(selectedTowerType)) {
                    selectedTowerType = null;
                }
            }

        }
        mouseActuatedLastFrame = input.isMouseButtonPressed(0);
        return false;
    }

    public void setSelectedTowerType(String type) {
        this.selectedTowerType = type;
    }

    public String getSelectedTowerType() {
        return selectedTowerType;
    }

    // Render range circle at mouse hover when placing tower
    // 1. If no tower selected (and not dragging), return
    // 2. Convert mouseX/mouseY to grid pos via grid.pixelToGrid()
    // 3. Check isBuildable — if not, return
    // 4. Get tile center via grid.gridToPixel() + tileSize/2
    // 5. Get range for tower type (FIREWALL=140, ANTIVIRUS=120, ENCRYPTION=130,
    // IDS=160)
    // 6. Use GL_BLEND to draw translucent filled circle + line border
    // 7. Also draw yellow rect outline on hovered tile
    /*
     * public void renderHoverRange(ShapeRenderer renderer, GridManager grid,
     * float mouseX, float mouseY) {
     * int[] gPos = grid.pixelToGrid(mouseX, mouseY);
     * if (dragTowerType == null && !isDragging)
     * {
     * return;
     * }
     * 
     * if(!grid.isBuildable(gPos[0], gPos[1]))
     * {
     * return;
     * }
     * 
     * Vector2 pPos = grid.gridToPixel(gPos[0], gPos[1]);
     * float centerX = pPos.x + (grid.getTileSize() / 2);
     * float centerY = pPos.y + (grid.getTileSize() / 2);
     * // Create temp tower instance to show range before placement and creation of
     * actual tower instance
     * Tower tmp = new Tower("tmp", centerX, centerY, grid.getTileSize(),
     * grid.getTileSize(), getSelectedTowerType());
     * tmp.showTowerRange(renderer, centerX, centerY, tmp.getTowerColor());
     * }
     */
    // using the new dragTowerType to show range when dragging, and
    // selectedTowerType when just hovering without dragging in tower.java
    public void renderHoverRange(ShapeRenderer renderer, GridManager grid,
            float mouseX, float mouseY) {
        String activeType = dragTowerType != null ? dragTowerType : selectedTowerType;
        if (activeType == null && !isDragging) {
            return;
        }

        int[] gPos = grid.pixelToGrid(mouseX, mouseY);// this is put here rather than above activeType check because we
                                                      // want to return early if the tile is not buildable, even if the
                                                      // player is dragging a tower
        if (!grid.isBuildable(gPos[0], gPos[1])) {
            return;
        }

        Vector2 pPos = grid.gridToPixel(gPos[0], gPos[1]);
        float centerX = pPos.x + (grid.getTileSize() / 2f);
        float centerY = pPos.y + (grid.getTileSize() / 2f);
        // used new getters in Tower.java to get range and color based on activeType
        // (dragTowerType if dragging, otherwise selectedTowerType)
        float range = Tower.getRangeForType(activeType);
        Color color = Tower.getColorForType(activeType);
        Tower.drawRangeCircle(renderer, centerX, centerY, range, color);
    }

    // Drag-and-drop tower placement
    // 1. If mouseDown and not dragging: check if click is on a tower card in bottom
    // HUD
    // Card layout: startX=20, startY=10, cardW=80, cardH=65, gap=8,
    // towers=FIREWALL/ANTIVIRUS/ENCRYPTION/IDS
    // If clicked and canAfford → set isDragging=true, dragTowerType=clicked
    // 2. If isDragging: update dragX/dragY to mouse position
    // 3. If mouse released while dragging: try place tower at grid pos (same logic
    // as handleInput)
    // Then reset isDragging=false, dragTowerType=null
    public void handleDrag(float mouseX, float mouseY, boolean mouseDown,
            GridManager grid, GameState state, iEntityOps entityOps) {
        if (mouseDown && !isDragging) {
            selectTowerFromHUD(mouseX, mouseY, state);
        }
        // Scenario B: Player is actively doing something with a tower
        else if (isDragging) {
            if (mouseDown) {
                // Still holding the mouse, update the ghost image position
                dragX = mouseX;
                dragY = mouseY;
            } else {
                // Mouse released! Try to place it and clean up.
                placeTower(dragX, dragY, grid, state, entityOps);
                resetDragState();
            }
        }
    }

    public void selectTowerFromHUD(float mouseX, float mouseY, GameState state) {
        float startX = 20f;
        float startY = 10f;
        float cardW = 100f;
        float cardH = 65f;
        float gap = 8f;

        for (int i = 0; i < GameState.ALL_TOWER_TYPES.length; i++) {
            float cx = startX + i * (cardW + gap);

            // Check if card clickable
            if (mouseX >= cx && mouseX <= cx + cardW && mouseY >= startY && mouseY <= startY + cardH) {

                // Check if enough currency
                if (state.canAfford(GameState.ALL_TOWER_TYPES[i])) {
                    isDragging = true;
                    dragTowerType = GameState.ALL_TOWER_TYPES[i];
                    dragX = mouseX;
                    dragY = mouseY;
                }
                return;
            }
        }
    }

    public void placeTower(float mouseX, float mouseY, GridManager grid, GameState state, iEntityOps entityOps) {
        int[] gridPos = grid.pixelToGrid(mouseX, mouseY);
        int row = gridPos[0];
        int col = gridPos[1];

        if (grid.isBuildable(row, col)) {
            // Buy tower
            state.purchaseTower(dragTowerType);

            // Find center
            com.badlogic.gdx.math.Vector2 towerPos = grid.gridToPixel(row, col);

            // Create and register tower
            Tower newTower = new Tower("Tower" + towerCount++, towerPos.x, towerPos.y,
                    grid.getTileSize(), grid.getTileSize(), dragTowerType);

            entityOps.addEntity(newTower);
            grid.placeTower(row, col, newTower);
        }
    }

    public void resetDragState() {
        isDragging = false;
        dragTowerType = null;
    }

    // If not dragging, return. Otherwise draw rect at dragX/dragY with 0.4f alpha
    // using GL_BLEND
    public void renderDragGhost(ShapeRenderer renderer) {
        if (!isDragging || dragTowerType == null)
            return;

        Color c = Tower.getColorForType(dragTowerType);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        renderer.setColor(c.r, c.g, c.b, 0.4f);
        renderer.rect(dragX - 24f, dragY - 24f, 48f, 48f);
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    // 1. Convert mouseX/mouseY to grid pos
    // 2. Check bounds, check tile is OCCUPIED
    // 3. Get tower ref from tile
    // 4. Refund 50% of tower cost via GameState.getPrice() / 2 →
    // state.addCurrency()
    // 5. Remove tower entity via entityOps.removeEntity(tower.getEntityId())
    // 6. Reset tile to BUILDABLE, clear towerRef
    public boolean handleSell(float mouseX, float mouseY, GridManager grid,
            GameState state, iEntityOps entityOps) {
        int[] gPos = grid.pixelToGrid(mouseX, mouseY);
        Tile target = grid.getTile(gPos[0], gPos[1]);
        // Check if tower on tile
        if (target.getType() == TileType.OCCUPIED) {
            // Refund 1/2 currency, Remove entity, set Tile back to buildable, clear
            // towerRef on tile
            state.addCurrency(GameState.getPrice(target.getTowerRef().getTowerType()) / 2);
            entityOps.removeEntity(target.getTowerRef().getId());
            target.setType(TileType.BUILDABLE);
            target.setTowerRef(null);
            return true;
        }
        return false;
    }

    public boolean isDragging() {
        return isDragging;
    }
}
