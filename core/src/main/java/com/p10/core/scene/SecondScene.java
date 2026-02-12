package com.p10.core.scene;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.p10.core.interfaces.iCollision;
import com.p10.core.interfaces.iEntityOps;
import com.p10.core.interfaces.iInput;
import com.p10.core.interfaces.iMovement;
import com.p10.core.interfaces.iSceneControl;

public class SecondScene extends Scene {

    public SecondScene(
            iCollision collision,
            iEntityOps entityOps,
            iSceneControl sceneCtrl,
            iInput input,
            iMovement movement
    ) {
        super("SecondScene", collision, entityOps, sceneCtrl, input, movement);
    }

    @Override
    protected void onLoad() {
        System.out.println("[SecondScene] load()");
    }

    @Override
    protected void onUnload() {
        System.out.println("[SecondScene] unload()");
    }

    @Override
    public void update(float dt) {
        // ESC -> back to FirstScene (menu)
        if (input.isKeyPressed(Input.Keys.ESCAPE)) {
            sceneCtrl.switchScene("FirstScene");
        }

        // Press Q -> End scene
        if (input.isKeyPressed(Input.Keys.Q)) {
            sceneCtrl.switchScene("ThirdScene");
        }
    }

    @Override
    public void renderShapes(ShapeRenderer renderer) {
        // draw border
        renderer.rect(20, 20, 760, 440);
    }
}
