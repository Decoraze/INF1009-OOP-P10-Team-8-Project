package com.p10.game.ai;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.p10.core.interfaces.iEntityOps;
import com.p10.game.entities.Enemy;
import com.p10.game.entities.Projectile;
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
		if (!tower.isActive()) {
			return; // Skip rest of code
		}

		// : Call tower.update(dt) to tick cooldown
		tower.update(dt);

		// : Build list of enemies within tower's range (use distance check)

		List<Enemy> enemywithinRange = new ArrayList<>(); // Stores enemies that are within tower range

		// Loop List
		for (int i = 0; i < allEnemies.size(); i++) {
			Enemy curEnemy = allEnemies.get(i); // Store Current enemy

			// Calculate Distance
			float dx = tower.getPosition().x - curEnemy.getPosition().x;
			float dy = tower.getPosition().y - curEnemy.getPosition().y;

			float dist = (float) Math.sqrt(dx * dx + dy * dy);

			// Check if enemy is within range, if within then add them to the list
			if (dist <= tower.getRange()) {
				enemywithinRange.add(curEnemy);
			}
		}

		// : If no enemies in range, return
		if (enemywithinRange.size() < 1) {
			return;
		}

		// : Use TargetStrategy.pickTarget() to select best target

		Enemy targetEnemy = TargetStrategy.pickTarget(enemywithinRange, tower.getStrategy(), tower.getPosition().x,
				tower.getPosition().y);

		// : If tower canFire(), create a Projectile aimed at target

		if (tower.canFire()) {

			// - Calculate direction vector from tower to target
			Vector2 direction = new Vector2(targetEnemy.getPosition()).sub(tower.getPosition());

			// - Apply tower.getDamageMultiplier(target) to tower.getDamage()
			float totalDamage = tower.getDamage() * tower.getDamageMultiplier(targetEnemy); // Base damage * Multiplier

			// Create Projectile Class

			Projectile projectile = new Projectile("AI-projectile -" + tower.getId(), tower.getPosition().x,
					tower.getPosition().y, direction, totalDamage);// changed from .getDamage() to totalDamage

			// - Add projectile to entity manager via entityOps.addEntity()
			entityOps.addEntity(projectile);
			// - Reset tower cooldown
			tower.resetCooldown();
		}

	}
}
