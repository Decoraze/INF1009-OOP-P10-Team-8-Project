package com.p10.core.managers;

import java.util.ArrayList;
import java.util.List;

import com.p10.core.entities.*;
/**
 * CollisionManager - Handles collision detection
 * 1
 */
public class CollisionManager {
	private List<Entity> collidables;
	
    public CollisionManager() {
    	this.collidables = new ArrayList<>();
        System.out.println("[CollisionManager] Stub initialized");
    }

    public void addCollidable(CollidableEntity e) {
    	collidables.add(e);
    }
    
    public void removeCollidable(String id) {
    	for(Entity e : collidables)
    	{
    		if(id == e.getId())
    		{
    	    	collidables.remove(e);
    	    	break;
    		}
    	}
    }
    
    public void checkCollisions(List<Entity> collidableEntities) {
        // Empty - implements ---
    }

    public void resolveCollisions(CollidableEntity e1, CollidableEntity e2) {
    	
    }
    
    public void dispose() {
        System.out.println("[CollisionManager] Stub disposed");
    }
}