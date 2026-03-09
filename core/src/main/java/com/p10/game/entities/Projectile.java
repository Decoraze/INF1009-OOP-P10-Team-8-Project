package com.p10.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.p10.core.entities.CollidableEntity;

/**
 * Projectile fired by towers toward enemies.
 * Moves in a straight line, deals damage on collision, then deactivates.
 */
public class Projectile extends CollidableEntity {
    private float damage;
    private float speed;
    private Vector2 direction;
    private static Texture texture;
    private static boolean texLoaded = false;

    /**
     * @param id        Unique entity ID
     * @param x         Starting X (tower position)
     * @param y         Starting Y (tower position)
     * @param direction Direction vector toward target (will be normalized)
     * @param damage    Damage amount (already multiplied by tower's
     *                  getDamageMultiplier)
     */
    public Projectile(String id, float x, float y, Vector2 direction, float damage) {
        super(id, x, y, 8, 8);
        this.direction = new Vector2(direction).nor();
        this.damage = damage;
        this.speed = 350f;
        // : Load projectile texture once (static), use try-catch
        if (!texLoaded) {
            try {
                texture = new Texture("sprites/projectile.png");
            } catch (Exception e) {
                System.out.println("Projectile texture not found");
                texture = null;
            }
            texLoaded = true;
        }
    }

    @Override
    public void update(float dt) {
        // : Move position along direction * speed * dt
        position.x += direction.x * speed * dt;
        position.y += direction.y * speed * dt;
        // : Sync hitbox center with position
        hitbox.setPosition(position.x, position.y);
        // : Deactivate if projectile goes off-screen (x<-50 || x>900 || y<-50 ||
        // y>550)
        if (position.x < -50 || position.x > 900 ||
            position.y < -50 || position.y > 550) {

            setActive(false);
        }

    }

    public float getDamage() {
        return damage;
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
        // : If other is an Enemy, deal damage and deactivate this projectile
        if (other instanceof Enemy) {

            Enemy enemy = (Enemy) other;

            enemy.takeDamage(damage);
            // Destroy projectile
            setActive(false);
        }
    }

    @Override
    public void renderShapes(ShapeRenderer renderer) {
        // : If no texture, draw a small yellow circle
        if (texture == null) {

            renderer.setColor(Color.YELLOW);

            renderer.circle(
                position.x + hitbox.getWidth() / 2,
                position.y + hitbox.getHeight() / 2,
                4
            );
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
                hitbox.getWidth(),
                hitbox.getHeight()
            );
        }

    }
}
