package com.p10.core.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * SceneManager - Manages game scenes (menu, game, etc.)
 * 
 */
public class SceneManager {

    private String currentSceneName = "none";

    public SceneManager() {
        System.out.println("[SceneManager] Stub initialized");
    }

    public void update(float deltaTime) {
        // Empty - implements ----
    }

    public void renderShapes(ShapeRenderer renderer) {
        // Empty - implements -----
    }

    public void renderTextures(SpriteBatch batch) {
        // Empty - implements -0----
    }

    public String getCurrentSceneName() {
        return currentSceneName;
    }

    public void dispose() {
        System.out.println("[SceneManager] Stub disposed");
    }

    public void onResize(int width, int height) {
        System.out.println("[SceneManager] Handling resize: " + width + "x" + height);
        // update scene of new dimensions if user scales up or down
    }
}