package com.p10.core.entities;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public abstract class CollidableEntity extends Entity {
    protected Rectangle hitbox; //
    protected int collisionLayer; //

    public CollidableEntity(String id, float x, float y, float w, float h) {
        super(id, x, y);
        this.hitbox = new Rectangle(x, y, w, h);
    }

    public abstract Rectangle getHitbox(); //
    public abstract boolean checkCollision(CollidableEntity other); //
    public abstract void onCollisionEnter(CollidableEntity other); //
}
