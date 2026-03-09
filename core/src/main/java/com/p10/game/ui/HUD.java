package com.p10.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.p10.core.interfaces.iInput;
import com.p10.game.grid.TowerPlacer;
import com.p10.game.wave.GameState;

/**
 * HUD renders the in-game UI:
 * - Top bar: lives, currency, wave counter, score
 * - Bottom bar: tower shop cards (4 tower types with prices, key hints)
 * - Prep phase indicator
 *
 * Also handles number key input to select towers from the shop.
 *
 */
public class HUD {
    private BitmapFont font;
    private BitmapFont smallFont;
    private float screenW, screenH;
    private static final float BAR_HEIGHT = 50f;
    private static final float CARD_W = 80f;
    private static final float CARD_H = 65f;
    private static final float CARD_GAP = 8f;

    public HUD(float screenW, float screenH) {
        this.screenW = screenW;
        this.screenH = screenH;
        // : Initialize fonts (font scale 1.2, smallFont scale 0.8)
    }

    public void renderShapes(ShapeRenderer renderer, GameState state) {
        // : Draw top info bar (dark background)
        // : Draw bottom shop bar (dark background)
        // : Draw 4 tower shop cards with states:
        // - During wave: greyed out (shop closed)
        // - Can't afford: dimmed with faded color
        // - Selected: bright border + full color
        // - Available: normal colored card
        // : Draw prep phase indicator box if in prep phase
    }

    public void renderText(SpriteBatch batch, GameState state, String nextEnemyType) {
        // : Top bar — draw Lives, Currency, Wave X/Y, Score
        // : Bottom bar — draw tower names, prices, key hints [1]-[4]
        // : If prep phase — draw "PREP PHASE", next enemy type, "[SPACE] Start
        // Wave"
    }

    /**
     * Handle number key presses (1-4) to select tower type from shop.
     * Only works during prep phase and if player can afford the tower.
     */
    public void handleInput(iInput input, GameState state, TowerPlacer placer) {
        // : If not prep phase, deselect and return
        // : Check keys NUM_1 through NUM_4
        // : If player can afford the tower, set it as selected in both state and
        // placer
    }

    private Color getTowerColor(String type) {
        // : Return color per tower type (same as Tower.getTowerColor())
        return Color.WHITE;
    }

    public void dispose() {
        // : Dispose fonts
    }

    public BitmapFont getFont() {
        return font;
    }
}
