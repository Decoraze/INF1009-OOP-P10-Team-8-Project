package com.p10.game.ai;

import com.badlogic.gdx.math.Vector2;
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
    	
    	float nx, ny;
		
        // : If enemy is dead or inactive, return false
    	
    	if (enemy.isDead() || !enemy.isActive()) {
    		return false;
    	}
    	
        // : If enemy's pathIndex >= path end, deactivate enemy and return true
    	if (enemy.getPathIndex() >= path.getWaypointCount()) {
    		enemy.setActive(false);			// Deactivate Enemy
    		return true;
    	}
    	
    	
    	
        // : Get next waypoint from path using enemy's pathIndex
    	Vector2 target = path.getWaypoint(enemy.getPathIndex());
    	
    	
    	
        // : Calculate direction vector from enemy position to waypoint
    	
    	// Get Direction Vector
    	float dx = target.x - enemy.getPosition().x;
		float dy = target.y - enemy.getPosition().y;
		
		float dist = (float) Math.sqrt(dx*dx + dy*dy);
	
    	
        // : If distance to waypoint < 5f, advance to next waypoint
		if (dist < 5f) {
			enemy.nextWaypoint();				// Increase path Index
		}
		
		
		// : Otherwise, normalize direction and move enemy at its speed * dt
		else {
			// Normalize
			nx = dx/dist;
			ny = dy/dist;
			
			// : Update enemy velocity and position
	        // though do note, we can adjust the enemy pos directly and we arent using velo
	        // for anytyhing else. set directly
			enemy.setVelocity(nx * enemy.getSpeed(), ny * enemy.getSpeed());	// TBC if needed
			enemy.getPosition().x += nx * enemy.getSpeed() * dt;				// Set x Position
			enemy.getPosition().y += ny * enemy.getSpeed() * dt;				// Set y Position		
		}
    	
		return false;
    	
    }

    public PathDefinition getPath() {
        return path;
    }
}
