package com.p10.core.managers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * EntityManager - Manages all entities in the game
 * STUB VERSION - Will be replaced by Person 2
 */
public class EntityManager {

    private List<Object> entities;

    public EntityManager() {
        this.entities = new ArrayList<>();
        System.out.println("[EntityManager] Stub initialized");
    }

    public void addEntity(Object entity) {
        entities.add(entity);
    }

    public void removeEntity(Object entity) {
        entities.remove(entity);
    }

    public List<Object> getAllEntities() {
        return entities;
    }

    public List<Object> getCollidableEntities() {
        return new ArrayList<>();
    }

    public void updateAll(float deltaTime) {
        // Empty - Person 2 implements
    }

    public void renderShapes(ShapeRenderer renderer) {
        // Empty - Person 2 implements
    }

    public void renderTextures(SpriteBatch batch) {
        // Empty - Person 2 implements
    }

    public int getEntityCount() {
        return entities.size();
    }

    public void dispose() {
        entities.clear();
        System.out.println("[EntityManager] Stub disposed");
    }
}