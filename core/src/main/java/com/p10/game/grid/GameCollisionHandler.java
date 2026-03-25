package com.p10.game.grid;

import java.util.ArrayList;
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
	public Server sortEntities(List<Entity> all, List<Projectile> projs, List<Enemy> enys) {
		Server foundServer = null;
		for (Entity e : all) // Loops through all Entities to check individual instances
		{
			if (e instanceof Projectile)
				projs.add((Projectile) e); // Downcast and separate to respective lists accordingly
			else if (e instanceof Enemy)
				enys.add((Enemy) e);
			else if (e instanceof Server)
				foundServer = (Server) e; // If a server is one of the instances set variable to return
		}
		return foundServer; // Later used in main function to set Server instance
	}

	public void enemyVsProjectile(List<Enemy> enys, List<Projectile> projs, GameState state) {
		for (Enemy e : enys) {
			if (!e.isActive())
				continue; // If Enemy is not active

			for (Projectile p : projs) // Nested loop to check each Enemy against each Projectile
			{
				if (!p.isActive())
					continue; // If Projectile is not active

				if (p.getHitbox().overlaps(e.getHitbox())) // When Projectile hits Enemy
				{
					e.takeDamage(p.getDamage()); // Enemy takes damage according to Projectile damage
					p.setActive(false); // Projectile is destroyed on first contact of an Enemy
					if (e.isDead()) // If Enemy is dead
					{
						e.setActive(false); // Remove Enemy from screen
						state.addCurrency(e.getReward()); // Reward player accordingly
						state.addScore(1); // Update player score accordingly
					}
					break; // Projectile only hits one enemy
				}
			}
		}
	}

	public void enemyVsServer(List<Entity> all, List<Enemy> enys, Server server, GameState state) {
		if (server == null || !server.isActive())
			return; // If server is destroyed / is not active
		for (Enemy e : enys) {
			if (!e.isActive())
				continue; // If enemy not active check next in list

			if (e.getHitbox().overlaps(server.getHitbox())) // When Enemy hits Server
			{
				server.takeDamage(1); // Server takes damage
				state.loseLife(); // Server lose life accordingly
				e.setActive(false); // Enemy gets destroyed

				if (server.isDestroyed()) // When health <= 0 deactivate server and end game
				{
					deactivateAllEntities(all); // Clear all relevant entities
					state.setLives(0); // Game Over Scene
					break;
				}
			}
		}
	}

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
		List<Projectile> projectiles = new ArrayList<>(); // Individual lists for each sub Entity type
		List<Enemy> enemies = new ArrayList<>();

		// sortEntities returns Server after sorting all entities list
		Server server = sortEntities(allEntities, projectiles, enemies);

		// Handle respective entities collision
		enemyVsProjectile(enemies, projectiles, state);
		enemyVsServer(allEntities, enemies, server, state);
	}

	public void deactivateAllEntities(List<Entity> allEntities) {
		for (int i = 0; i < allEntities.size(); i++) {
			allEntities.get(i).setActive(false);
		}
	}
}
