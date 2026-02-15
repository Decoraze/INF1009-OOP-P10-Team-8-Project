package com.p10.core.entities;

public class CollisionResponse {
    public void resolveCollision(CollidableEntity e1, CollidableEntity e2) {
        float pushStrength = 8.0f;// this one can play around ngl to fit the code. (will try to find another
                                  // method to allow devs to fine tune.)
        float dx = e1.getPosition().x - e2.getPosition().x;
        float dy = e1.getPosition().y - e2.getPosition().y;
        float dist = (float) Math.sqrt(dx * dx + dy * dy);
        if (dist > 0) { // fixed this as it was inverted . the bug crashed the game. when this runs with
                        // <=, if distance is 0 it will divide by 0 and skips if positive. by using > we
                        // can make sure the collision happens and preventing a divide by 0 error if we
                        // do use >= 0
            float nx = dx / dist;
            float ny = dy / dist;
            // these here are the kinematic checks (stuff is in entity)
            // kinematics is basically if the entity is a kinematic entity like the player,
            // it doesnt get pushed
            // we use += and -= here for entity 1 and entity 2 to ensure they go opposite
            // directions like paul walker and vin disel
            if (!e1.isKinematic()) {
                e1.getPosition().x += nx * pushStrength;
                e1.getPosition().y += ny * pushStrength;
            }
            if (!e2.isKinematic()) {
                e2.getPosition().x -= nx * pushStrength;
                e2.getPosition().y -= ny * pushStrength;
            }
        }
    }
}
