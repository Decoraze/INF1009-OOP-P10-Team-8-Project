package com.p10.core.scene;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.p10.core.interfaces.iAudio;
import com.p10.core.interfaces.iCollision;
import com.p10.core.interfaces.iEntityOps;
import com.p10.core.interfaces.iInput;
import com.p10.core.interfaces.iMovement;
import com.p10.core.interfaces.iSceneControl;

public class HelpScene extends Scene {
    private BitmapFont font;

    // your normal imports and usage of bitmap font along side your interfaces.
    public HelpScene(
            iCollision collision,
            iEntityOps entityOps,
            iSceneControl sceneCtrl,
            iInput input,
            iAudio audio,
            iMovement movement) {
        super("HelpScene", collision, entityOps, sceneCtrl, input, movement, audio);
    }

    @Override
    protected void onLoad() {
        font = new BitmapFont();
        font.getData().setScale(1.0f);// font size
        System.out.println("[HelpScene] Loaded");

    }

    @Override
    protected void onUnload() {
        if (font != null)
            font.dispose();
        System.out.println("[HelpScene] Unloaded");
    }

    @Override
    public void update(float dt) {
        if (input.isKeyJustPressed(Input.Keys.ENTER)) {
            sceneCtrl.switchScene("FirstScene");
        }
    }

    @Override
    public void renderShapes(ShapeRenderer renderer) {
    }

    @Override
    public void renderTextures(SpriteBatch batch) {
        if (font == null)
            return;
        float x = 280;// position where the text is at
        float top = 420;
        float spacing = 35;

        font.draw(batch, "=== ABSTRACT ENGINE ===", x, top);
        font.draw(batch, "Controls:", x, top - spacing * 2);
        font.draw(batch, "W A S D  -  Move Player", x, top - spacing * 3);
        font.draw(batch, "SPACE    -  Jump Sound", x, top - spacing * 4);
        font.draw(batch, "M        -  Play Music", x, top - spacing * 5);
        font.draw(batch, "N        -  Stop Music", x, top - spacing * 6);
        font.draw(batch, "Scene Navigation:", x, top - spacing * 8);
        font.draw(batch, "ENTER    -  Next Scene", x, top - spacing * 9);
        font.draw(batch, "ESC      -  Previous Scene", x, top - spacing * 10);
        font.draw(batch, "Press ENTER to start", x, top - spacing * 12);
    }
}