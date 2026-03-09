package com.p10.game.ai;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

/**
 * PathDefinition stores an ordered list of waypoints that enemies follow.
 * Built by GridManager from the level's grid layout.
 *
 */
public class PathDefinition {
    private List<Vector2> waypoints;

    public PathDefinition() {
        this.waypoints = new ArrayList<>();
    }

    public void addWaypoint(float x, float y) {
        // : Add a new Vector2 waypoint to the list
    }

    public Vector2 getWaypoint(int index) {
        // : Return waypoint at index, or null if out of bounds
        return null;
    }

    public boolean isEnd(int index) {
        // : Return true if index >= waypoints size
        return false;
    }

    public int getWaypointCount() {
        // : Return number of waypoints
        return 0;
    }

    public List<Vector2> getWaypoints() {
        // : Return a copy of the waypoints list
        return new ArrayList<>();
    }

    public Vector2 getLastWaypoint() {
        // : Return the last waypoint, or (0,0) if empty
        return new Vector2(0, 0);
    }
}
