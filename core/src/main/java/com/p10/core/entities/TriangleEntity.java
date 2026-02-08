package com.p10.core.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class TriangleEntity extends Shape {
    private Vector2[] vertices; //

    public TriangleEntity(String id, float x, float y, Vector2[] vertices, Color color) {
        // Width/Height for hitbox usually calculated from vertex bounds
        super(id, x, y, 50, 50, color);
        this.vertices = vertices;
    }

    @Override public Rectangle getHitbox() { return hitbox; }
    @Override public boolean checkCollision(CollidableEntity other) { return hitbox.overlaps(other.getHitbox()); }
    @Override public void onCollisionEnter(CollidableEntity other) {}
    
    // Required by Entity abstract methods
    @Override public void update(float dt) {}
    
    @Override
    public void renderShapes(ShapeRenderer renderer) {
    	renderer.setColor(color);
        renderer.triangle(
            vertices[0].x, vertices[0].y,
            vertices[1].x, vertices[1].y,
            vertices[2].x, vertices[2].y
        );
    }
    
    @Override
    public void renderTextures(SpriteBatch batch) {
    }
}
