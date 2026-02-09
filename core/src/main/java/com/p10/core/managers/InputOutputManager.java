package com.p10.core.managers;

import com.badlogic.gdx.math.Vector2;
import com.p10.core.interfaces.iInput;
import com.p10.core.managers.IO.AudioOutput;
import com.p10.core.managers.IO.KeyboardInput;
import com.p10.core.managers.IO.MouseInput;

public class InputOutputManager implements iInput {

    private final KeyboardInput keyboardInput;
    private final MouseInput mouseInput;
    private final AudioOutput audioOutput;

    public InputOutputManager() {
        this.keyboardInput = new KeyboardInput();
        this.mouseInput = new MouseInput();
        this.audioOutput = new AudioOutput();
        System.out.println("[InputOutputManager] Initialized");
    }

    public void handleInput() {

        mouseInput.update();
    }

    @Override
    public boolean isKeyPressed(int keyCode) {
        return keyboardInput.isPressed(keyCode);
    }

    @Override
    public boolean isKeyJustPressed(int keyCode) {
        return keyboardInput.isJustPressed(keyCode);
    }

    @Override
    public Vector2 getMousePosition() {
        return mouseInput.getPos();
    }

    @Override
    public boolean isMouseButtonPressed(int button) {
        return mouseInput.isButtonPressed(button);
    }

    @Override
    public void playSound(String soundName) {
        audioOutput.playSound(soundName);
    }

    @Override
    public void playMusic(String musicName) {
        audioOutput.playMusic(musicName);
    }

    @Override
    public void stopMusic() {
        audioOutput.stopMusic();
    }

    public void dispose() {
        audioOutput.dispose();
        System.out.println("[InputOutputManager] Disposed");
    }
}
