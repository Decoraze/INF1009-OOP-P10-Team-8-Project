package com.p10.game.ai;

import java.util.List;

import com.p10.game.entities.Enemy;

/**
 * TargetStrategy enum defines how a tower picks its target from enemies in
 * range.
 * NEAREST = closest enemy, STRONGEST = highest HP, FIRST = furthest along path.
 *
 */
public enum TargetStrategy {
    NEAREST,
    STRONGEST,
    FIRST;

    /**
     * Pick the best target from enemies in range based on the strategy.
     * 
     * @param inRange  List of enemies within tower's range
     * @param strategy Which strategy to use
     * @param towerX   Tower's X position
     * @param towerY   Tower's Y position
     * @return The best target, or null if list is empty
     */
	
	
    public static Enemy pickTarget(List<Enemy> inRange, TargetStrategy strategy, float towerX, float towerY) {
        // : If list is empty, return null
    	
    	if (inRange.size() > 0) {
    		
    		Enemy pickedEnemy = inRange.get(0); 		// To ensure that it will always return an enemy from the list
    		
    		
    		 // : For NEAREST — find enemy with smallest distance to tower
        	switch (strategy) {
        		case NEAREST:
        			float dist = 10000;			// To put value that is furthest away from each other
        			
        			// Loop List
        			for (int i=0; i < inRange.size();i++) {
        				Enemy curEnemy = inRange.get(i);																					// Store Current enemy
        				float calDist = dst(curEnemy.getPosition().x, curEnemy.getPosition().y, towerX, towerY);							// Calculate distance from enemy to tower
        				
        				
        				// If the current enemy distance is lower then one currently stored
        				if (calDist < dist) {	
        					pickedEnemy = curEnemy;
        					dist = calDist;
        				}
        			}
        			
        			break;
        			
        			
        			
    			// : For STRONGEST — find enemy with highest health
        		case STRONGEST:
        			float highestHealth = 0;
        			
        			// Loop List
        			for (int i=0; i < inRange.size();i++) {
        				Enemy curEnemy = inRange.get(i);																					// Store Current enemy
        				
        				// If the current enemy health is higher then one currently stored
        				if (curEnemy.getHealth() > highestHealth) {	
        					pickedEnemy = curEnemy;
        					highestHealth = curEnemy.getHealth();
        				}
        			}
        			
        			break;
        		
        			
    			 // : For FIRST — find enemy with highest pathIndex (furthest along path)	
        		case FIRST:
        			int highestPathindex = 0;
        			
        			// Loop List
        			for (int i=0; i < inRange.size();i++) {
        				Enemy curEnemy = inRange.get(i);																					// Store Current enemy
        			
        				
        				// If the current enemy pathIndex is higher then one currently stored
        				if (curEnemy.getPathIndex() > highestPathindex) {	
        					pickedEnemy = curEnemy;
        					highestPathindex = curEnemy.getPathIndex();
        				}
        			}

        			break;
   
        	}
        	
        	return pickedEnemy;
        
    	}
    	
        return null;			// Else just return null
    }

    private static float dst(float x1, float y1, float x2, float y2) {
        // : Calculate Euclidean distance between two points
    	
    	float diffx = x2 - x1;
    	float diffy = y2 - y1;
    	
        return (float) Math.sqrt(diffx * diffx + diffy * diffy);
    }
}
