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
                renderer.rect(0, screenH - 50, screenW, 50);

                // Tower cards — 4 colored boxes
                float cardW = 170;
                float cardH = 70;
                float cardY = 280;
                float startX = (screenW - 4 * cardW - 3 * 10) / 2f; // centered with 10px gaps

                Color[] colors = { Color.ORANGE, Color.GREEN, Color.CYAN, Color.PURPLE };
                for (int i = 0; i < 4; i++) {
                        float cx = startX + i * (cardW + 10);
                        // card background
                        renderer.setColor(0.15f, 0.15f, 0.25f, 1f);
                        renderer.rect(cx, cardY, cardW, cardH);
                        // color strip on left
                        renderer.setColor(colors[i]);
                        renderer.rect(cx, cardY, 6, cardH);
                }

                // Controls section background
                renderer.setColor(0.12f, 0.12f, 0.2f, 1f);
                renderer.rect(20, 15, screenW - 40, 80);
        }

        @Override
        public void renderTextures(SpriteBatch batch) {
                // Title
                titleFont.setColor(Color.WHITE);
                titleFont.draw(batch, "HOW TO PLAY  NETDEFENDER", 20, screenH - 10);

                // Brief intro — 2 lines max
                font.setColor(Color.WHITE);
                font.draw(batch, "Defend your server from cyber threats by placing the RIGHT security towers!", 30,
                                screenH - 65);
                smallFont.setColor(Color.LIGHT_GRAY);
                smallFont.draw(batch,
                                "Each tower type is effective against specific attacks. Wrong towers deal ZERO damage.",
                                30, screenH - 85);

                // Section header
                font.setColor(Color.CYAN);
                font.draw(batch, "TOWER MATCHUPS", 30, screenH - 110);

                // Tower cards text
                float cardW = 170;
                float startX = (screenW - 4 * cardW - 3 * 10) / 2f;
                String[][] towerInfo = {
                                { "FIREWALL [1] $30", "Beats: DDoS, Worm", "Weak: Virus" },
                                { "ANTIVIRUS [2] $80", "Beats: Virus, Trojan", "Weak: Worm" },
                                { "ENCRYPTION [3] $55", "Beats: Phishing, MITM", "Weak: Trojan" },
                                { "IDS [4] $45", "Beats: Worm, Trojan", "Weak: DDoS" },
                };

                for (int i = 0; i < 4; i++) {
                        float cx = startX + i * (cardW + 10) + 14;
                        smallFont.setColor(Color.WHITE);
                        smallFont.draw(batch, towerInfo[i][0], cx, 345);
                        smallFont.setColor(0.4f, 1f, 0.4f, 1f);
                        smallFont.draw(batch, towerInfo[i][1], cx, 325);
                        smallFont.setColor(1f, 0.4f, 0.4f, 1f);
                        smallFont.draw(batch, towerInfo[i][2], cx, 305);
                }

                // Tips — short and clean
                smallFont.setColor(Color.YELLOW);
                smallFont.draw(batch,
                                "Place towers NEXT to the path. Enemies walk along brown tiles toward your server.", 30,
                                260);
                smallFont.draw(batch, "$50 bonus after each wave. Killing enemies earns currency + score.", 30, 240);
                smallFont.draw(batch,
                                "Lives hit 0 = Game Over!  Use layered defense (all tower types) for mixed threat levels.",
                                30, 220);

                // Controls
                font.setColor(Color.CYAN);
                font.draw(batch, "CONTROLS:", 30, 85);
                smallFont.setColor(Color.WHITE);
                smallFont.draw(batch,
                                "[1][2][3][4]  Select tower type     [CLICK]  Place tower on grid     [SPACE]  Start wave",
                                30, 65);
                smallFont.draw(batch,
                                "[M]  Toggle music on/off            [ESC]  Return to main menu       [ENTER]  Dismiss popups",
                                30, 45);

                // Footer
                font.setColor(Color.LIGHT_GRAY);
                font.draw(batch, "Press ESC or ENTER to return to Main Menu", screenW / 2 - 160, 15);
        }
}
