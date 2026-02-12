package com.p10.core.managers;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.p10.core.interfaces.iSceneControl;
import com.p10.core.scene.Scene;


public class SceneManager implements iSceneControl {

    private final Map<String, Scene> scenes = new HashMap<>();
    private String currentSceneName = "none";
    private Scene currentScene = null;

    public SceneManager() {
        System.out.println("[SceneManager] Initialized");
    }

    // Register a scene
    public void registerScene(String name, Scene scene) {
        if (name == null || scene == null) return;
        scenes.put(name, scene);
        System.out.println("[SceneManager] Registered scene: " + name);
    }

    @Override
    public void switchScene(String sceneName) {
        if (sceneName == null) return;

        Scene next = scenes.get(sceneName);
        if (next == null) {
            System.out.println("[SceneManager] switchScene failed: scene not found -> " + sceneName);
            return;
        }

        // Unload current
        if (currentScene != null) {
            currentScene.unload();
        }

        // Switch
        currentScene = next;
        currentSceneName = sceneName;

        // Load next
        currentScene.load();

        System.out.println("[SceneManager] Switched to: " + currentSceneName);
    }

    public void update(float deltaTime) {
        if (currentScene != null) {
            currentScene.update(deltaTime);
        }
    }

    public void renderShapes(ShapeRenderer renderer) {
        if (currentScene != null) {
            currentScene.renderShapes(renderer);
        }
    }

    public void renderTextures(SpriteBatch batch) {
        if (currentScene != null) {
            currentScene.renderTextures(batch);
        }
    }

    @Override
    public Scene getCurrentScene() {
        return currentScene;
    }

    @Override
    public String getCurrentSceneName() {
        return currentSceneName;
    }

    public void dispose() {
        System.out.println("[SceneManager] Disposing...");
        for (Scene s : scenes.values()) {
            if (s != null) s.dispose();
        }
        scenes.clear();
        currentScene = null;
        currentSceneName = "none";
    }

    public void onResize(int width, int height) {
        System.out.println("[SceneManager] Handling resize: " + width + "x" + height);
    }
}
