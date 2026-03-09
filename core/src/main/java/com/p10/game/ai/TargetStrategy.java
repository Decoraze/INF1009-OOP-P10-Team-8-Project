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
        // : For NEAREST — find enemy with smallest distance to tower
        // : For STRONGEST — find enemy with highest health
        // : For FIRST — find enemy with highest pathIndex (furthest along path)
        return null;
    }

    private static float dst(float x1, float y1, float x2, float y2) {
        // : Calculate Euclidean distance between two points
        return 0f;
    }
}
