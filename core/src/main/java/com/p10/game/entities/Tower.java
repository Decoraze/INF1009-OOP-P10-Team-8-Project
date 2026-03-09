package com.p10.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.p10.core.entities.CollidableEntity;

/**
 * Tower represents a network defense tool (FIREWALL, ANTIVIRUS, ENCRYPTION,
 * IDS)
 * placed on the grid to shoot at enemies.
 *
 * Each tower type has different range, fireRate, damage, and effectiveness
 * against specific enemy types (damage multiplier system).
 *
 */
public class Tower extends CollidableEntity {
    private String towerType;
    private float range;
    private float fireRate;
    private float damage;
    private float cooldownTimer;
    private com.p10.game.ai.TargetStrategy strategy;
    private Texture texture;

    /**
     * @param id        Unique entity ID
     * @param x         Grid-snapped X position
     * @param y         Grid-snapped Y position
     * @param w         Hitbox width
     * @param h         Hitbox height
     * @param towerType One of: "FIREWALL", "ANTIVIRUS", "ENCRYPTION", "IDS"
     */
    public Tower(String id, float x, float y, float w, float h, String towerType) {
        super(id, x, y, w, h);
        this.towerType = towerType;
        this.cooldownTimer = 0f;
        setKinematic(true);
        this.strategy = com.p10.game.ai.TargetStrategy.NEAREST;

        // : Based on towerType, assign range, fireRate, damage values:
        // FIREWALL: range=140, fireRate=0.7, damage=5
        // ANTIVIRUS: range=120, fireRate=0.9, damage=8
        // ENCRYPTION: range=130, fireRate=0.8, damage=6
        // IDS: range=160, fireRate=0.5, damage=4
        // : Load the correct texture from sprites/ folder
    }

    private Texture loadTex(String path) {
        // : Load texture safely with try-catch, return null if not found
        return null;
    }

    @Override
    public void update(float dt) {
        // : Sync hitbox position with entity position
        // : Tick down cooldownTimer by dt
    }

    public boolean canFire() {
        // : Return true if cooldownTimer <= 0
        return false;
    }

    public void resetCooldown() {
        // : Set cooldownTimer = 1/fireRate
    }

    /**
     * Returns the damage multiplier when this tower attacks a specific enemy.
     * Correct tower vs threat = 1.5x, partially effective = 0.2x, wrong tower =
     * 0.05x
     *
     * Matchups:
     * FIREWALL → strong vs DDOS, WORM | weak vs VIRUS
     * ANTIVIRUS → strong vs VIRUS, TROJAN | weak vs WORM
     * ENCRYPTION → strong vs PHISHING, MITM | weak vs TROJAN
     * IDS → strong vs WORM, TROJAN | weak vs DDOS
     */
    public float getDamageMultiplier(Enemy enemy) {
        // : Implement the damage multiplier matrix above
        return 0.05f;
    }

    public Color getTowerColor() {
        // : Return unique color per towerType
        // FIREWALL=ORANGE, ANTIVIRUS=GREEN, ENCRYPTION=CYAN, IDS=PURPLE
        return Color.WHITE;
    }

    // --- Getters/Setters ---
    public String getTowerType() {
        return towerType;
    }

    public float getRange() {
        return range;
    }

    public float getDamage() {
        return damage;
    }

    public float getFireRate() {
        return fireRate;
    }

    public com.p10.game.ai.TargetStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(com.p10.game.ai.TargetStrategy s) {
        this.strategy = s;
    }

    @Override
    public boolean checkCollision(CollidableEntity other) {
        // : Return true if hitboxes overlap
        return false;
    }

    @Override
    public void onCollisionEnter(CollidableEntity other) {
        // : Handle collision response (if any needed for towers)
    }

    @Override
    public void renderShapes(ShapeRenderer renderer) {
        // : If no texture, draw a colored rectangle as fallback
    }

    @Override
    public void renderTextures(SpriteBatch batch) {
        // : If texture exists, draw it centered on position
    }
}
