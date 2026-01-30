package com.p10.core.interfaces;

import java.util.List;

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
    boolean checkCollision(Object e1, Object e2); // TODO: Replace Object with CollidableEntity when rumaana creates it

    /**
     * Get all collidable entities
     * 
     * @return List of all collidable entities
     */
    List<Object> getCollidables(); // TODO: Replace Object with CollidableEntity when rumaana creates it

    /**
     * Add a collidable entity to be tracked
     * 
     * @param entity The collidable entity to add
     */
    void addCollidable(Object entity); // TODO: Replace Object with CollidableEntity when rumaana creates it
}