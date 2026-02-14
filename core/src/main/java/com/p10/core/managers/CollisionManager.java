package com.p10.core.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.p10.core.entities.CollidableEntity;
import com.p10.core.entities.CollisionDetection;
import com.p10.core.entities.CollisionResponse;
import com.p10.core.interfaces.iCollision;//added interfaced. 

/**
 * CollisionManager - Handles collision detection
 * 1
 */
public class CollisionManager implements iCollision {
    private CollisionDetection detector;
    private CollisionResponse responder;
    private Map<String, Float> collisionCooldowns;
    private static final float COOLDOWN = 1.0f; // 1 second between logs

    public CollisionManager() {
        // detector and responder are abstract — need concrete subclasses to instantiate
        // For now, left as null until concrete implementations are created
    	this.detector = new CollisionDetection();
    	this.responder = new CollisionResponse();
    	this.collisionCooldowns = new HashMap<>();
        System.out.println("[CollisionManager] Stub initialized");
    }

    // this violates SRP refactor it before continuing
    public void checkCollisions(List<CollidableEntity> collidableEntities) {
        // Decrease cooldowns
        List<String> expired = new ArrayList<>();
        for (Map.Entry<String, Float> entry : collisionCooldowns.entrySet()) {
            entry.setValue(entry.getValue() - 0.016f); // ~1 frame at 60fps
            if (entry.getValue() <= 0)
                expired.add(entry.getKey());
        }
        for (String key : expired)
            collisionCooldowns.remove(key);

        // this nested loop is already in detection so remove
        for (int i = 0; i < collidableEntities.size(); i++) {
            for (int j = i + 1; j < collidableEntities.size(); j++) {
                CollidableEntity e1 = collidableEntities.get(i);
                CollidableEntity e2 = collidableEntities.get(j);
                // this check is also under detection so remove
                if (checkCollision(e1, e2)) {
                	// use return of detect to pass in parameters for this method
                	responder.resolveCollision(e1, e2);
                    String key = e1.getId() + "<->" + e2.getId();
                    if (!collisionCooldowns.containsKey(key)) {
                		System.out.println("[CollisionManager] Collision detected: " + e1.getId() + " <-> " + e2.getId());
                        responder.onCollision(e1, e2);
                        collisionCooldowns.put(key, COOLDOWN);
                    }
            	}
                /*
                 * float w = com.badlogic.gdx.Gdx.graphics.getWidth();
                 * float h = com.badlogic.gdx.Gdx.graphics.getHeight();
                 * e1.getPosition().x = Math.max(0, Math.min(w, e1.getPosition().x));
                 * e1.getPosition().y = Math.max(0, Math.min(h, e1.getPosition().y));
                 * e2.getPosition().x = Math.max(0, Math.min(w, e2.getPosition().x));
                 * e2.getPosition().y = Math.max(0, Math.min(h, e2.getPosition().y));
                 */
            }
        }
    }
    
    public void addCollidable(CollidableEntity e) {
        // delegates to detector internally
        if (detector != null)
            detector.addCollidable(e);
    }

    // this should be removed (?) should exist in detection instead (?)
    public boolean checkCollision(CollidableEntity e1, CollidableEntity e2) {
        return e1.getHitbox().overlaps(e2.getHitbox());
    }

    public List<CollidableEntity> getCollidables() {
        return new ArrayList<CollidableEntity>(); // Get CollidableEntity list from iCollision interface
    }

    public void dispose() {
        System.out.println("[CollisionManager] Stub disposed");
    }
}