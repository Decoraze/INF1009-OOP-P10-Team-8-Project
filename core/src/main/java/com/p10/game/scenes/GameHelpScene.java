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

/**
 * GameHelpScene — explains game mechanics, tower matchups, and controls.
 * Accessed from MainMenu via H key.
 * ESC or ENTER returns to MainMenu.
 *
 * @author Aurelius (integration)
 */
public class GameHelpScene extends Scene {
    private BitmapFont titleFont;
    private BitmapFont font;
    private BitmapFont smallFont;
    private float screenW, screenH;

    public GameHelpScene(iCollision collision, iEntityOps entityOps, iSceneControl sceneCtrl,
            iInput input, iAudio audio, iMovement movement,
            float screenW, float screenH) {
        super("GameHelp", collision, entityOps, sceneCtrl, input, movement, audio);
        this.screenW = screenW;
        this.screenH = screenH;
    }

    @Override
    protected void onLoad() {
        titleFont = FontManager.getTitle();
        font = FontManager.getBody();
        smallFont = FontManager.getSmall();
    }

    @Override
    protected void onUnload() {
        // titleFont.dispose();
        // font.dispose();
        // smallFont.dispose();
    }

    @Override
    public void update(float dt) {
        // ESC or ENTER returns to main menu
        if (input.isKeyJustPressed(Keys.ESCAPE) || input.isKeyJustPressed(Keys.ENTER)) {
            sceneCtrl.switchScene("MainMenu");
        }
    }

    @Override
    public void renderShapes(ShapeRenderer renderer) {
        // Dark background
        renderer.setColor(0.08f, 0.08f, 0.15f, 1f);
        renderer.rect(0, 0, screenW, screenH);

        // Title bar
        renderer.setColor(0.2f, 0.6f, 1f, 0.8f);
        renderer.rect(0, screenH - 55, screenW, 55);

        // Tower matchup section background
        renderer.setColor(0.12f, 0.12f, 0.2f, 1f);
        renderer.rect(20, 160, screenW - 40, 200);

        // Controls section background
        renderer.setColor(0.12f, 0.12f, 0.2f, 1f);
        renderer.rect(20, 20, screenW - 40, 120);

        // Tower color indicators
        float cardY = 310;
        float cardW = 170;
        // Firewall — orange
        renderer.setColor(Color.ORANGE);
        renderer.rect(30, cardY, 12, 12);
        // Antivirus — green
        renderer.setColor(Color.GREEN);
        renderer.rect(30 + cardW, cardY, 12, 12);
        // Encryption — cyan
        renderer.setColor(Color.CYAN);
        renderer.rect(30 + cardW * 2, cardY, 12, 12);
        // IDS — purple
        renderer.setColor(Color.PURPLE);
        renderer.rect(30 + cardW * 3, cardY, 12, 12);
    }

    @Override
    public void renderTextures(SpriteBatch batch) {
        // Title
        titleFont.setColor(Color.WHITE);
        titleFont.draw(batch, "HOW TO PLAY — NETDEFENDER", 20, screenH - 12);

        // Game concept
        font.setColor(Color.WHITE);
        font.draw(batch, "Defend your server from cyber threats by placing the RIGHT security towers!", 30,
                screenH - 70);
        font.draw(batch, "Each tower type is effective against specific attacks. Wrong towers deal ZERO damage.", 30,
                screenH - 95);
        font.draw(batch, "Place towers on GREEN tiles during PREP PHASE, then press SPACE to start each wave.", 30,
                screenH - 120);

        // Tower matchups header
        font.setColor(Color.CYAN);
        font.draw(batch, "TOWER MATCHUPS — Match the right defense to each threat:", 30, 355);

        // Tower details
        float cardW = 170;
        smallFont.setColor(Color.WHITE);
        // Firewall
        smallFont.draw(batch, "  FIREWALL [1] $30", 30, 325);
        smallFont.setColor(Color.GREEN);
        smallFont.draw(batch, "  Strong: DDoS, Worm", 30, 305);
        smallFont.setColor(Color.RED);
        smallFont.draw(batch, "  Weak: Virus", 30, 285);

        // Antivirus
        smallFont.setColor(Color.WHITE);
        smallFont.draw(batch, "  ANTIVIRUS [2] $80", 30 + cardW, 325);
        smallFont.setColor(Color.GREEN);
        smallFont.draw(batch, "  Strong: Virus, Trojan", 30 + cardW, 305);
        smallFont.setColor(Color.RED);
        smallFont.draw(batch, "  Weak: Worm", 30 + cardW, 285);

        // Encryption
        smallFont.setColor(Color.WHITE);
        smallFont.draw(batch, "  ENCRYPTION [3] $55", 30 + cardW * 2, 325);
        smallFont.setColor(Color.GREEN);
        smallFont.draw(batch, "  Strong: Phishing, MITM", 30 + cardW * 2, 305);
        smallFont.setColor(Color.RED);
        smallFont.draw(batch, "  Weak: Trojan", 30 + cardW * 2, 285);

        // IDS
        smallFont.setColor(Color.WHITE);
        smallFont.draw(batch, "  IDS [4] $45", 30 + cardW * 3, 325);
        smallFont.setColor(Color.GREEN);
        smallFont.draw(batch, "  Strong: Worm, Trojan", 30 + cardW * 3, 305);
        smallFont.setColor(Color.RED);
        smallFont.draw(batch, "  Weak: DDoS", 30 + cardW * 3, 285);

        // Gameplay tips
        smallFont.setColor(Color.YELLOW);
        smallFont.draw(batch,
                "STRATEGY: Place towers NEXT to the path. Enemies walk along tan/brown tiles toward your server.", 30,
                250);
        smallFont.draw(batch,
                "You earn $50 bonus currency after each wave. Killing enemies also gives currency and score.", 30, 230);
        smallFont.draw(batch, "If enemies reach your server, you lose lives. Game over when lives hit 0!", 30, 210);
        smallFont.draw(batch,
                "Level 4 (Mixed Assault) requires Defense in Depth — use ALL tower types to counter mixed threats.", 30,
                190);
        smallFont.draw(batch, "This teaches the real-world cybersecurity concept of layered security.", 30, 170);

        // Controls section
        font.setColor(Color.CYAN);
        font.draw(batch, "CONTROLS:", 30, 130);
        smallFont.setColor(Color.WHITE);
        smallFont.draw(batch,
                "[1][2][3][4] — Select tower type       [CLICK] — Place tower on grid       [SPACE] — Start wave", 30,
                108);
        smallFont.draw(batch,
                "[M] — Toggle music on/off              [ESC] — Return to main menu          [ENTER] — Dismiss popups",
                30, 88);

        // Footer
        font.setColor(Color.LIGHT_GRAY);
        font.draw(batch, "Press ESC or ENTER to return to Main Menu", 250, 30);
    }
}
