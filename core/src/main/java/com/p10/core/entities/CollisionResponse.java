package com.p10.core.entities;

public class CollisionResponse {
    public void resolveCollision(CollidableEntity e1, CollidableEntity e2) {
        float dx = e1.getPosition().x - e2.getPosition().x;
        float dy = e1.getPosition().y - e2.getPosition().y;
        float dist = (float) Math.sqrt(dx * dx + dy * dy);

        if (dist > 0) {
            float nx = dx / dist;
            float ny = dy / dist;

            // Relative velocity along collision normal
            float dvx = e1.getVelocity().x - e2.getVelocity().x;
            float dvy = e1.getVelocity().y - e2.getVelocity().y;
            float relativeSpeed = dvx * nx + dvy * ny;

            // Only resolve if entities are moving toward each other
            if (relativeSpeed > 0) return;

            float totalMass = e1.getMass() + e2.getMass();
            float e1Ratio = e2.getMass() / totalMass;
            float e2Ratio = e1.getMass() / totalMass;

            // Apply velocity-based impulse
            if (!e1.isKinematic()) {
                e1.getVelocity().x -= relativeSpeed * nx * e1Ratio;
                e1.getVelocity().y -= relativeSpeed * ny * e1Ratio;
            }
            if (!e2.isKinematic()) {
                e2.getVelocity().x += relativeSpeed * nx * e2Ratio;
                e2.getVelocity().y += relativeSpeed * ny * e2Ratio;
            }

            // Position separation (prevent overlap)
            float pushStrength = 2.0f;
            if (!e1.isKinematic()) {
                e1.getPosition().x += nx * pushStrength * e1Ratio;
                e1.getPosition().y += ny * pushStrength * e1Ratio;
            }
            if (!e2.isKinematic()) {
                e2.getPosition().x -= nx * pushStrength * e2Ratio;
                e2.getPosition().y -= ny * pushStrength * e2Ratio;
            }
        }
    }
    
}
