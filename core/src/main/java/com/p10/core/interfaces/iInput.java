package com.p10.core.interfaces;

import com.badlogic.gdx.math.Vector2;

/**
 * iInput Interface
 * 
 * Contract for input and audio operations.
 * Implemented by: InputOutputManager
 * Used by: Scene (to check input/play sounds without knowing about
 * InputOutputManager directly)
 */
public interface iInput {

    /**
     * Check if a specific key is currently pressed
     * 
     * @param keyCode The LibGDX key code (e.g., Input.Keys.SPACE)
     * @return true if the key is pressed, false otherwise
     */
    boolean isKeyPressed(int keyCode);

    /**
     * Check if a specific key was just pressed this frame
     * 
     * @param keyCode The LibGDX key code
     * @return true if the key was just pressed, false otherwise
     */
    boolean isKeyJustPressed(int keyCode);

    /**
     * Get the current mouse/touch position
     * 
     * @return Vector2 containing x and y coordinates
     */
    Vector2 getMousePosition();

    /**
     * Check if mouse button is pressed
     * 
     * @param button The button code (0 = left, 1 = right, 2 = middle)
     * @return true if pressed, false otherwise
     */
    boolean isMouseButtonPressed(int button);

    /**
     * Play a sound effect
     * 
     * @param soundName The name/identifier of the sound to play
     */

    // void playSound(String soundName);

    /**
     * Play background music
     * 
     * @param musicName The name/identifier of the music to play
     */
    // void playMusic(String musicName);

    /**
     * Stop all currently playing music
     */
    // void stopMusic();
}