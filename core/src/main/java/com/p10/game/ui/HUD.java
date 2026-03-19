package com.p10.game.ui;

import com.badlogic.gdx.Input;
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

    /*
     * deleted necaise we moved tower costs to GameState for better centralization
     * and easier access from both HUD and TowerPlacer
     * private final int COST_FIREWALL = 100;
     * private final int COST_ANTIVIRUS = 150;
     * private final int COST_ENCRYPTION = 200;
     * private final int COST_IDS = 250;
     */
    public HUD(float screenW, float screenH) {
        this.screenW = screenW;
        this.screenH = screenH;
        // : Initialize fonts (font scale 1.2, smallFont scale 0.8)
        this.font = new BitmapFont();
        this.font.getData().setScale(1.2f);
        this.smallFont = new BitmapFont();
        this.smallFont.getData().setScale(0.8f);
    }

    public void renderShapes(ShapeRenderer renderer, GameState state, TowerPlacer placer) {// added TowerPlacer to check
                                                                                           // selected tower type for
                                                                                           // shop card borders
                                                                                           // GameState tracks game
                                                                                           // data, it shouldn't know
                                                                                           // about UI selection state.
                                                                                           // TowerPlacer already owns
                                                                                           // selectedTowerType and
                                                                                           // isDragging. Clean
                                                                                           // separation — GameState =
                                                                                           // data, TowerPlacer =
                                                                                           // interaction.

        // : Draw top info bar (dark background)
        renderer.setColor(0.1f, 0.1f, 0.1f, 0.9f);
        renderer.rect(0, screenH - BAR_HEIGHT, screenW, BAR_HEIGHT);

        // : Draw bottom shop bar (dark background)
        renderer.setColor(0.1f, 0.1f, 0.1f, 0.9f);
        renderer.rect(0, 0, screenW, BAR_HEIGHT + 40);

        // : Draw 4 tower shop cards with states:
        // - During wave: greyed out (shop closed)
        // - Can't afford: dimmed with faded color
        // - Selected: bright border + full color
        // - Available: normal colored card
        String[] towers = { "FIREWALL", "ANTIVIRUS", "ENCRYPTION", "IDS" };
        int[] prices = { GameState.getPrice("FIREWALL"), GameState.getPrice("ANTIVIRUS"),
                GameState.getPrice("ENCRYPTION"), GameState.getPrice("IDS") };// changed to get prices from GameState
                                                                              // instead of hardcoding in HUD for better
                                                                              // centralization
        float startX = 20f;
        float startY = 10f;

        for (int i = 0; i < towers.length; i++) {
            float cx = startX + i * (CARD_W + CARD_GAP);
            boolean canAfford = state.getCurrency() >= prices[i];

            if (!state.isPrepPhase()) {
                renderer.setColor(0.3f, 0.3f, 0.3f, 1f);
            } else if (!canAfford) {
                Color tc = getTowerColor(towers[i]);
                renderer.setColor(tc.r * 0.4f, tc.g * 0.4f, tc.b * 0.4f, 1f);
            } else {
                renderer.setColor(getTowerColor(towers[i]));
            }

            renderer.rect(cx, startY, CARD_W, CARD_H);
            // TODO @ChayHan: Draw yellow border on selected tower card
            // Check if this tower matches towerPlacer's selected type
            // If so, switch to ShapeType.Line, draw yellow rect border (cx-3, startY-3,
            // CARD_W+6, CARD_H+6)
            // Then switch back to ShapeType.Filled

            if (placer.getSelectedTowerType() != null && placer.getSelectedTowerType().equalsIgnoreCase(towers[i])) {
                renderer.set(ShapeRenderer.ShapeType.Line);
                renderer.setColor(Color.YELLOW);
                renderer.rect(cx - 3, startY - 3, CARD_W + 6, CARD_H + 6);
                renderer.set(ShapeRenderer.ShapeType.Filled); // Switch back to filled for the next cards
            }
        }

        // : Draw prep phase indicator box if in prep phase
        if (state.isPrepPhase()) {
            renderer.setColor(0.2f, 0.6f, 0.2f, 0.8f);
            renderer.rect(screenW / 2 - 200, screenH - BAR_HEIGHT - 40, 400, 40);
        }
    }

    public void renderText(SpriteBatch batch, GameState state, String nextEnemyType) {
        // : Top bar — draw Lives, Currency, Wave X/Y, Score
        font.setColor(Color.WHITE);
        font.draw(batch, "Lives: " + state.getLives(), 20, screenH - 15);
        font.draw(batch, "Currency: $" + state.getCurrency(), 150, screenH - 15);
        font.draw(batch, "Wave: " + state.getWave(), 350, screenH - 15);
        font.draw(batch, "Score: " + state.getScore(), 500, screenH - 15);
        // Controls hint in top right
        smallFont.setColor(Color.LIGHT_GRAY);
        smallFont.draw(batch, "[M] Music  [F1] Hitbox  [F3] Debug  [ESC] Menu", screenW - 400, screenH - 40);

        // : Bottom bar — draw tower names, prices, key hints [1]-[4]
        String[] towers = { "FIREWALL", "ANTIVIRUS", "ENCRYPTION", "IDS" };
        int[] prices = { GameState.getPrice("FIREWALL"), GameState.getPrice("ANTIVIRUS"),
                GameState.getPrice("ENCRYPTION"), GameState.getPrice("IDS") };// changed to get prices from GameState
                                                                              // instead of hardcoding in HUD for better
                                                                              // centralization
        float startX = 20f;
        float startY = 10f;

        for (int i = 0; i < towers.length; i++) {
            float cx = startX + i * (CARD_W + CARD_GAP);
            smallFont.setColor(Color.BLACK);
            smallFont.draw(batch, "[" + (i + 1) + "]", cx + 5, startY + CARD_H - 5);
            smallFont.draw(batch, towers[i], cx + 5, startY + CARD_H - 25);
            smallFont.draw(batch, "$" + prices[i], cx + 5, startY + 20);
        }

        // : If prep phase — draw "PREP PHASE", next enemy type, "[SPACE] Start Wave"
        if (state.isPrepPhase()) {
            font.draw(batch, "PREP PHASE - Next Attack: " + nextEnemyType, screenW / 2 - 180,
                    screenH - BAR_HEIGHT - 12);
            font.draw(batch, "[SPACE] Start Wave", screenW - 220, screenH - BAR_HEIGHT - 12);
        }
    }

    /**
     * Handle number key presses (1-4) to select tower type from shop.
     * Only works during prep phase and if player can afford the tower.
     */
    public void handleInput(iInput input, GameState state, TowerPlacer placer) {
        // : If not prep phase, deselect and return
        if (!state.isPrepPhase()) {
            placer.setSelectedTowerType(null);

            return;
        }

        // : Check keys NUM_1 through NUM_4
        String selectedTower = null;
        if (input.isKeyJustPressed(Input.Keys.NUM_1))
            selectedTower = "FIREWALL";
        if (input.isKeyJustPressed(Input.Keys.NUM_2))
            selectedTower = "ANTIVIRUS";
        if (input.isKeyJustPressed(Input.Keys.NUM_3))
            selectedTower = "ENCRYPTION";
        if (input.isKeyJustPressed(Input.Keys.NUM_4))
            selectedTower = "IDS";

        // : If player can afford the tower, set it as selected in both state and placer
        if (selectedTower != null) {
            int cost = GameState.getPrice(selectedTower);// changed to get price from GameState instead of hardcoding in
                                                         // HUD for better centralization

            if (state.getCurrency() >= cost) {
                placer.setSelectedTowerType(selectedTower);
            }
        }
    }

    private Color getTowerColor(String type) {
        // : Return color per tower type (same as Tower.getTowerColor())
        switch (type.toUpperCase()) {
            case "FIREWALL":
                return Color.ORANGE;
            case "ANTIVIRUS":
                return Color.GREEN;
            case "ENCRYPTION":
                return Color.CYAN;
            case "IDS":
                return Color.PURPLE;
            default:
                return Color.WHITE;
        }
    }

    // TODO @ChayHan: Render contextual instruction text during gameplay
    // Prep phase + no tower selected: "[1]-[4] select tower | Click/drag to place |
    // SPACE to start"
    // Prep phase + tower selected: "Selected: [type] | Click to place | Right-click
    // to sell"
    // Wave phase: "Wave in progress! Match towers to threats!"
    // Use smallFont, draw near top of game area (below top HUD bar)
    public void renderInstructions(SpriteBatch batch, GameState state, TowerPlacer placer) {
        smallFont.setColor(Color.WHITE);
        float yPos = screenH - BAR_HEIGHT - 30; // Positioned below the top bar

        if (state.isPrepPhase()) {
            if (placer.getSelectedTowerType() == null && !placer.isDragging()) {
                smallFont.draw(batch, "[1]-[4] select tower | Click/drag to place | SPACE to start", screenW / 2 - 220, yPos);
            } else {
                String type = placer.isDragging() ? "DRAGGING" : placer.getSelectedTowerType();
                smallFont.draw(batch, "Selected: " + type + " | Click/drop to place | Right-click to sell", screenW / 2 - 220, yPos);
            }
        } else {
            smallFont.draw(batch, "Wave in progress! Match towers to threats!", screenW / 2 - 150, yPos);
        }
    }

    // TODO @HuiYang: Game over overlay text (big red "GAME OVER" + press ENTER
    // hint)
    public void renderGameOver(SpriteBatch batch) {
        // TODO @HuiYang
    }

    // TODO @HuiYang: Win overlay text (big green "YOU WIN!" + press ENTER hint)
    public void renderGameWon(SpriteBatch batch) {
        // TODO @HuiYang
    }

    public void dispose() {
        // : Dispose fonts

        font.dispose();
        smallFont.dispose();
    }

    public BitmapFont getFont() {
        return font;
    }
}
