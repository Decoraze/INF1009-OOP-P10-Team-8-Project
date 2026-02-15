package com.p10.core.scene;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.p10.core.interfaces.iAudio;
import com.p10.core.interfaces.iCollision;
import com.p10.core.interfaces.iEntityOps;
import com.p10.core.interfaces.iInput;
import com.p10.core.interfaces.iMovement;
import com.p10.core.interfaces.iSceneControl;

public class ThirdScene extends Scene {
    private BitmapFont font;

    public ThirdScene(
            iCollision collision,
            iEntityOps entityOps,
            iSceneControl sceneCtrl,
            iInput input,
            iAudio audio,
            iMovement movement) {
        super("ThirdScene", collision, entityOps, sceneCtrl, input, movement, audio);
    }

    @Override
    protected void onLoad() {
        font = new BitmapFont();
        font.getData().setScale(1.5f);
        System.out.println("[ThirdScene] Loaded");

    }

    @Override
    protected void onUnload() {
        if (font != null)
            font.dispose();
        System.out.println("[ThirdScene] unload()");
    }

    @Override
    public void update(float dt) {
        if (input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ENTER)) {
            sceneCtrl.switchScene("HelpScene");
        }
        if (input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            sceneCtrl.switchScene("SecondScene");
        }
    }

    @Override
    public void renderShapes(ShapeRenderer renderer) {
        // End panel
        // renderer.rect(120, 120, 560, 240);
    }

    @Override
    public void renderTextures(SpriteBatch batch) { // render the extures these will be used for later
        if (font == null)
            return;
        float x = 150;
        float top = 320;
        float spacing = 35;

        font.draw(batch, "=== GAME OVER ===", x, top);
        font.draw(batch, "ENTER  -  Return to Help", x, top - spacing * 2);
        font.draw(batch, "ESC    -  Back to Scene 2", x, top - spacing * 3);
    }
    // basically for third scene i just added a game over scene to have a proper way
    // to show its 3 scenes
}
