package com.p10.game.grid;

import java.util.List;

import com.p10.core.entities.Entity;
import com.p10.game.wave.GameState;

/**
 * GameCollisionHandler processes game-specific collisions ON TOP of the
 * engine's CollisionManager.
 * Two collision pairs:
 * 1. Projectile vs Enemy — deal damage, deactivate projectile, award currency
 * if killed
 * 2. Enemy vs Server — server takes damage, lose a life, deactivate enemy
 *
 */
public class GameCollisionHandler {

    /**
     * Process all game-specific collisions this frame.
     * 
     * @param allEntities All active entities from EntityManager
     * @param state       GameState to update (currency, score, lives)
     */
    public void processCollisions(List<Entity> allEntities, GameState state) {
        // : Separate allEntities into lists of Projectiles, Enemies, and find the
        // Server
        // : For each active projectile, check overlap with each active enemy
        // - If overlap: deal projectile damage to enemy, deactivate projectile
        // - If enemy dies: add reward to currency, add score, deactivate enemy
        // - Break after first hit (projectile only hits one enemy)
        // : For each active enemy, check overlap with server
        // - If overlap: server takes 1 damage, lose 1 life, deactivate enemy
    }
}
