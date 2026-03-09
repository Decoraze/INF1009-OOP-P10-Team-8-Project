package com.p10.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.p10.core.entities.CollidableEntity;

/**
 * Enemy represents a network threat (VIRUS, WORM, TROJAN, DDOS, PHISHING)
 * that moves along a path toward the server.
 *
 * Extends CollidableEntity from the Abstract Engine.
 *
 */
public class Enemy extends CollidableEntity {
    private float health;
    private float maxHealth;
    private float speed;
    private String attackType;
    private int reward;
    private int pathIndex;
    private Texture texture;

    // Static texture cache — load once, reuse for all enemies
    private static Texture texVirus, texWorm, texTrojan, texPhishing;
    private static boolean texturesLoaded = false;

    private static void loadTextures() {
        // : Load textures from sprites/ folder for each enemy type
        // Use try-catch in case textures are missing
        // Set texturesLoaded = true after loading
    }

    /**
     * @param id         Unique entity ID
     * @param x          Spawn X position
     * @param y          Spawn Y position
     * @param size       Hitbox size (square)
     * @param attackType One of: "VIRUS", "WORM", "TROJAN", "DDOS", "PHISHING"
     * @param health     Starting HP
     * @param speed      Movement speed (pixels/sec)
     * @param reward     Currency earned when killed
     */
    public Enemy(String id, float x, float y, float size,
            String attackType, float health, float speed, int reward) {
        super(id, x, y, size, size);
        this.attackType = attackType;
        this.health = health;
        this.maxHealth = health;
        this.speed = speed;
        this.reward = reward;
        this.pathIndex = 0;

        // : Call loadTextures() and assign the correct texture based on attackType
    }

    @Override
    public void update(float dt) {
        // : Sync hitbox center with current position
    }

    public void takeDamage(float amount) {
        // : Reduce health by amount, clamp to 0
    }

    public boolean isDead() {
        // : Return true if health <= 0
        return false;
    }

    public void nextWaypoint() {
        // : Increment pathIndex
    }

    // --- Getters ---
    public float getHealth() {
        return health;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public float getSpeed() {
        return speed;
    }

    public String getAttackType() {
        return attackType;
    }

    public int getReward() {
        return reward;
    }

    public int getPathIndex() {
        return pathIndex;
    }

    public void setPathIndex(int i) {
        this.pathIndex = i;
    }

    @Override
    public boolean checkCollision(CollidableEntity other) {
        // : Return true if this entity's hitbox overlaps with other's hitbox
        return false;
    }

    @Override
    public void onCollisionEnter(CollidableEntity other) {
        // : Handle collision response (if any needed for enemies)
    }

    @Override
    public void renderShapes(ShapeRenderer renderer) {
        // : Draw health bar above enemy (background bar + colored fill based on HP
        // ratio)
        // : If no texture, draw a colored circle as fallback (use getEnemyColor())
    }

    private Color getEnemyColor() {
        // : Return a unique color per attackType
        // VIRUS=RED, WORM=LIME, TROJAN=MAROON, DDOS=YELLOW, PHISHING=MAGENTA
        return Color.WHITE;
    }

    @Override
    public void renderTextures(SpriteBatch batch) {
        // : If texture exists, draw it centered on position
    }
}
