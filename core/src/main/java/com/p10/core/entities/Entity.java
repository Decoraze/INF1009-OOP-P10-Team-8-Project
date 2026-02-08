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

    public Entity(String id, float x, float y) {
        this.id = id;
        this.position = new Vector2(x, y);
        this.scale = new Vector2(1, 1);
        this.rotation = 0f;
        this.active = true;
        this.visible = true;
    }

    // Abstract methods: Children MUST implement these
    public abstract void update(float deltaTime);
    
    // Generic render, specific renderers used in subclasses
    public abstract void renderShapes(ShapeRenderer renderer); 
    public abstract void renderTextures(SpriteBatch batch); 

    // Getters and Setters
    public String getId() {
        return id; }
    public Vector2 getPosition() {
        return position; }
    public void setPosition(Vector2 pos) {
        this.position.set(pos); }
    public float getRotation() {
        return rotation; }
    public void setRotation(float rotation) {
        this.rotation = rotation; }
    public Vector2 getScale() {
        return scale; }
    public void setScale(float x, float y) {
        this.scale.set(x, y); }
    public boolean isActive() {
        return active; }
    public void setActive(boolean active) {
        this.active = active; }
    public boolean isVisible() {
        return visible; }
    public void setVisible(boolean visible) {
        this.visible = visible; }

}
