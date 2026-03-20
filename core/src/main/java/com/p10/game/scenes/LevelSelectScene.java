package com.p10.game.scenes;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
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
import com.p10.game.ui.FontManager;
import com.p10.game.wave.LevelConfig;

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
        // : Initialize fonts
        titleFont = FontManager.getTitle();
        font = FontManager.getBody();
    }

    @Override
    protected void onUnload() {
        // : Dispose fonts
        // titleFont.dispose();
        // font.dispose();
    }

    @Override
    public void update(float dt) {
        // : NUM_1 → set level1_DDoS, switch to GameplayScene
        // : NUM_2 → set level2_Virus, switch to GameplayScene
        // : NUM_3 → set level3_Phishing, switch to GameplayScene
        // : NUM_4 → set level4_Mixed, switch to GameplayScene
        // : ESC → switch to MainMenu
        if (input.isKeyJustPressed(Keys.NUM_1)) {
            GameplayScene.setSelectedLevel(LevelConfig.level1_DDoS());
            sceneCtrl.switchScene("GameplayScene");
        } else if (input.isKeyJustPressed(Keys.NUM_2)) {
            GameplayScene.setSelectedLevel(LevelConfig.level2_Virus());
            sceneCtrl.switchScene("GameplayScene");
        } else if (input.isKeyJustPressed(Keys.NUM_3)) {
            GameplayScene.setSelectedLevel(LevelConfig.level3_Phishing());
            sceneCtrl.switchScene("GameplayScene");
        } else if (input.isKeyJustPressed(Keys.NUM_4)) {
            GameplayScene.setSelectedLevel(LevelConfig.level4_Mixed());
            sceneCtrl.switchScene("GameplayScene");
        } else if (input.isKeyJustPressed(Keys.NUM_5)) {
            GameplayScene.setSelectedLevel(LevelConfig.level5_FullSpectrum());
            sceneCtrl.switchScene("GameplayScene");
        } else if (input.isKeyJustPressed(Keys.NUM_6)) {
            GameplayScene.setSelectedLevel(LevelConfig.level6_Survival());
            sceneCtrl.switchScene("GameplayScene");
        } else if (input.isKeyJustPressed(Keys.ESCAPE)) {
            sceneCtrl.switchScene("MainMenu");
        }
    }

    @Override
    public void renderShapes(ShapeRenderer renderer) {
        // : Draw dark background
        renderer.setColor(0.1f, 0.1f, 0.1f, 1);

        renderer.rect(0, 0, screenW, screenH);

        // TODO @HuiYang: Color-code boxes per threat type instead of all grey
        // Level 1 DDoS: orange (0.8f, 0.5f, 0.1f, 1)
        // Level 2 Virus: red (0.8f, 0.2f, 0.2f, 1)
        // Level 3 Phishing: purple (0.6f, 0.2f, 0.8f, 1)
        // Level 4 Mixed: teal (0.2f, 0.7f, 0.7f, 1)
        // If adding level 5/6: gold (0.9f, 0.8f, 0.1f) and grey (0.5f, 0.5f, 0.5f)
        // May need to rearrange to 2x3 grid layout to fit 6 boxes
        // 2x3 grid layout, color-coded per threat type
        Color[] boxColors = {
                new Color(0.8f, 0.5f, 0.1f, 1), // DDoS — orange
                new Color(0.8f, 0.2f, 0.2f, 1), // Virus — red
                new Color(0.6f, 0.2f, 0.8f, 1), // Phishing — purple
                new Color(0.2f, 0.7f, 0.7f, 1), // Mixed — teal
                new Color(0.9f, 0.8f, 0.1f, 1), // Full Spectrum — gold
                new Color(0.5f, 0.5f, 0.5f, 1), // Survival — grey
        };
        float boxW = 200, boxH = 80, gapX = 50, gapY = 20;// box dimensions and spacing
        float startX = (screenW - (2 * boxW + gapX)) / 2f;// center grid horizontally
        float startY = 60;// start from bottom with some padding
        // Draw 2x3 grid of boxes
        for (int i = 0; i < 6; i++) {
            int col = i % 2;
            int row = i / 2;
            float bx = startX + col * (boxW + gapX);
            float by = startY + (2 - row) * (boxH + gapY);
            renderer.setColor(boxColors[i]);// set color per level
            renderer.rect(bx, by, boxW, boxH);// box background
        }
    }

    @Override
    public void renderTextures(SpriteBatch batch) {
        // : Draw title "SELECT LEVEL"

        titleFont.setColor(1, 1, 1, 1);
        font.setColor(1, 1, 1, 1);
        float boxW = 200, boxH = 80, gapX = 50, gapY = 20;// box dimensions and spacing
        float lx = (screenW - (2 * boxW + gapX)) / 2f;// same startX as shapes for text alignment
        float ly = 60;// same startY as shapes for text alignment
        // : Draw 6 level options with brief descriptions (can be multiline)
        String[] labels = {
                "1: DDoS Attack\n- FIREWALL",
                "2: Virus Outbreak\n- ANTIVIRUS",
                "3: Phishing\nCampaign\n- ENCRYPTION",
                "4: Mixed Assault\n- All threat types\n- ALL towers!",
                "5: Full Spectrum\n- 6 hard waves\n- Tight budget!",
                "6: Survival Mode\n- 10 waves\n- Only 5 lives!",
        };
        for (int i = 0; i < 6; i++) {
            int col = i % 2;
            int row = i / 2;
            float bx = lx + col * (boxW + gapX) + 10;
            float by = ly + (2 - row) * (boxH + gapY) + boxH - 10;
            font.draw(batch, labels[i], bx, by);
        }
    }
}
