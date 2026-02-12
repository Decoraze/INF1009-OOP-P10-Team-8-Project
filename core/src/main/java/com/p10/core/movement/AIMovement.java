package com.p10.core.movement;

import com.p10.core.entities.Entity;

public abstract class AIMovement {
	
	public AIMovement() {
		
	}
	
	
	 // For the particular object itself
    public void update(Entity entity, float dt) {
    	entity.update(dt);
    }
}
