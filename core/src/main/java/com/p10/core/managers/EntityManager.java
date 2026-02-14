package com.p10.core.managers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.p10.core.entities.CollidableEntity;
import com.p10.core.entities.Entity;
import com.p10.core.entities.Shape;
import com.p10.core.entities.TextureObject;
import com.p10.core.interfaces.iEntityOps;

/**
 * EntityManager - Manages all entities in the game
 * 
 */
public class EntityManager implements iEntityOps { // implements entityInterface.

    private List<Entity> entities; // List to hold entities

    // Constructor
    public EntityManager() {
        this.entities = new ArrayList<>(); // Create entities list
        System.out.println("[EntityManager] Stub initialized");
    }

    public void addEntity(Entity entity) {
        entities.add(entity); // Add entities to the list
    }

    public void removeEntity(String id) {
        for (Entity obj : entities) {
            if (obj.getId().equals(id)) { // remember that if its ==, it is a memory reference comparison not a
                                          // string/int comparison anymore. changed to .equals
                entities.remove(obj); // Remove entities on the list
                break; // modifying the list while iterating with a for-each loop. This will throw
                       // ConcurrentModificationException. so added a break after remove. once found ,
                       // stop looping.
            }
        }
    }

    public List<Entity> getAllEntities() {
        return new ArrayList<>(this.entities); // Return a copy of the list to hold entities
        // return entities; // OLD, violates Encapsulation
    }

    public Entity getEntity(String id) {
        // Loop through list to find the ID
        for (Entity obj : entities) {
            if (obj.getId().equals(id)) {// remember that if its ==, it is a memory reference comparison not a
                                         // string/int comparison anymore. changed to .equals
                return obj;
            }
        }
        return null;
    }

    // TBC
    public List<CollidableEntity> getCollidableEntities() {
        ArrayList<CollidableEntity> temp = new ArrayList<>();
        for (Entity obj : entities) {
            if (obj instanceof CollidableEntity) {
                temp.add((CollidableEntity) obj);
            }
        }
        return temp;
    }// change getCollidableEntities() return type to List<CollidableEntity>:

    // TBC
    public void updateAll(float deltaTime) {
        for (Entity obj : entities) {
            obj.update(deltaTime);
        }
    }

    public void renderShapes(ShapeRenderer renderer) {
        // Loop through entity list
        for (Entity obj : entities) {
            // If it's an object of either circle or triangle then draw
            if (obj instanceof Shape) {

                obj.renderShapes(renderer); // Call method to render
            }
        }
    }

    public void renderTextures(SpriteBatch batch) {
        // Loop through entity list
        for (Entity obj : entities) {
            if (obj instanceof TextureObject) {
                obj.renderTextures(batch); // Call method to render
            }
        }
    }

    public int getEntityCount() {
        return entities.size();
    }

    public void dispose() {
        entities.clear();
        System.out.println("[EntityManager] Stub disposed");
    }

    public void onResize(int width, int height) {
        System.out.println("[EntityManager] Handling resize: " + width + "x" + height);
        // add entity border update if needed...
    }

    // debugging purposes for hitboxes.
    public void renderHitboxes(ShapeRenderer renderer) {
        for (Entity obj : entities) {
            if (obj instanceof CollidableEntity) {
                CollidableEntity ce = (CollidableEntity) obj;
                com.badlogic.gdx.math.Rectangle hb = ce.getHitbox();
                renderer.setColor(com.badlogic.gdx.graphics.Color.YELLOW);
                renderer.rect(hb.x, hb.y, hb.width, hb.height);
                // System.out.println(
                // "[HITBOX] " + ce.getId() + " at " + hb.x + "," + hb.y + " size " + hb.width +
                // "x" + hb.height);

            }
        }
    }
}