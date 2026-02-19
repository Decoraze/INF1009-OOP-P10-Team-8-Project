package com.p10.core.movement;

import com.badlogic.gdx.Input;
import com.p10.core.entities.Entity;
import com.p10.core.interfaces.iInput;

public class PlayerMovement {
	private float speed = 150f;

	public void update(Entity entity, float dt, iInput input) {
		float vx = 0;
		float vy = 0;

		if (input.isKeyPressed(Input.Keys.W))
			vy += speed;
		if (input.isKeyPressed(Input.Keys.S))
			vy -= speed;
		if (input.isKeyPressed(Input.Keys.A))
			vx -= speed;
		if (input.isKeyPressed(Input.Keys.D))
			vx += speed;

		entity.setVelocity(vx, vy);
		entity.getPosition().x += vx * dt;
		entity.getPosition().y += vy * dt;

		if (vx != 0 || vy != 0)
			System.out.println("[PlayerMovement] " + entity.getId() + " moved to ("
					+ (int) entity.getPosition().x + ", " + (int) entity.getPosition().y + ")");
	}

}