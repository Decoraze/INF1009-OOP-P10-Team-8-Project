package com.p10.core.scene;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.p10.core.interfaces.iCollision;
import com.p10.core.interfaces.iEntityOps;
import com.p10.core.interfaces.iInput;
import com.p10.core.interfaces.iMovement;
import com.p10.core.interfaces.iSceneControl;

public class ThirdScene extends Scene {

    public ThirdScene(
            iCollision collision,
            iEntityOps entityOps,
            iSceneControl sceneCtrl,
            iInput input,
            iMovement movement) {
        super("ThirdScene", collision, entityOps, sceneCtrl, input, movement);
    }

    @Override
    protected void onLoad() {
        System.out.println("[ThirdScene] load()");
    }

    @Override
    protected void onUnload() {
        System.out.println("[ThirdScene] unload()");
    }

    @Override
    public void update(float dt) {
        // ENTER -> restart gameplay
        if (input.isKeyPressed(Input.Keys.ENTER)) {
            sceneCtrl.switchScene("SecondScene");
        }

        // ESC -> back to menu
        if (input.isKeyPressed(Input.Keys.ESCAPE)) {
            sceneCtrl.switchScene("FirstScene");
        }
    }

    @Override
    public void renderShapes(ShapeRenderer renderer) {
        // End panel
        // renderer.rect(120, 120, 560, 240);
    }

    @Override
    public void renderTextures(SpriteBatch batch) { // render the extures these will be used for later
        // no textures for this scene
    }
}
