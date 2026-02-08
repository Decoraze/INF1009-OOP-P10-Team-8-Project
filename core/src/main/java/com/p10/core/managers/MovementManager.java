package com.p10.core.managers;

import java.util.List;


import com.p10.core.entities.*;
/**
 * MovementManager - Updates entity positions
 * 
 */
public class MovementManager {

    public MovementManager() {
        System.out.println("[MovementManager] Stub initialized");
    }

    public void updateMovement(List<Entity> entities, float deltaTime) {
        // Empty - implements ---
    	for (Entity obj : entities) {
    		
    	}
    }

    public void dispose() {
        System.out.println("[MovementManager] Stub disposed");
    }
}