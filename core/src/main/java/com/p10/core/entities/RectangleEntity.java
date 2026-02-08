package com.p10.core.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle; // The LibGDX math tool

public class RectangleEntity extends Shape { //i have to name the file as rectangleEntity because of having to use the li[gdx Rectangle math tool
    private float width;  //
    private float height; //

    public RectangleEntity(String id, float x, float y, float width, float height, Color color) {
        // Initializes the Shape/CollidableEntity with the provided width and height
        super(id, x, y, width, height, color);
        this.width = width;
        this.height = height;
    }


    @Override
    public void update(float dt) {
        // Keeps the hitbox (the imported Rectangle) synced with the entity position
        hitbox.setPosition(position.x, position.y);
    }

    // Required by CollidableEntity abstract methods
    @Override public Rectangle getHitbox() { return hitbox; }
    @Override public boolean checkCollision(CollidableEntity other) { return hitbox.overlaps(other.getHitbox()); }
    @Override public void onCollisionEnter(CollidableEntity other) {}
    
    
    // Required by Entity abstract methods
    @Override
    public void renderShapes(ShapeRenderer renderer) {
    	renderer.setColor(color);
        // Uses the unique width and height fields from UML
        renderer.rect(position.x, position.y, width, height);
    }
    
    @Override
    public void renderTextures(SpriteBatch batch) {
    }
    
    
}
