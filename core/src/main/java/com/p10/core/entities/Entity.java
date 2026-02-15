package com.p10.core.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
    protected String id;
    protected Vector2 position;
    protected float rotation;
    protected Vector2 scale;
    private boolean active;
    private boolean visible;
    private boolean kinematic = false;
    // mass — determines how much an entity gets pushed in collisions
    // default 1.0f so existing behaviour is preserved if not set
    protected float mass = 1.0f;
    // velocity — the entity's current speed and direction
    // movement systems SET velocity, collision system READS it
    protected Vector2 velocity;
    // kinematics were added here to tell collision system which entity should and
    // should not be physically pushed

    public boolean isKinematic() {
        return kinematic;
        // if true here, collisions are detected but entity doesn't get moved. (this is
        // useful in certain games like pong etc and is general/abnstract enough where
        // alot of games can utilise it and can be left alone if not used)
    }

    public void setKinematic(boolean k) {
        this.kinematic = k;
    }

    // velocity getters and setters
    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(float vx, float vy) {
        this.velocity.set(vx, vy);
    }

    public Entity(String id, float x, float y) {
        this.id = id;
        this.position = new Vector2(x, y);
        this.scale = new Vector2(1, 1);
        this.rotation = 0f;
        this.active = true;
        this.visible = true;
        this.velocity = new Vector2(0, 0);
    }

    // Abstract methods: Children MUST implement these
    public abstract void update(float deltaTime);

    // Generic render, specific renderers used in subclasses
    public abstract void renderShapes(ShapeRenderer renderer);

    public abstract void renderTextures(SpriteBatch batch);

    // Getters and Setters
    public String getId() {
        return id;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 pos) {
        this.position.set(pos);
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public Vector2 getScale() {
        return scale;
    }

    public void setScale(float x, float y) {
        this.scale.set(x, y);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

}
