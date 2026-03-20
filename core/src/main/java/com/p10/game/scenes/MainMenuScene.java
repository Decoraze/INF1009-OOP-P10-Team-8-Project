package com.p10.game.scenes;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
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

/**
 * MainMenuScene displays the game title and menu options.
 * [1] or [ENTER] → LevelSelect, [H] → HelpScene
 *
 */
public class MainMenuScene extends Scene {
    private static final String SUBTITLE = "NETDEFENDER";

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
        // : Initialize fonts
        titleFont = FontManager.getTitle();
        menuFont = FontManager.getBody();
    }

    @Override
    protected void onUnload() {
        // : Dispose fonts
        // titleFont.dispose();
        // menuFont.dispose();
    }

    @Override
    public void update(float dt) {
        // : NUM_1 or ENTER → switch to LevelSelect
        if (input.isKeyJustPressed(Keys.NUM_1) || input.isKeyJustPressed(Keys.ENTER)) {
            sceneCtrl.switchScene("LevelSelect");
        }
        // H key opens the help/tutorial screen
        if (input.isKeyJustPressed(Keys.H)) {
            sceneCtrl.switchScene("GameHelp");
        }
    }

    @Override
    public void renderShapes(ShapeRenderer renderer) {
        // : Draw dark background
        renderer.setColor(0.03f, 0.03f, 0.1f, 1f);
        renderer.rect(0, 0, screenW, screenH);
        renderer.setColor(0.2f, 0.6f, 1f, 1f);
        renderer.rect(0, screenH * 0.6f, screenW, 3f);
        renderer.rect(0, screenH * 0.35f, screenW, 2f);
        renderer.setColor(0.1f, 0.2f, 0.4f, 0.3f);
        renderer.rect(0, 0, screenW, 60f);
        // : Draw decorative bar under title
        renderer.setColor(0.2f, 0.6f, 1f, 1);
        renderer.rect(screenW / 2 - 150, screenH - 120, 300, 5);

    }

    @Override
    public void renderTextures(SpriteBatch batch) {
        // : Draw "NETDEFENDER" title in cyan
        titleFont.setColor(0.2f, 0.6f, 1f, 1);
        titleFont.draw(batch, "NETDEFENDER", screenW / 2 - 150, screenH - 50);
        GlyphLayout subtitleLayout = new GlyphLayout(menuFont, SUBTITLE);
        menuFont.setColor(0.6f, 0.6f, 0.7f, 1f);
        menuFont.draw(batch, SUBTITLE, (screenW - subtitleLayout.width) / 2f, screenH - 100);
        // : Draw menu options
        menuFont.setColor(1, 1, 1, 1);
        menuFont.draw(batch, "1: Start Game", screenW / 2 - 100, screenH - 200);
        menuFont.draw(batch, "H: How to Play", screenW / 2 - 100, screenH - 250);
        // : Draw "Team P10" footer
        menuFont.draw(batch, "Team P10", screenW / 2 - 100, 20);

    }
}
