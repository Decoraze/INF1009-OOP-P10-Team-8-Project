package com.p10.core.movement;

import java.util.List;

import com.p10.core.entities.Entity;

public abstract class PlayerMovement {
	
	
	public PlayerMovement() {
		
	}
	
	
	 // For the particular object itself
    public void update(Entity entity, float dt) {
    	entity.update(dt);
    }
	
}
