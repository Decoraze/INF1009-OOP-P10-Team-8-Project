package com.p10.core.interfaces;

import com.badlogic.gdx.math.Vector2;

/**
 * iMovement Interface
 * 
 * Contract for movement and physics operations.
 * Implemented by: MovementManager
 * Used by: Scene (to apply movement/physics without knowing about
 * MovementManager directly)
 * 
 */
public interface iMovement {

    /**
     * Apply movement to an entity based on its velocity
     * 
     * @param entity    The entity to move
     * @param deltaTime Time since last frame
     */
    void applyMovement(Object entity, float deltaTime); // TODO: Replace Object with Entity when rumaana creates it

    /**
     * Apply physics calculations (gravity, acceleration, etc.)
     * 
     * @param entity    The entity to apply physics to
     * @param deltaTime Time since last frame
     */
    void applyPhysics(Object entity, float deltaTime); // TODO: Replace Object with Entity when rumaana creates it

    /**
     * Apply a force to an entity
     * 
     * @param entity The entity to apply force to
     * @param force  The force vector to apply
     */
    void applyForce(Object entity, Vector2 force); // TODO: Replace Object with Entity when rumaana creates it
}