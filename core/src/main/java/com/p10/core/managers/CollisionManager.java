package com.p10.core.managers;

import java.util.ArrayList;
import java.util.List;

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
    private java.util.Map<String, Float> collisionCooldowns = new java.util.HashMap<>();
    private static final float COOLDOWN = 1.0f; // 1 second between logs

    public CollisionManager() {
        // detector and responder are abstract — need concrete subclasses to instantiate
        // For now, left as null until concrete implementations are created
        System.out.println("[CollisionManager] Stub initialized");
    }

    public void checkCollisions(List<CollidableEntity> collidableEntities) {
        // Decrease cooldowns
        java.util.List<String> expired = new java.util.ArrayList<>();
        for (java.util.Map.Entry<String, Float> entry : collisionCooldowns.entrySet()) {
            entry.setValue(entry.getValue() - 0.016f); // ~1 frame at 60fps
            if (entry.getValue() <= 0)
                expired.add(entry.getKey());
        }
        for (String key : expired)
            collisionCooldowns.remove(key);

        for (int i = 0; i < collidableEntities.size(); i++) {
            for (int j = 0; j < collidableEntities.size(); j++) {
            	if (collidableEntities.get(i).getId() == collidableEntities.get(j).getId())
            	{
            		continue;
            	}
                CollidableEntity e1 = collidableEntities.get(i);
                CollidableEntity e2 = collidableEntities.get(j);
                // When hitbox overlaps
                if (checkCollision(e1, e2)) {
                    String key = e1.getId() + "<->" + e2.getId();
                    if (!collisionCooldowns.containsKey(key)) {
                        System.out
                                .println("[CollisionManager] Collision detected: " + e1.getId() + " <-> " + e2.getId());
                        e1.onCollisionEnter(e2);
                        e2.onCollisionEnter(e1);
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