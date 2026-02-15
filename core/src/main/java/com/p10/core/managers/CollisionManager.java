package com.p10.core.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.p10.core.entities.CollidableEntity;
import com.p10.core.entities.CollisionDetection;
import com.p10.core.entities.CollisionResponse;
import com.p10.core.interfaces.iCollision;//added interfaced. 
import com.p10.core.interfaces.iMovement;

/**
 * CollisionManager - Handles collision detection
 * 1
 */
public class CollisionManager implements iCollision {
    private CollisionDetection detector;// we didnt have collision detector and response before because of a temporary
                                        // demo solution prior.
    private CollisionResponse responder;// added these to decouple the detection response and detector from the detector
                                        // itself
    private java.util.Map<String, Float> collisionCooldowns = new java.util.HashMap<>();
    private static final float COOLDOWN = 1.0f; // 1 second between logs
    private iMovement movement;// we are using iMovement not implementing it so we call its methods that
                               // iMovement provide which goes back to MovementManager. this is used because of
                               // the bouncing you see on the screen we need this because of the bounce we ar
                               // eusing in reflect

    public CollisionManager(iMovement movement) {
        this.detector = new CollisionDetection();
        this.responder = new CollisionResponse();
        this.movement = movement;
        System.out.println("[CollisionManager] Initialized with detector and responder");
    }
    
    public void logCollisions(CollidableEntity e1, CollidableEntity e2, String key) {
    	if (!collisionCooldowns.containsKey(key)) {
            System.out.println("[CollisionManager] Collision detected: " + e1.getId() + " <-> " + e2.getId());
            collisionCooldowns.put(key, COOLDOWN);
    	}
    	
    }
    
    public void checkCollisions(List<CollidableEntity> collidableEntities) {
        // Step 1: Update cooldowns
        List<String> expired = new ArrayList<>();
        for (java.util.Map.Entry<String, Float> entry : collisionCooldowns.entrySet()) {
            entry.setValue(entry.getValue() - 0.016f);
            if (entry.getValue() <= 0)
                expired.add(entry.getKey());
        }
        for (String key : expired)
            collisionCooldowns.remove(key);

        // Step 2: Detect colliding pairs (delegated to CollisionDetection)
        List<CollidableEntity[]> pairs = detector.detectCollisions(collidableEntities);

        // Step 3: For each pair — notify + resolve
        for (CollidableEntity[] pair : pairs) {
            CollidableEntity e1 = pair[0];
            CollidableEntity e2 = pair[1];

            String key = e1.getId() + "<->" + e2.getId();
            logCollisions(e1, e2, key);
            
            e1.onCollisionEnter(e2);
            e2.onCollisionEnter(e1);

            // explanation for below part
            /*
             * find the dx and dy where which parts are colliding (0).
             * e.g. e1(300,200) e2(250, 200) then dx = 50 dy = 0. thus +50 pixels to the
             * right negative will be left (graph)
             * dist is the straight line distance between them. pythagoras theorum. where we
             * root the x2 and y2 to get c.
             * so we normalise the direction with this, after dividing x and y by the
             * distance, this scales the direction to length to 1. Thus, making it say
             * "oh this interaction abovve means the collision happenedf along the X axis pointing right from e2 to e1"
             * e1 will bounce away using movement.reverse.... in the direction of the normal
             * away from e2. e.g. E1 gets (1,0) which means go right
             * e2 will recieve (-1,0) which is go left. if (dist > 0) this makes sure that
             * the distance wont happen to become a divide by 0 error due to the divison of
             * the normalisation
             * 
             */

            // Reflect ONCE — billiard bounce
            float dx = e1.getPosition().x - e2.getPosition().x;
            float dy = e1.getPosition().y - e2.getPosition().y;
            float dist = (float) Math.sqrt(dx * dx + dy * dy);
            if (dist > 0) {
                float nx = dx / dist;
                float ny = dy / dist;
                movement.reverseDirection(e1.getId(), nx, ny);
                movement.reverseDirection(e2.getId(), -nx, -ny);
            }
            // Push apart every frame they overlap
            responder.resolveCollision(e1, e2);
        }
    }

    // this should be removed (?) should exist in detection instead (?)
    public boolean checkCollision(CollidableEntity e1, CollidableEntity e2) {
        return e1.getHitbox().overlaps(e2.getHitbox());
    }

    public void dispose() {
        System.out.println("[CollisionManager] Stub disposed");
    }
}