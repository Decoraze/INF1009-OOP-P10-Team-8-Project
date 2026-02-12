package com.p10.core.interfaces;

import java.util.List;
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
    
    /**
     * Get all collidable entities
     * 
     * @return List of all collidable entities
     */
    List<CollidableEntity> getCollidables();

    /**
     * Add a collidable entity to be tracked
     * 
     * @param entity The collidable entity to add
     */
    void addCollidable(CollidableEntity e);
}