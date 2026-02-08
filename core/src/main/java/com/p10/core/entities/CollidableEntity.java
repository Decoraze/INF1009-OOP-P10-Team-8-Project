package com.p10.core.entities;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public abstract class CollidableEntity extends Entity {
    private Rectangle hitbox; //
    private int collisionLayer; //

    public CollidableEntity(String id, float x, float y, float w, float h) {
        super(id, x, y);
        this.hitbox = new Rectangle(x, y, w, h);
    }

    // Give this method a BODY { } so children don't have to implement it
    public Rectangle getHitbox() {
        return hitbox;
    }
    public abstract boolean checkCollision(CollidableEntity other); //
    public abstract void onCollisionEnter(CollidableEntity other); //
}
