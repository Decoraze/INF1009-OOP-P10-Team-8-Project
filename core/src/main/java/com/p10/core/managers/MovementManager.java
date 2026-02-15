package com.p10.core.managers;

import java.util.HashMap;
import java.util.Map;

import com.p10.core.entities.Entity;
import com.p10.core.interfaces.iInput;
import com.p10.core.interfaces.iMovement;
import com.p10.core.movement.AIMovement;
import com.p10.core.movement.PlayerMovement;

/**
 * MovementManager - Updates entity positions
 * 
 */
public class MovementManager implements iMovement {// added iMovement implementation for interface
    private PlayerMovement playerMovement; // two movements
    private Map<String, AIMovement> aiMovements; // two movements abstract
    private float screenW = 800, screenH = 480; // init original values

    public MovementManager() {
        this.playerMovement = new PlayerMovement();// init player movement
        this.aiMovements = new HashMap<>();// init movement for hashmap for AI usage
        System.out.println("[MovementManager] Stub initialized");
    }

    // TBC (for whole list of entity or ?)
    public void applyMovement(Entity entity, float dt) {
        if (!aiMovements.containsKey(entity.getId())) {
            aiMovements.put(entity.getId(), new AIMovement());
            System.out.println("[MovementManager] Created AIMovement for: " + entity.getId());
        }
        aiMovements.get(entity.getId()).update(entity, dt, screenW, screenH);
    }

    public void applyPhysics(Entity entity, float dt) {
        entity.update(dt);
    }

    // update player movement
    public void applyPlayerMovement(Entity entity, float dt, iInput input) {
        playerMovement.update(entity, dt, input);
    }

    public void onResize(int width, int height) {
        this.screenW = width;
        this.screenH = height;
        System.out.println("[MovementManager] Screen bounds updated: " + width + "x" + height);
    }

    public void dispose() {
        aiMovements.clear();
        System.out.println("[MovementManager] Disposed");
    }

    // looks up the AIMovement by entity ID in the HashMap, calls reflect. If entity
    // isn't AI (like player), ai is null and nothing happens.
    public void reverseDirection(String entityId, float nx, float ny) {
        AIMovement ai = aiMovements.get(entityId);
        if (ai != null) {
            ai.reflect(nx, ny);
        }
    }
}