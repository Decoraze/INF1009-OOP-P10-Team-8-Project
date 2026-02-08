package com.p10.core.managers;

import java.util.List;

import com.p10.core.entities.*;
import com.badlogic.gdx.math.Vector2;


/**
 * MovementManager - Updates entity positions
 * 
 */
public class MovementManager {

    public MovementManager() {
        System.out.println("[MovementManager] Stub initialized");
    }
    
    
    // For the particular object itself
    public void updateMovement(List<Entity> entities, float dt) {
    	for (Entity obj : entities) {
    		obj.update(dt);				// Velocity TBC
    	}
    }
    
    
    // For all objects
    public void applyPhysics(List<Entity> entities, float dt) {
    	// Empty - implements ---
    	for (Entity obj : entities) {
    		obj.getPosition().add(200 * dt, 0);				// Velocity TBC
    	}
    }
    
    
    // When the objects get hit
    public void applyForce(Entity e, Vector2 force) {
    	e.getPosition().add(force);
    }
    
    

    public void dispose() {
        System.out.println("[MovementManager] Stub disposed");
    }
}