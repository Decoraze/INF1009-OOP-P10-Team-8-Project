package com.p10.core.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class TriangleEntity extends Shape {
    private Vector2[] vertices; //

    public TriangleEntity(String id, float x, float y, Vector2[] vertices, Color color) {
        // Width/Height for hitbox usually calculated from vertex bounds
        super(id, x, y, 50, 50, color);
        this.vertices = vertices;
    }

    @Override
    public boolean checkCollision(CollidableEntity other) { // Use getHitbox() because hitbox is private in parent
        return getHitbox().overlaps(other.getHitbox());
    }

    @Override
    public void onCollisionEnter(CollidableEntity other) {
    }

    // Required by Entity abstract methods
    @Override
    public void update(float dt) {
        getHitbox().setCenter(position.x, position.y);
    }

    @Override
    public void renderShapes(ShapeRenderer renderer) {
        renderer.setColor(color);
        // Draw relative to position
        float size = 25;
        renderer.triangle(
                position.x, position.y + size, // top
                position.x - size, position.y - size, // bottom-left
                position.x + size, position.y - size // bottom-right
        );
    }

    @Override
    public void renderTextures(SpriteBatch batch) {
    }
}
