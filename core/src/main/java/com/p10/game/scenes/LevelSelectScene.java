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
 * LevelSelectScene displays 4 level options for the player to choose from.
 * Keys 1-4 select a level, ESC goes back to MainMenu.
 *
 */
public class LevelSelectScene extends Scene {
    private BitmapFont titleFont;
    private BitmapFont font;
    private float screenW, screenH;

    public LevelSelectScene(iCollision collision, iEntityOps entityOps, iSceneControl sceneCtrl,
            iInput input, iAudio audio, iMovement movement,
            float screenW, float screenH) {
        super("LevelSelect", collision, entityOps, sceneCtrl, input, movement, audio);
        this.screenW = screenW;
        this.screenH = screenH;
    }

    @Override
    protected void onLoad() {
        // : Initialize fonts (titleFont scale 2, font scale 1.2)
    }

    @Override
    protected void onUnload() {
        // : Dispose fonts
    }

    @Override
    public void update(float dt) {
        // : NUM_1 → set level1_DDoS, switch to GameplayScene
        // : NUM_2 → set level2_Virus, switch to GameplayScene
        // : NUM_3 → set level3_Phishing, switch to GameplayScene
        // : NUM_4 → set level4_Mixed, switch to GameplayScene
        // : ESC → switch to MainMenu
    }

    @Override
    public void renderShapes(ShapeRenderer renderer) {
        // : Draw dark background
        // : Draw 4 colored level selection boxes
    }

    @Override
    public void renderTextures(SpriteBatch batch) {
        // : Draw title "SELECT LEVEL"
        // : Draw level labels with descriptions and key hints
        // : Draw ESC hint
    }
}
