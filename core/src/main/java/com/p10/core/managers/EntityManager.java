package com.p10.core.managers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.p10.core.entities.*;

/**
 * EntityManager - Manages all entities in the game
 * 
 */
public class EntityManager {

    private List<Entity> entities;				// List to hold entities

    
    // Constructor
    public EntityManager() {
        this.entities = new ArrayList<>();			// Create entities list
        System.out.println("[EntityManager] Stub initialized");
    }

    public void addEntity(Entity entity) {
        entities.add(entity);					// Add entities to the list
    }

    public void removeEntity(String id) {
    	for (Entity obj : entities) {
    		if (obj.getId() == id) {
    			entities.remove(obj);				// Remove entities on the list
    		}
    	}
    }

    public List<Entity> getAllEntities() {
    	return new ArrayList<>(this.entities);			// Return a copy of the list to hold entities
        // return entities;								// OLD, violates Encapsulation						
    }

    public Entity getEntity(String id) {
    	// Loop through list to find the ID
    	for (Entity obj : entities) {
    		if (obj.getId() == id) {
    			return obj;
    		}
    	}
    	return null;
    }
    
    // TBC
    public List<Entity> getCollidableEntities() {
    	ArrayList<Entity> temp = new ArrayList<>();
  
    	for (Entity obj : entities) {
    		 if (obj instanceof CollidableEntity) {
    			 temp.add(obj);
    		 }
    	}
        return temp;
    }

    
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
		    if (obj instanceof CircleEntity || obj instanceof TriangleEntity){
		    	obj.renderShapes(renderer);	// Call method to render
		    }
		}
    }

    public void renderTextures(SpriteBatch batch) {
    	// Loop through entity list
		for (Entity obj : entities) {
			if (obj instanceof TextureObject) {
		       obj.renderTextures(batch);		// Call method to render
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
}