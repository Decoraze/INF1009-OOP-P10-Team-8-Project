package com.p10.game.ai;

import java.util.List;

import com.p10.core.interfaces.iEntityOps;
import com.p10.game.entities.Enemy;
import com.p10.game.entities.Tower;

/**
 * TowerAI handles tower targeting and firing logic.
 * Each frame: find enemies in range, pick target via TargetStrategy,
 * fire a Projectile if cooldown is ready.
 *
 */
public class TowerAI {
    private int projectileCounter = 0;

    /**
     * Update a single tower — find targets, fire projectiles.
     * 
     * @param tower      The tower to update
     * @param allEnemies All active enemies in the game
     * @param dt         Delta time
     * @param entityOps  Engine interface to add new entities (projectiles)
     */
    public void update(Tower tower, List<Enemy> allEnemies, float dt, iEntityOps entityOps) {
        // : If tower is inactive, return
        // : Call tower.update(dt) to tick cooldown
        // : Build list of enemies within tower's range (use distance check)
        // : If no enemies in range, return
        // : Use TargetStrategy.pickTarget() to select best target
        // : If tower canFire(), create a Projectile aimed at target
        // - Calculate direction vector from tower to target
        // - Apply tower.getDamageMultiplier(target) to tower.getDamage()
        // - Add projectile via entityOps.addEntity()
        // - Reset tower cooldown
    }

    private float dst(float x1, float y1, float x2, float y2) {
        // : Euclidean distance
        return 0f;
    }
}
