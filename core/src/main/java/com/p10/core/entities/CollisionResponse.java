package com.p10.core.entities;

public class CollisionResponse {
	public void resolveCollision(CollidableEntity e1, CollidableEntity e2) {
		// Push entities apart so they don't overlap
        float pushStrength = 3.0f;
        float dx = e1.getPosition().x - e2.getPosition().x;
        float dy = e1.getPosition().y - e2.getPosition().y;
        float dist = (float) Math.sqrt(dx * dx + dy * dy);
        if (dist > 0) {
            float nx = dx / dist;
            float ny = dy / dist;
            e1.getPosition().x += nx * pushStrength;
            e1.getPosition().y += ny * pushStrength;
            e2.getPosition().x -= nx * pushStrength;
            e2.getPosition().y -= ny * pushStrength;
        }
        else {
        	e1.getPosition().x += pushStrength;
        	e2.getPosition().x -= pushStrength;
        }
	}
	
	public void onCollision(CollidableEntity e1, CollidableEntity e2) {
        e1.onCollisionEnter(e2);
        e2.onCollisionEnter(e1);
	}
	
}