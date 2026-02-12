package com.p10.core.entities;

import java.util.ArrayList;
import java.util.List;

public abstract class CollisionDetection {
	private List<CollidableEntity> collidables;

	public void addCollidable(CollidableEntity e) {
    	collidables.add(e);
	}
	
	public void removeCollidable(String id) {
		for(Entity e : collidables)
    	{
    		if(id == e.getId())
    		{
    	    	collidables.remove(e);
    	    	break;
    		}
    	}
	}
	
	public List<CollidableEntity> detectCollisions() {
		List<CollidableEntity> collided = new ArrayList<>();
		for (int i = 0; i < collidables.size(); i++)
		{
			for(int j = 0; j < collidables.size(); j++)
			{
				// if same entity just skip
				if (collidables.get(j).getId() == collidables.get(i).getId())
				{
					continue;
				}
				// if collided send pair to collision response (?)
				if (collidables.get(i).getHitbox().overlaps(collidables.get(j).getHitbox()))
				{
					collided.add(collidables.get(i));
					collided.add(collidables.get(j));
				}
			}
		}
		return new ArrayList<CollidableEntity>(collided);
	}
}
