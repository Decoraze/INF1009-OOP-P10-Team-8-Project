package com.p10.core.movement;

import java.util.Random;

import com.p10.core.entities.Entity;

public class AIMovement {
	private Random random = new Random();
	private float dirX;
	private float dirY;
	private float speed;
	private float changeTimer = 0;

	// "AI" movement but more like arandom value movement here
	public AIMovement() {
		this.dirX = random.nextFloat() * 2 - 1;
		this.dirY = random.nextFloat() * 2 - 1;
		this.speed = 80 + random.nextFloat() * 60;
	}

	public void update(Entity entity, float dt, float screenW, float screenH) { // added screen bounds
		changeTimer += dt;
		if (changeTimer > 1 + random.nextFloat()) {
			dirX = random.nextFloat() * 2 - 1;
			dirY = random.nextFloat() * 2 - 1;
			changeTimer = 0;
			System.out.println("[AIMovement] " + entity.getId() + " changed direction");
		}

		entity.getPosition().x += dirX * speed * dt;
		entity.getPosition().y += dirY * speed * dt;

		// Bounce off screen edges
		if (entity.getPosition().x < 0 || entity.getPosition().x > screenW) { // added screen bounds dynamic
			dirX *= -1;
			System.out.println("[AIMovement] " + entity.getId() + " bounced X");
		}
		if (entity.getPosition().y < 0 || entity.getPosition().y > screenH) { // added screen bounds
			dirY *= -1;
			System.out.println("[AIMovement] " + entity.getId() + " bounced Y");
		}
	}
}