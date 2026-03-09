package com.p10.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.p10.core.entities.Entity;
import com.p10.core.entities.NonCollidableEntity;

public class HealthBar extends NonCollidableEntity {

    private final Entity parentEntity;
    private final float maxHealth;
    private float currentHealth;
    private final float width = 40f;
    private final float height = 5f;

    public HealthBar(Entity parent, float maxHealth) {
        // Must call the parent constructor defined in NonCollidableEntity
        super("healthbar_" + parent.getId(), parent.getPosition().x, parent.getPosition().y + 50f);
        this.parentEntity = parent;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
    }

    public void updateHealth(float currentHealth) {
        this.currentHealth = currentHealth;
    }

    @Override
    public void update(float deltaTime) {
        if (parentEntity != null) {
            // Your engine uses 'position' Vector2 instead of raw x and y
            this.position.x = parentEntity.getPosition().x;
            this.position.y = parentEntity.getPosition().y + 50f;
        }
    }

    @Override
    public void renderShapes(ShapeRenderer renderer) {
        // Moved the drawing logic into the correct overridden method
        float healthPercent = Math.max(0, currentHealth / maxHealth);

        renderer.setColor(Color.RED);
        renderer.rect(this.position.x, this.position.y, width, height);

        renderer.setColor(Color.GREEN);
        renderer.rect(this.position.x, this.position.y, width * healthPercent, height);
    }

    @Override
    public void renderTextures(SpriteBatch batch) {
        // Leave empty since HealthBar only uses the ShapeRenderer
    }
}
