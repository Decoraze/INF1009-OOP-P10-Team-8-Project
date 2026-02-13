package com.p10.core.interfaces;

import com.p10.core.entities.Entity;

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
    void applyMovement(Entity entity, float deltaTime);

    /**
     * Apply physics calculations (gravity, acceleration, etc.)
     * 
     * @param entity    The entity to apply physics to
     * @param deltaTime Time since last frame
     */
    void applyPhysics(Entity entity, float deltaTime);

    void applyPlayerMovement(Entity entity, float dt, iInput input);

}