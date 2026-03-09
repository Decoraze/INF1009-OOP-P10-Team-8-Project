package com.p10.game.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.p10.core.interfaces.iInput;

/**
 * EduPopup displays educational text at the start of each level.
 * Shows level name + cybersecurity explanation.
 * Blocks gameplay until player presses ENTER/SPACE/clicks.
 *
 */
public class EduPopup {
    private String title;
    private String text;
    private boolean visible;
    private BitmapFont font;
    private BitmapFont titleFont;
    private float screenW, screenH;

    public EduPopup(float screenW, float screenH) {
        this.screenW = screenW;
        this.screenH = screenH;
        this.visible = false;
        // : Initialize fonts (titleFont scale 1.5)
    }

    public void show(String title, String text) {
        // : Set title, text, and make visible
    }

    public void handleInput(iInput input) {
        // : If ENTER, SPACE, or mouse click pressed → hide popup
    }

    public void renderShapes(ShapeRenderer renderer) {
        // : If not visible, return
        // : Draw centered popup box (dark background with cyan borders)
    }

    public void renderText(SpriteBatch batch) {
        // : If not visible, return
        // : Draw title in cyan
        // : Draw text lines in white (split by \n)
        // : Draw "Press ENTER or click to continue..." hint at bottom
    }

    public boolean isVisible() {
        return visible;
    }

    public void dispose() {
        // : Dispose fonts
    }
}
