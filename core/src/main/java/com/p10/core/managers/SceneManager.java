package com.p10.core.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * SceneManager - Manages game scenes (menu, game, etc.)
 * STUB VERSION - Will be replaced by Person 4
 */
public class SceneManager {

    private String currentSceneName = "none";

    public SceneManager() {
        System.out.println("[SceneManager] Stub initialized");
    }

    public void update(float deltaTime) {
        // Empty - Person 4 implements
    }

    public void renderShapes(ShapeRenderer renderer) {
        // Empty - Person 4 implements
    }

    public void renderTextures(SpriteBatch batch) {
        // Empty - Person 4 implements
    }

    public String getCurrentSceneName() {
        return currentSceneName;
    }

    public void dispose() {
        System.out.println("[SceneManager] Stub disposed");
    }
}