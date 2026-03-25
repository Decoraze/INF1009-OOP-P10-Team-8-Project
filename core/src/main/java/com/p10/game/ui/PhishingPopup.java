package com.p10.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/*
 * PhishingPopup handles the display and interaction of the phishing
 * email quiz UI. Only responsible for rendering and processing clicks.
 *
 * Data comes from PhishingEmail (data class).
 * Emails come from PhishingEmailPool (factory).
 * This class only handles the UI — SRP compliant.
 */
public class PhishingPopup {

    // tracks if popup is currently visible
    private boolean visible;
    // the email currently being displayed
    private PhishingEmail currentEmail;
    // tracks which suspicious zones player has found
    private boolean[] foundElements;
    // true if player clicked a wrong area
    private boolean failedClick;
    // screen dimensions for layout
    private float screenW, screenH;
    // pool of emails to pick from
    private PhishingEmailPool emailPool;
    private boolean succeeded; // true when all zones found

    public PhishingPopup(float screenW, float screenH) {
        this.screenW = screenW;
        this.screenH = screenH;
        this.visible = false;
        this.failedClick = false;
        this.succeeded = false;
        // create email pool which builds all preset emails
        this.emailPool = new PhishingEmailPool(screenW, screenH);
    }

    /*
     * Show the popup with a random email.
     * Called by GameplayScene when lives hit 0 on phishing level.
     */
    public void show() {
        visible = true;
        failedClick = false;
        // pick random email from pool
        currentEmail = emailPool.getRandomEmail();
        // init tracking array for found zones
        foundElements = new boolean[currentEmail.getZoneCount()];
        succeeded = false;
    }

    public boolean hasSucceeded() {
        return succeeded;
    }

    /*
     * Handle player click on the email.
     * Checks if click hit a suspicious zone or a wrong area.
     * Returns: 1 = found a zone, -1 = wrong click (fail), 0 = nothing
     */
    public int handleClick(float mx, float my) {
        if (!visible || currentEmail == null)
            return 0;

        // email panel bounds — only process clicks inside the panel
        // panel sized for 800x480 screen with margin for HUD
        float px = screenW / 2 - 250;
        float py = 10; // start near bottom, away from HUD at top
        float pw = 500;
        float ph = screenH - 20; // almost full height

        // ignore clicks outside the email panel entirely
        if (mx < px || mx > px + pw || my < py || my > py + ph) {
            return 0; // clicked outside panel, ignore
        }

        boolean hitZone = false;

        // check each suspicious zone for click hit
        for (int i = 0; i < currentEmail.getZones().size(); i++) {
            PhishingEmail.SuspiciousZone zone = currentEmail.getZones().get(i);
            if (!foundElements[i] && zone.getBounds().contains(mx, my)) {
                foundElements[i] = true;
                hitZone = true;
                break;
            }
        }

        if (!hitZone) {
            // clicked inside panel but missed all zones — wrong click
            failedClick = true;
            return -1;
        }

        return 1;
    }

