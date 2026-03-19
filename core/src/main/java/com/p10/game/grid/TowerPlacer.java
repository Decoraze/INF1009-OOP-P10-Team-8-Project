package com.p10.game.grid;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.p10.core.interfaces.iEntityOps;
import com.p10.core.interfaces.iInput;
import com.p10.game.entities.Tower;
import com.p10.game.grid.Tile.TileType;
import com.p10.game.wave.GameState;

/*
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer; uncomment this once implemeted otherwise it will disappear zz*/
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
    // TODO @ChayHan: Drag-and-drop fields
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
                // @Aurelius idk how the naming is but i just concatenate Tower and count
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

    // TODO @JunMing: Render range circle at mouse hover when placing tower
    // 1. If no tower selected (and not dragging), return
    // 2. Convert mouseX/mouseY to grid pos via grid.pixelToGrid()
    // 3. Check isBuildable — if not, return
    // 4. Get tile center via grid.gridToPixel() + tileSize/2
    // 5. Get range for tower type (FIREWALL=140, ANTIVIRUS=120, ENCRYPTION=130,
    // IDS=160)
    // 6. Use GL_BLEND to draw translucent filled circle + line border
    // 7. Also draw yellow rect outline on hovered tile
    public void renderHoverRange(ShapeRenderer renderer, GridManager grid,
            float mouseX, float mouseY) {
        // TODO @JunMing
    	int[] gPos = grid.pixelToGrid(mouseX, mouseY);
    	if (dragTowerType == null && !isDragging)
    	{
    		return;
    	}

    	if(!grid.isBuildable(gPos[0], gPos[1]))
    	{
    		return;
    	}

    	Vector2 pPos = grid.gridToPixel(gPos[0], gPos[1]);
    	float centerX = pPos.x + (grid.getTileSize() / 2);
    	float centerY = pPos.y + (grid.getTileSize() / 2);
    	// Create temp tower instance to show range before placement and creation of actual tower instance
    	Tower tmp = new Tower("tmp", centerX, centerY, grid.getTileSize(), grid.getTileSize(), getSelectedTowerType());
    	tmp.showTowerRange(renderer, centerX, centerY, tmp.getTowerColor());
    }

    // TODO @ChayHan: Drag-and-drop tower placement
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
            // Check if click is on a tower card in bottom HUD
            String[] towers = {"FIREWALL", "ANTIVIRUS", "ENCRYPTION", "IDS"};
            float startX = 20f;
            float startY = 10f;
            float cardW = 80f;
            float cardH = 65f;
            float gap = 8f;

            for (int i = 0; i < towers.length; i++) {
                float cx = startX + i * (cardW + gap);
                // Check if mouse is within card boundaries
                if (mouseX >= cx && mouseX <= cx + cardW && mouseY >= startY && mouseY <= startY + cardH) {
                    if (state.canAfford(towers[i])) {
                        isDragging = true;
                        dragTowerType = towers[i];
                        dragX = mouseX;
                        dragY = mouseY;
                    }
                    break;
                }
            }
        } else if (isDragging) {
            if (mouseDown) {
                // Follow mouse while dragging
                dragX = mouseX;
                dragY = mouseY;
            } else {
                // Mouse released -> Try to place tower
                int[] gridPos = grid.pixelToGrid(dragX, dragY);
                if (grid.isBuildable(gridPos[0], gridPos[1])) {
                    state.purchaseTower(dragTowerType);
                    Vector2 towerPos = grid.gridToPixel(gridPos[0], gridPos[1]);

                    Tower newTower = new Tower("Tower" + towerCount++, towerPos.x, towerPos.y,
                        grid.getTileSize(), grid.getTileSize(), dragTowerType);
                    entityOps.addEntity(newTower);
                    grid.placeTower(gridPos[0], gridPos[1], newTower);
                }

                // Reset dragging state
                isDragging = false;
                dragTowerType = null;
            }
        }
    }

    // TODO @ChayHan: Render semi-transparent ghost tower at mouse during drag
    // If not dragging, return. Otherwise draw rect at dragX/dragY with 0.4f alpha
    // using GL_BLEND
    public void renderDragGhost(ShapeRenderer renderer) {
        if (!isDragging || dragTowerType == null) return;

        // Enable blending for transparency
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        // Map type to color
        Color c = Color.WHITE;
        switch(dragTowerType.toUpperCase()) {
            case "FIREWALL": c = Color.ORANGE; break;
            case "ANTIVIRUS": c = Color.GREEN; break;
            case "ENCRYPTION": c = Color.CYAN; break;
            case "IDS": c = Color.PURPLE; break;
        }

        renderer.setColor(c.r, c.g, c.b, 0.4f); // 0.4f alpha
        // Draw centered on mouse (Assuming 48x48 tile size from grid initialization)
        renderer.rect(dragX - 24f, dragY - 24f, 48f, 48f);

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }




    // TODO @JunMing: Sell tower on right-click
    // 1. Convert mouseX/mouseY to grid pos
    // 2. Check bounds, check tile is OCCUPIED
    // 3. Get tower ref from tile
    // 4. Refund 50% of tower cost via GameState.getPrice() / 2 →
    // state.addCurrency()
    // 5. Remove tower entity via entityOps.removeEntity(tower.getEntityId())
    // 6. Reset tile to BUILDABLE, clear towerRef
    public boolean handleSell(float mouseX, float mouseY, GridManager grid,
            GameState state, iEntityOps entityOps) {
        // TODO @JunMing
    	int[] gPos = grid.pixelToGrid(mouseX, mouseY);
    	Tile target = grid.getTile(gPos[0], gPos[1]);
    	// Check if tower on tile
    	if (target.getType() == TileType.OCCUPIED)
    	{
    		// Refund 1/2 currency, Remove entity, set Tile back to buildable, clear towerRef on tile
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
