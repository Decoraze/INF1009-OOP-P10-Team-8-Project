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
        titleFont = new BitmapFont();
        titleFont.getData().setScale(2);
        font = new BitmapFont();
        font.getData().setScale(1.2);
    }

    @Override
    protected void onUnload() {
        // : Dispose fonts
        titleFont.dispose();
        font.dispose();
    }

    @Override
    public void update(float dt) {
        // : NUM_1 → set level1_DDoS, switch to GameplayScene
        // : NUM_2 → set level2_Virus, switch to GameplayScene
        // : NUM_3 → set level3_Phishing, switch to GameplayScene
        // : NUM_4 → set level4_Mixed, switch to GameplayScene
        // : ESC → switch to MainMenu
        if (input.isKeyJustPressed(iInput.Keys.NUM_1)) {
            GameplayScene.setSelectedLevel(LevelConfig.level1_DDoS);
            sceneCtrl.switchScene("Gameplay");
        } else if (input.isKeyJustPressed(iInput.Keys.NUM_2)) {
            GameplayScene.setSelectedLevel(LevelConfig.level2_Virus);
            sceneCtrl.switchScene("Gameplay");
        } else if (input.isKeyJustPressed(iInput.Keys.NUM_3)) {
            GameplayScene.setSelectedLevel(LevelConfig.level3_Phishing);
            sceneCtrl.switchScene("Gameplay");
        } else if (input.isKeyJustPressed(iInput.Keys.NUM_4)) {
            GameplayScene.setSelectedLevel(LevelConfig.level4_Mixed);
            sceneCtrl.switchScene("Gameplay");
        } else if (input.isKeyJustPressed(iInput.Keys.ESCAPE)) {
            sceneCtrl.switchScene("MainMenu");
        }
    }

    @Override
    public void renderShapes(ShapeRenderer renderer) {
        // : Draw dark background
            renderer.setColor(0.1f, 0.1f, 0.1f, 1);
            renderer.begin(ShapeRenderer.ShapeType.Filled);
            renderer.rect(0, 0, screenW, screenH);
            renderer.end();
        // : Draw 4 colored level selection boxes
        renderer.setColor(0.3f, 0.3f, 0.3f, 1);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.rect(100, 100, 200, 100);
        renderer.rect(350, 100, 200, 100);
        renderer.rect(100, 250, 200, 100);
        renderer.rect(350, 250, 200, 100);
        renderer.end();

    }

    @Override
    public void renderTextures(SpriteBatch batch) {
        // : Draw title "SELECT LEVEL"
        batch.begin();
        titleFont.setColor(1, 1, 1, 1);
        titleFont.draw(batch, "SELECT LEVEL", screenW / 2 - 100, screenH - 50);
        // : Draw level labels with descriptions and key hints
        font.setColor(1, 1, 1, 1);
        font.draw(batch, "1: DDoS\n- Fast enemies\n- No special abilities", 110, 180);
        font.draw(batch, "2: Virus\n- Enemies with shields\n- Moderate speed", 360, 180);
        font.draw(batch, "3: Phishing\n- Enemies that heal\n- Slow speed", 110, 330);
        font.draw(batch, "4: Mixed\n- Combination of all enemy types", 360, 330);
        // : Draw ESC hint
        font.draw(batch, "ESC: Back to Main Menu", 10, 20);
        batch.end();
    }
}
