package com.p10.core.interfaces;

import com.p10.core.entities.CollidableEntity;

/**
 * iCollision Interface
 * 
 * Contract for collision operations.
 * Implemented by: CollisionManager
 * Used by: Scene (to check collisions without knowing about CollisionManager
 * directly)
 */
public interface iCollision {

    /**
     * Check if two collidable entities are colliding
     * 
     * @param e1 First entity
     * @param e2 Second entity
     * @return true if colliding, false otherwise
     */
    boolean checkCollision(CollidableEntity e1, CollidableEntity e2);

}