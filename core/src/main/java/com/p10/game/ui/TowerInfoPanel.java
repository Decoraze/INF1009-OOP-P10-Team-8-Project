package com.p10.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.p10.core.interfaces.iInput;

public class TowerInfoPanel {
    private BitmapFont font;

    public TowerInfoPanel() {
        this.font = new BitmapFont();
    }

    public void renderShapes(ShapeRenderer renderer, float towerX, float towerY, float range) {
        renderer.setColor(Color.WHITE);
        renderer.circle(towerX, towerY, range);
    }

    public void renderText(SpriteBatch batch, String towerName, float towerX, float towerY, int damage, float fireRate) {
        String eduText = getEducationalText(towerName);

        font.setColor(Color.WHITE);
        font.draw(batch, "Name: " + towerName + " | DMG: " + damage + " | Rate: " + fireRate, towerX + 50, towerY + 50);

        font.setColor(Color.CYAN);
        font.draw(batch, eduText, towerX + 50, towerY + 20);
    }

    public boolean isHovered(iInput input, float towerX, float towerY, float towerWidth, float towerHeight) {
        Vector2 mousePos = input.getMousePosition();
        return mousePos.x >= towerX && mousePos.x <= towerX + towerWidth &&
            mousePos.y >= towerY && mousePos.y <= towerY + towerHeight;
    }

    private String getEducationalText(String towerName) {
        if (towerName.equalsIgnoreCase("FIREWALL")) {
            return "A firewall monitors and filters network traffic based on\nsecurity rules. First line of defense against DDoS floods and worms.";
        } else if (towerName.equalsIgnoreCase("ANTIVIRUS")) {
            return "Antivirus software detects and removes malicious programs\nby scanning files against known threat signatures and behaviours.";
        } else if (towerName.equalsIgnoreCase("ENCRYPTION")) {
            return "Encryption converts data into coded form so only authorized\nparties can read it. Defends against phishing and MITM attacks.";
        } else if (towerName.equalsIgnoreCase("IDS")) {
            return "Monitors network traffic for\nsuspicious patterns that may indicate a security breach.";
        }
        return "";
    }

    public void dispose() {
        font.dispose();
    }
}
