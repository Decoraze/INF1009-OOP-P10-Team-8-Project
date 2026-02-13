package com.p10.core.entities;

import java.util.ArrayList;
import java.util.List;

public class CollisionDetection {
	private List<CollidableEntity> collidables = new ArrayList<>();// init list else will be treated as null without
																	// making new list. first time if this is called,
																	// (collidables.add(e) it will become a null pointer
																	// crash)

	public void addCollidable(CollidableEntity e) {
		collidables.add(e);
	}

	public void removeCollidable(String id) {
		for (Entity e : collidables) {
			if (id.equals(e.getId())) // changed to .equal to compare between actual string content rather than memory
										// addresses (== is memory address)
			{
				collidables.remove(e);
				break;
			}
		}
	}

	public List<CollidableEntity> detectCollisions() {
		List<CollidableEntity> collided = new ArrayList<>();
		for (int i = 0; i < collidables.size(); i++) {
			for (int j = 0; j < collidables.size(); j++) {// should start from j = i + 1 to avoid checking the same pair
															// in index 0. but works
				// if same entity just skip
				if (collidables.get(j).getId().equals(collidables.get(i).getId())) {
					continue;
				}
				// if collided send pair to collision response (?)
				if (collidables.get(i).getHitbox().overlaps(collidables.get(j).getHitbox())) {
					collided.add(collidables.get(i));
					collided.add(collidables.get(j));
				}
			}
		}
		return new ArrayList<CollidableEntity>(collided);
	}
}
