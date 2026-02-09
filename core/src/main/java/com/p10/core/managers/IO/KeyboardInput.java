package com.p10.core.managers.IO;

import com.badlogic.gdx.Gdx;

public class KeyboardInput {

    public boolean isPressed(int key) {
        return Gdx.input.isKeyPressed(key);
    }

    public boolean isJustPressed(int key) {
        return Gdx.input.isKeyJustPressed(key);
    }
}
