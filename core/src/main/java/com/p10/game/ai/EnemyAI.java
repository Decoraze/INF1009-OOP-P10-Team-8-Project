package com.p10.game.ai;

import com.p10.game.entities.Enemy;

/**
 * EnemyAI controls enemy movement along a predefined waypoint path.
 * Each frame, it moves the enemy toward the next waypoint.
 * When the enemy reaches the final waypoint, it signals the server should take
 * damage.
 *
 */
public class EnemyAI {
    private PathDefinition path;

    public EnemyAI(PathDefinition path) {
        this.path = path;
    }

    /**
     * Move the enemy along the path.
     * 
     * @param enemy The enemy to move
     * @param dt    Delta time
     * @return true if enemy reached the end of the path (server takes damage)
     */
    public boolean update(Enemy enemy, float dt) {
        // : If enemy is dead or inactive, return false
        // : If enemy's pathIndex >= path end, deactivate enemy and return true
        // : Get next waypoint from path using enemy's pathIndex
        // : Calculate direction vector from enemy position to waypoint
        // : If distance to waypoint < 5f, advance to next waypoint
        // : Otherwise, normalize direction and move enemy at its speed * dt
        // : Update enemy velocity and position
        // though do note, we can adjust the enemy pos directly and we arent using velo
        // for anytyhing else. set directly
        return false;
    }

    public PathDefinition getPath() {
        return path;
    }
}
