package com.p10.core.interfaces;

import java.util.List;
import com.p10.core.entities.*;

/**
 * iEntityOps Interface
 * 
 * entity operations (spawn, remove, get entities).
 * Implemented by: EntityManager
 * Used by: Scene (to manage entities without knowing about EntityManager
 * directly)
 * 
 */
public interface iEntityOps {

    /**
     * Spawn/add a new entity to the game
     * 
     * @param entity The entity to add
     */
    void addEntity(Entity entity);

    /**
     * Remove an entity by its ID
     * 
     * @param id The unique identifier of the entity to remove
     */
    void removeEntity(String id);

    /**
     * Get all entities currently in the game
     * 
     * @return List of all entities
     */
    List<Entity> getAllEntities(); 
    
    
    Entity getEntity(String id);
    /**
     * get an entity by its ID
     * 
     * @param id The unique identifier of the entity to remove
     */
    
}