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
    }

    @Override
    protected void onUnload() {
        // : Dispose fonts
    }

    @Override
    public void update(float dt) {
        // : NUM_1 or ENTER → switch to LevelSelect
        // : H → switch to HelpScene
    }

    @Override
    public void renderShapes(ShapeRenderer renderer) {
        // : Draw dark background
        // : Draw decorative bar under title
    }

    @Override
    public void renderTextures(SpriteBatch batch) {
        // : Draw "NETDEFENDER" title in cyan
        // : Draw menu options
        // : Draw "Team P10" footer
    }
}
