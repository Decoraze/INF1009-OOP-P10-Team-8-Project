package com.p10.core.managers;

import java.util.ArrayList;
import java.util.List;

import com.p10.core.entities.*;
/**
 * CollisionManager - Handles collision detection
 * 1
 */
public class CollisionManager {
	private CollisionDetection detector;
	private CollisionResponse responder;
    public CollisionManager() {
        System.out.println("[CollisionManager] Stub initialized");
    }
    
    public void checkCollisions(List<Entity> collidableEntities) {
        // Empty - implements ---
    }
    
    public void checkCollisions(boolean e1, boolean e2) {
    	
    }
    
    public List<CollidableEntity> getCollidables() {
    	return new ArrayList<CollidableEntity>();		// Get CollidableEntity list from iCollision interface
    }
    
    public void dispose() {
        System.out.println("[CollisionManager] Stub disposed");
    }
}