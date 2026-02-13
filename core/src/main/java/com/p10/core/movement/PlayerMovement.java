package com.p10.core.movement;

import com.badlogic.gdx.Input;
import com.p10.core.entities.Entity;
import com.p10.core.interfaces.iInput;

public class PlayerMovement {
	private float speed = 150f;

	public void update(Entity entity, float dt, iInput input) {
		boolean moved = false;
		// sum for this....basically if key pressed WASD, get pos for corresponding key
		// based on the speed and delta time. then takes ID and move to the position and
		// updates
		if (input.isKeyPressed(Input.Keys.W)) {
			entity.getPosition().y += speed * dt;
			moved = true;
		}
		if (input.isKeyPressed(Input.Keys.S)) {
			entity.getPosition().y -= speed * dt;
			moved = true;
		}
		if (input.isKeyPressed(Input.Keys.A)) {
			entity.getPosition().x -= speed * dt;
			moved = true;
		}
		if (input.isKeyPressed(Input.Keys.D)) {
			entity.getPosition().x += speed * dt;
			moved = true;
		}
		if (moved)
			System.out.println("[PlayerMovement] " + entity.getId() + " moved to (" + (int) entity.getPosition().x
					+ ", " + (int) entity.getPosition().y + ")");

		/*
		 * Clamp to screen bounds
		 * float w = com.badlogic.gdx.Gdx.graphics.getWidth();
		 * float h = com.badlogic.gdx.Gdx.graphics.getHeight();
		 * entity.getPosition().x = Math.max(0, Math.min(w, entity.getPosition().x));
		 * entity.getPosition().y = Math.max(0, Math.min(h, entity.getPosition().y));
		 */
	}

}