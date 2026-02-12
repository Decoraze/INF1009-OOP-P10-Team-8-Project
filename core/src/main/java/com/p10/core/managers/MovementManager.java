package com.p10.core.managers;

import java.util.List;

import com.p10.core.entities.*;
import com.badlogic.gdx.math.Vector2;
import com.p10.core.movement.*;

/**
 * MovementManager - Updates entity positions
 * 
 */
public class MovementManager {

	
	
    public MovementManager() {
        System.out.println("[MovementManager] Stub initialized");
    }
    
    
  
    // TBC (for whole list of entity or ?)
    public void applyMovement(Entity entity, float dt) {
    	entity.update(dt);
    }
    
    
    public void applyPhysics(Entity entity, float dt) {
    	entity.update(dt);
    }
    

    public void dispose() {
        System.out.println("[MovementManager] Stub disposed");
    }
}