package com.p10.game.grid;

import java.util.List;

import com.p10.core.entities.Entity;
import com.p10.game.entities.Enemy;
import com.p10.game.entities.Projectile;
import com.p10.game.entities.Server;
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

		// Loops through pairs of entities
		for (int i = 0; i < allEntities.size(); i++) {
			for (int j = i + 1; j < allEntities.size(); j++) {
				Entity e1 = allEntities.get(i);
				Entity e2 = allEntities.get(j);
				Enemy enemy = null;
				Projectile p = null;
				Server server = null;

				// Enemy VS Projectile
				if (e1 instanceof Enemy && e2 instanceof Projectile) // Cast as child class
				{
					enemy = (Enemy) e1;
					p = (Projectile) e2;
				} else if (e2 instanceof Enemy && e1 instanceof Projectile) // 2 way check
				{
					enemy = (Enemy) e2;
					p = (Projectile) e1;
				}

				if (enemy != null && p != null) // If enemy and projectile has been assigned
				{
					if (!enemy.isActive() || !p.isActive()) // If enemy/projectile not active no interaction
						continue; // just skip

					if (p.getHitbox().overlaps(enemy.getHitbox())) // Projectile hit enemy
					{
						enemy.takeDamage(p.getDamage());
						p.setActive(false);

						if (enemy.isDead()) // When health <= 0 deactivate enemy and grant rewards
						{
							enemy.setActive(false);
							state.addCurrency(enemy.getReward());
							state.addScore(1);
						}
					}
				}

				// Enemy VS Server
				if (e1 instanceof Enemy && e2 instanceof Server) // Case as child class
				{
					enemy = (Enemy) e1;
					server = (Server) e2;
				} else if (e2 instanceof Enemy && e1 instanceof Server) // 2 way check
				{
					enemy = (Enemy) e2;
					server = (Server) e1;
				}

				if (enemy != null && server != null) // If enemy and server has been assigned
				{
					if (!enemy.isActive()) // Same as above but no server check
						continue; // go next iteration

					if (enemy.getHitbox().overlaps(server.getHitbox())) {
						server.takeDamage(1);
						state.loseLife(); // ADD: lose 1 life per enemy reaching server
						enemy.setActive(false);

						if (server.isDestroyed()) // When health <= 0 deactivate server and end game
						{
							server.setActive(false);
							state.setLives(0); // Game over scene
							break;
						}
					}
					// continue;deleted because unessary as loop will end after this iteration
					// anyway and we want to check all pairs of entities
				}
			}
		}
	}
}
