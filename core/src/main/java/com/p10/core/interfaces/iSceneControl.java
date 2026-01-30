package com.p10.core.interfaces;

/**
 * iSceneControl Interface
 * 
 * Contract for scene management operations.
 * Implemented by: SceneManager
 * Used by: Scene (to switch scenes without knowing about SceneManager directly)
 */
public interface iSceneControl {

    /**
     * Switch to a different scene by name
     * 
     * @param sceneName The name of the scene to switch to
     */
    void switchScene(String sceneName);

    /**
     * Get the current active scene
     * 
     * @return The current scene object
     */
    Object getCurrentScene(); // TODO: Replace Object with Scene when Hui Yang creates it

    /**
     * Get the name of the current scene
     * 
     * @return The name of the current scene
     */
    String getCurrentSceneName();
}