package com.p10.core.entities;

import java.util.ArrayList;
import java.util.List;

public class CollisionDetection {
	// DELETED: private List<CollidableEntity> collidables = new ArrayList<>();
	// DELETED: public void addCollidable(CollidableEntity e) { collidables.add(e);
	// }
	// DELETED: public void removeCollidable(String id) { ... }
	// made it simpler by decoupling (check with your branch see diff lmk la haha)
	public List<CollidableEntity[]> detectCollisions(List<CollidableEntity> collidables) {// get collidable lists from
																							// collision managaer
																							// detection just does the
																							// math SRP
		List<CollidableEntity[]> pairs = new ArrayList<>();
		for (int i = 0; i < collidables.size(); i++) {
			for (int j = i + 1; j < collidables.size(); j++) {
				if (collidables.get(i).getHitbox().overlaps(collidables.get(j).getHitbox())) {
					pairs.add(new CollidableEntity[] { collidables.get(i), collidables.get(j) });
				}
			}
		}
		return pairs;// instead of using two .get and add methods, we combined them to have a array
						// returning back to the function. this is cleaner as its a flat list rather
						// than individually
	}
}