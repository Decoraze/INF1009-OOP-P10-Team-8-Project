package com.p10.core.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class CircleEntity extends Shape {
    private float radius; //

    public CircleEntity(String id, float x, float y, float radius, Color color) {
        // Hitbox size is diameter (radius * 2)
        super(id, x, y, radius * 2, radius * 2, color);
        this.radius = radius;
    }


    @Override public Rectangle getHitbox() { return hitbox; }
    @Override public boolean checkCollision(CollidableEntity other) { return hitbox.overlaps(other.getHitbox()); }
    @Override public void onCollisionEnter(CollidableEntity other) { /* Logic for hitting things */ }
    
    // Required by Entity abstract methods
    @Override
    public void update(float dt) {
        // Keep hitbox centered on the circle
        hitbox.setCenter(position.x, position.y);
    }
    
    @Override
    public void renderShapes(ShapeRenderer renderer) {
        // implementation will call SpriteBatch in the Manager
    	renderer.setColor(color);
        if (filled) renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.circle(position.x, position.y, radius);
    }
    
    @Override
    public void renderTextures(SpriteBatch batch) {
    }
}
