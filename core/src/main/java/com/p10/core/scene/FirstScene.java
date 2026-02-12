package com.p10.core.scene;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.p10.core.interfaces.iCollision;
import com.p10.core.interfaces.iEntityOps;
import com.p10.core.interfaces.iInput;
import com.p10.core.interfaces.iMovement;
import com.p10.core.interfaces.iSceneControl;

public class FirstScene extends Scene {

    public FirstScene(
            iCollision collision,
            iEntityOps entityOps,
            iSceneControl sceneCtrl,
            iInput input,
            iMovement movement
    ) {
        super("FirstScene", collision, entityOps, sceneCtrl, input, movement);
    }

    @Override
    protected void onLoad() {
        System.out.println("[FirstScene] load()");
    }

    @Override
    protected void onUnload() {
        System.out.println("[FirstScene] unload()");
    }

    @Override
    public void update(float dt) {
        // Press ENTER to go to SecondScene
        if (input.isKeyPressed(Input.Keys.ENTER)) {
            input.playSound("jump"); // optional (if your sound map contains it)
            sceneCtrl.switchScene("SecondScene");
        }

        // Quick dev shortcuts:
        if (input.isKeyPressed(Input.Keys.NUM_3)) {
            sceneCtrl.switchScene("ThirdScene");
        }
    }

    @Override
    public void renderShapes(ShapeRenderer renderer) {
        // Simple visual “panel”
        renderer.rect(80, 80, 640, 320);
    }
}
