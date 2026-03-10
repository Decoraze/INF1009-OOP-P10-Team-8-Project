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
    	Vector2 newWaypoint = new Vector2(x,y);
    	waypoints.add(newWaypoint);
    }

    
    public Vector2 getWaypoint(int index) {
        // : Return waypoint at index, or null if out of bounds
    	if (index >= 0 && index < waypoints.size()) {
    		Vector2 choosenWaypoint = waypoints.get(index);
    		return choosenWaypoint;
    	}
    	else {
    		return null;
    	}
        
    }

    public boolean isEnd(int index) {
        // : Return true if index >= waypoints size
    	if (index > waypoints.size()) {
    		return true;
    	}
    	
    	else {
    		return false;
    	}
        
    }

    public int getWaypointCount() {
        // : Return number of waypoints
        return waypoints.size();
    }

    public List<Vector2> getWaypoints() {
        // : Return a copy of the waypoints list
        return new ArrayList<>(this.waypoints);
    }

    public Vector2 getLastWaypoint() {
        // : Return the last waypoint, or (0,0) if empty
    	if (waypoints.size() > 0) {
    		return waypoints.get(waypoints.size() - 1);	
    	}
    	
    	else {
    		return new Vector2(0, 0);
    	}
    	
        
    }
}