    /*
     * Render popup background, email panel, and zone highlights.
     */
    public void renderShapes(ShapeRenderer renderer) {
        if (!visible)
            return;
        // on success or fail, just show dark overlay — no email panel
        if (succeeded || failedClick) {
            Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
            Gdx.gl.glBlendFunc(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
            renderer.setColor(0, 0, 0, 0.85f);
            renderer.rect(0, 0, screenW, screenH);
            Gdx.gl.glDisable(Gdx.gl.GL_BLEND);
            return; // skip email panel rendering
        }
        float px = screenW / 2 - 250;
        float py = 10; // start near bottom, away from HUD at top
        float pw = 500;
        float ph = screenH - 20; // almost full height

        // dark overlay behind popup
        Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
        Gdx.gl.glBlendFunc(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
        renderer.setColor(0, 0, 0, 0.7f);
        renderer.rect(0, 0, screenW, screenH);

        // email panel background (white)
        renderer.setColor(1, 1, 1, 0.95f);
        renderer.rect(px, py, pw, ph);

        // email header bar (dark blue)
        renderer.setColor(0.15f, 0.2f, 0.35f, 1f);
        renderer.rect(px, py + ph - 40, pw, 40);

        // highlight found zones in green
        for (int i = 0; i < currentEmail.getZones().size(); i++) {
            if (foundElements[i]) {
                PhishingEmail.SuspiciousZone z = currentEmail.getZones().get(i);
                renderer.setColor(0.2f, 0.8f, 0.2f, 0.4f);
                renderer.rect(z.getBounds().x, z.getBounds().y,
                        z.getBounds().width, z.getBounds().height);
            }
        }

        // red overlay if wrong click
        if (failedClick) {
            renderer.setColor(0.8f, 0.1f, 0.1f, 0.3f);
            renderer.rect(px, py, pw, ph);
        }

        Gdx.gl.glDisable(Gdx.gl.GL_BLEND);
        // DEBUG: draw zone outlines so we can see hitboxes
        /*
         * renderer.end();
         * renderer.begin(ShapeRenderer.ShapeType.Line);
         * renderer.setColor(1, 1, 0, 1); // yellow outlines
         * for (int i = 0; i < currentEmail.getZones().size(); i++) {
         * PhishingEmail.SuspiciousZone z = currentEmail.getZones().get(i);
         * renderer.rect(z.getBounds().x, z.getBounds().y,
         * z.getBounds().width, z.getBounds().height);
         * }
         * renderer.end();
         * renderer.begin(ShapeRenderer.ShapeType.Filled);
         */
    }

    /*
     * Render email text content — sender, subject, body, status.
     */
    public void renderText(SpriteBatch batch) {
        if (!visible || currentEmail == null)
            return;
        // on success or fail, just show result message on black screen
        if (succeeded || failedClick) {
            BitmapFont title = FontManager.getTitle();
            BitmapFont small = FontManager.getSmall();
            if (succeeded) {
                title.setColor(0, 1, 0, 1);
                title.draw(batch, "SUCCESS! You identified all threats!", screenW / 2 - 200, screenH / 2 + 20);
                small.setColor(1, 1, 1, 1);
                small.draw(batch, "Press ENTER to continue with 1 life", screenW / 2 - 140, screenH / 2 - 20);
            } else {
                title.setColor(1, 0, 0, 1);
                title.draw(batch, "WRONG! You fell for the phishing email!", screenW / 2 - 220, screenH / 2 + 20);
                small.setColor(1, 1, 1, 1);
                small.draw(batch, "Press ENTER to continue", screenW / 2 - 90, screenH / 2 - 20);
            }
            return; // skip email text rendering
        }
        BitmapFont title = FontManager.getTitle();
        BitmapFont body = FontManager.getBody();
        BitmapFont small = FontManager.getSmall();

        float px = screenW / 2 - 250;
        float py = 10; // start near bottom, away from HUD at top

        // header instruction text
        title.setColor(1, 1, 1, 1);
        title.draw(batch, "PHISHING ALERT - Find the suspicious elements!", px + 10, py + 395);

        // sender line
        body.setColor(0.2f, 0.2f, 0.2f, 1);
        body.draw(batch, "From: " + currentEmail.getSender(), px + 10, py + 360);

        // subject line
        body.setColor(0, 0, 0, 1);
        body.draw(batch, "Subject: " + currentEmail.getSubject(), px + 10, py + 340);

        // email body text line by line
        float textY = py + 310;
        small.setColor(0.1f, 0.1f, 0.1f, 1);
        for (String line : currentEmail.getBodyLines()) {
            small.draw(batch, line, px + 20, textY);
            textY -= 16; // line spacing
        }

        // count how many zones found so far
        int found = 0;
        for (boolean f : foundElements)
            if (f)
                found++;
        int total = currentEmail.getZoneCount();

        // progress counter
        small.setColor(0.2f, 0.6f, 1f, 1);
        small.draw(batch, "Found: " + found + "/" + total + " suspicious elements", px + 10, py + 20);

        // instruction text
        small.setColor(0.5f, 0.5f, 0.5f, 1);
        small.draw(batch, "Click on suspicious parts of the email. Wrong click = Game Over!", px + 10, py + 5);

        // show labels for found zones
        for (int i = 0; i < currentEmail.getZones().size(); i++) {
            if (foundElements[i]) {
                PhishingEmail.SuspiciousZone z = currentEmail.getZones().get(i);
                small.setColor(0, 0.6f, 0, 1);
                small.draw(batch, ">> " + z.getLabel(), z.getBounds().x, z.getBounds().y - 5);
            }
        }

        // fail message — player clicked wrong area
        if (failedClick) {
            title.setColor(1, 0, 0, 1);
            title.draw(batch, "WRONG! You fell for the phishing email!", px + 50, py + 200);
            small.setColor(1, 1, 1, 1);
            small.draw(batch, "Press ENTER to continue", px + 200, py + 180);
        }

        // success message — all zones found
        if (allFound()) {
            title.setColor(0, 1, 0, 1);
            title.draw(batch, "SUCCESS! You identified all threats!", px + 70, py + 200);
            small.setColor(1, 1, 1, 1);
            small.draw(batch, "Press ENTER to continue with 1 life", px + 180, py + 180);
        }
    }

    // --- Getters ---
    public boolean isVisible() {
        return visible;
    }

    public void hide() {
        visible = false;
    }

    public boolean hasFailed() {
        return failedClick;
    }

    // if all email zopnes found, player succeeds and gets revived.
    public boolean allFound() {
        if (foundElements == null)
            return false;
        for (boolean f : foundElements) {
            if (!f)
                return false;
        }
        succeeded = true;
        return true;
    }
}