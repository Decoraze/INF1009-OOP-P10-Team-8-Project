package com.p10.core.movement;

import java.util.Random;

import com.p10.core.entities.Entity;

public class AIMovement {
	private Random random = new Random();
	private float dirX;
	private float dirY;
	private float speed;
	private float baseSpeed; // basespeed is used to store the original speed to go back to
	// private float changeTimer = 0;
	private float boostTimer = 0; // tracks how long the accel lasts to go away from the player (this is mainly to
									// fix a little big of the visuals thoguh a better approach will be nice)

	// "AI" movement but more like arandom value movement here
	public AIMovement() {
		this.dirX = random.nextFloat() * 2 - 1;
		this.dirY = random.nextFloat() * 2 - 1;
		this.baseSpeed = 80 + random.nextFloat() * 60;// same as before kinda but we use this now to temporaily boost
														// the entity
		this.speed = baseSpeed;
	}

	// explanation for below:
	/*
	 * 1. Called by movement manager after CollisionManager detects a Collision
	 * 2. Checks the axis the collision is stronger on which, y or x.
	 * 3. Forces the direction away from the collision
	 * 4. sets speed to 3x for 0.3 to see the acceleration so that the player will
	 * stop jittering by catching up to the moving object (temp fix)
	 * note: the use of Math.abs is used here to make sure that the entity moves in
	 * the same direction as the collision normal which is always away
	 */
	public void reflect(float nx, float ny) {
		// Reflect both axes based on the collision normal
		dirX = (nx > 0) ? Math.abs(dirX) : (nx < 0) ? -Math.abs(dirX) : dirX;
		dirY = (ny > 0) ? Math.abs(dirY) : (ny < 0) ? -Math.abs(dirY) : dirY;
		speed = baseSpeed * 3;
		boostTimer = 0.3f;
	}

	public void update(Entity entity, float dt, float screenW, float screenH) { // added screen bounds
		// random direction removed (this was the invis wall so mb so added the accel
		// part to make sure animations look smooth again this is a temp fix and feature
		// of purely the demo version this isnt abstract i think)
		if (boostTimer > 0) {
			boostTimer -= dt;
			if (boostTimer <= 0) {
				speed = baseSpeed;
			}
		}
		/*
		 * if (changeTimer > 1 + random.nextFloat()) {
		 * dirX = random.nextFloat() * 2 - 1;
		 * dirY = random.nextFloat() * 2 - 1;
		 * changeTimer = 0;
		 * System.out.println("[AIMovement] " + entity.getId() + " changed direction");
		 * }
		 */

		// Bounce off screen edges
		entity.getPosition().x += dirX * speed * dt;
		entity.getPosition().y += dirY * speed * dt;

		// Bounce off screen edges & sets the position to the boundary rather than
		// anything after the edge. this math.abs is used also to prevent a double
		// inversion when using applicationCore's clamp
		if (entity.getPosition().x < 0) {
			entity.getPosition().x = 0;
			dirX = Math.abs(dirX);
		}
		if (entity.getPosition().x > screenW) {
			entity.getPosition().x = screenW;
			dirX = -Math.abs(dirX);
		}
		if (entity.getPosition().y < 0) {
			entity.getPosition().y = 0;
			dirY = Math.abs(dirY);
		}
		if (entity.getPosition().y > screenH) {
			entity.getPosition().y = screenH;
			dirY = -Math.abs(dirY);
		}
	}
}