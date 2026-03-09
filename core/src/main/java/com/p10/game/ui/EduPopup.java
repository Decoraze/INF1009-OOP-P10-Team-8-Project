package com.p10.game.ui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
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
        this.font = new BitmapFont();
        this.titleFont = new BitmapFont();
        this.titleFont.getData().setScale(1.5f);
    }

    public void show(String title, String text) {
        // : Set title, text, and make visible
        this.title = title;
        this.text = text;
        this.visible = true;
    }

    public void handleInput(iInput input) {
        // : If ENTER, SPACE, or mouse click pressed → hide popup
        if (!visible) return;
        if (input.isKeyJustPressed(Input.Keys.ENTER) ||
            input.isKeyJustPressed(Input.Keys.SPACE) ||
            input.isMouseButtonPressed(Input.Buttons.LEFT)) {
            this.visible = false;
        }
    }

    public void renderShapes(ShapeRenderer renderer) {
        // : If not visible, return
        if (!visible) return;

        // : Draw centered popup box (dark background with cyan borders)
        float w = 600;
        float h = 400;
        float x = (screenW - w) / 2;
        float y = (screenH - h) / 2;

        renderer.setColor(0.1f, 0.1f, 0.1f, 0.9f);
        renderer.rect(x, y, w, h);

        renderer.setColor(Color.CYAN);
        renderer.rectLine(x, y, x + w, y, 3);
        renderer.rectLine(x, y + h, x + w, y + h, 3);
        renderer.rectLine(x, y, x, y + h, 3);
        renderer.rectLine(x + w, y, x + w, y + h, 3);
    }

    public void renderText(SpriteBatch batch) {
        // : If not visible, return
        if (!visible) return;

        float w = 600;
        float h = 400;
        float x = (screenW - w) / 2;
        float y = (screenH - h) / 2;

        // : Draw title in cyan
        titleFont.setColor(Color.CYAN);
        titleFont.draw(batch, title, x + 50, y + h - 40);

        // : Draw text lines in white (split by \n)
        font.setColor(Color.WHITE);
        font.draw(batch, text, x + 50, y + h - 100);

        // : Draw "Press ENTER or click to continue..." hint at bottom
        font.setColor(Color.LIGHT_GRAY);
        font.draw(batch, "Press ENTER, SPACE, or Click to continue...", x + 50, y + 50);
    }

    public boolean isVisible() {
        return visible;
    }

    public void dispose() {
        // : Dispose fonts
        font.dispose();
        titleFont.dispose();
    }
}
