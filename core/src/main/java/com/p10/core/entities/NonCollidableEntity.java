package com.p10.core.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Abstract class for entities that do not participate in collision detection.
 * Used for UI, backgrounds, and decorative elements.
 */
public abstract class NonCollidableEntity extends Entity {

    public NonCollidableEntity(String id, float x, float y) {
        super(id, x, y);
    }

    @Override
    public abstract void update(float dt); //

}
