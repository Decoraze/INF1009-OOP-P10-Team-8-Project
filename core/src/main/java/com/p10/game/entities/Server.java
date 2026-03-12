package com.p10.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
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
        try {
            texture = new Texture("sprites/server.png");
        } catch (Exception e) {
            System.out.println("Server texture not found");
            texture = null;
        }
    }

    @Override
    public void update(float dt) {
        // : Sync hitbox position with entity position
        hitbox.setPosition(position.x, position.y);
    }

    public void takeDamage(float amount) {
        // : Reduce health by amount, clamp to 0
        health -= amount;

        if (health < 0) {
            health = 0;
        }
    }

    public boolean isDestroyed() {
        // : Return true if health <= 0
        return health <= 0;
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
        if (other == null || other.getHitbox() == null)
            return false;

        return hitbox.overlaps(other.getHitbox());
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

        if (texture == null) {

            renderer.setColor(Color.BLUE);

            renderer.rect(
                position.x,
                position.y,
                hitbox.getWidth(),
                hitbox.getHeight());
        }

        // Draw hearts above server — each heart = 1 HP
        float heartSize = 10f;
        float heartGap = 3f;
        float totalWidth = maxHealth * (heartSize + heartGap) - heartGap;
        float startX = position.x + (getHitbox().getWidth() - totalWidth) / 2f;
        float heartY = position.y + getHitbox().getHeight() + 8;

        for (int i = 0; i < (int) maxHealth; i++) {
            float hx = startX + i * (heartSize + heartGap);
            if (i < (int) health) {
                renderer.setColor(Color.RED); // full heart
            } else {
                renderer.setColor(0.3f, 0.1f, 0.1f, 1f); // empty heart (dark red)
            }
            // Draw heart as a small filled square (diamond shape would need more vertices)
            renderer.rect(hx, heartY, heartSize, heartSize);
        }
    }

    @Override
    public void renderTextures(SpriteBatch batch) {
        // : If texture exists, draw it centered on position
        if (texture != null) {

            batch.draw(
                texture,
                position.x,
                position.y,
                getHitbox().getWidth(),
                getHitbox().getHeight());
        }
    }
}
