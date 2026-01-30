package com.p10.core.interfaces;

import java.util.List;

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
    void spawnEntity(Object entity); // TODO: Use Object for now, replace with Entity when rumaana creates it

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
    List<Object> getAllEntities(); // TODO: Use Object for now, replace with Entity when rumaana creates it
}