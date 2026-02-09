package com.p10.core.managers.IO;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class MouseInput {

    private final Vector2 position = new Vector2();

    public void update() {
        position.set(Gdx.input.getX(), Gdx.input.getY());
    }

    public Vector2 getPos() {
        update();
        return position;
    }

    public boolean isButtonPressed(int button) {
        return Gdx.input.isButtonPressed(button);
    }
}
