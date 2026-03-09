package com.p10.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.p10.core.entities.CollidableEntity;

/**
 * Server is the player's base that enemies try to reach.
 * Placed at the end of the enemy path. Takes damage when enemies reach it.
 * Game over when server health reaches 0.
 */
public class Server extends CollidableEntity {
    private float health;
    private float maxHealth;
    private Texture texture;

    public Server(String id, float x, float y, float w, float h, float health) {
        super(id, x, y, w, h);
        this.health = health;
        this.maxHealth = health;
        setKinematic(true);
        // : Load server texture from sprites/server.png
    }

    @Override
    public void update(float dt) {
        // : Sync hitbox position with entity position
    }

    public void takeDamage(float amount) {
        // : Reduce health by amount, clamp to 0
    }

    public boolean isDestroyed() {
        // : Return true if health <= 0
        return false;
    }

    public float getHealth() {
        return health;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    @Override
    public boolean checkCollision(CollidableEntity other) {
        // : Return true if hitboxes overlap
        return false;
    }

    @Override
    public void onCollisionEnter(CollidableEntity other) {
        // : Handle collision response (if any)
    }

    @Override
    public void renderShapes(ShapeRenderer renderer) {
        // : If no texture, draw a blue rectangle as fallback
        // : Always draw health bar above server (background + colored fill based on
        // HP ratio)
    }

    @Override
    public void renderTextures(SpriteBatch batch) {
        // : If texture exists, draw it centered on position
    }
}
