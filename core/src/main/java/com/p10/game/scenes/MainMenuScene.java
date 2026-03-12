package com.p10.game.scenes;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.p10.core.interfaces.iAudio;
import com.p10.core.interfaces.iCollision;
import com.p10.core.interfaces.iEntityOps;
import com.p10.core.interfaces.iInput;
import com.p10.core.interfaces.iMovement;
import com.p10.core.interfaces.iSceneControl;
import com.p10.core.scene.Scene;

/**
 * MainMenuScene displays the game title and menu options.
 * [1] or [ENTER] → LevelSelect, [H] → HelpScene
 *
 */
public class MainMenuScene extends Scene {
    private BitmapFont titleFont;
    private BitmapFont menuFont;
    private float screenW, screenH;

    public MainMenuScene(iCollision collision, iEntityOps entityOps, iSceneControl sceneCtrl,
            iInput input, iAudio audio, iMovement movement,
            float screenW, float screenH) {
        super("MainMenu", collision, entityOps, sceneCtrl, input, movement, audio);
        this.screenW = screenW;
        this.screenH = screenH;
    }

    @Override
    protected void onLoad() {
        // : Initialize fonts (titleFont scale 3, menuFont scale 1.5)
        titleFont = new BitmapFont();
        titleFont.getData().setScale(3);
        menuFont = new BitmapFont();
        menuFont.getData().setScale(1.5f);
    }

    @Override
    protected void onUnload() {
        // : Dispose fonts
        titleFont.dispose();
        menuFont.dispose();
    }

    @Override
    public void update(float dt) {
        // : NUM_1 or ENTER → switch to LevelSelect
        if (input.isKeyJustPressed(iInput.Keys.NUM_1) || input.isKeyJustPressed(iInput.Keys.ENTER)) {
            sceneCtrl.switchScene("LevelSelect");
        }
        // : H → switch to HelpScene
        if (input.isKeyJustPressed(iInput.Keys.H)) {
            sceneCtrl.switchScene("Help");
        }
    }

    @Override
    public void renderShapes(ShapeRenderer renderer) {
        // : Draw dark background
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(0.1f, 0.1f, 0.1f, 1);
        renderer.rect(0, 0, screenW, screenH);
        // : Draw decorative bar under title
        renderer.setColor(0.2f, 0.6f, 1f, 1);
        renderer.rect(screenW / 2 - 150, screenH - 120, 300, 5);
        renderer.end();
    }

    @Override
    public void renderTextures(SpriteBatch batch) {
        // : Draw "NETDEFENDER" title in cyan
        batch.begin();
        titleFont.setColor(0.2f, 0.6f, 1f, 1);
        titleFont.draw(batch, "NETDEFENDER", screenW / 2 - 150, screenH - 50);
        // : Draw menu options
        menuFont.setColor(1, 1, 1, 1);
        menuFont.draw(batch, "1: Start Game", screenW / 2 - 100, screenH - 200);
        menuFont.draw(batch, "H: Help", screenW / 2 - 100, screenH - 250);
        // : Draw "Team P10" footer
        menuFont.draw(batch, "Team P10", screenW / 2 - 100, 20);
        batch.end();
    }
}
