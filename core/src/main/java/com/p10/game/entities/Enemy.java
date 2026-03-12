package com.p10.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.p10.core.entities.CollidableEntity;
import com.p10.game.entities.Tower;

/**
 * Enemy represents a network threat (VIRUS, WORM, TROJAN, DDOS, PHISHING)
 * that moves along a path toward the server.
 *
 * Extends CollidableEntity from the Abstract Engine.
 *
 */
public class Enemy extends CollidableEntity {
    private float health;
    private float maxHealth;
    private float speed;
    private String attackType;
    private int reward;
    private int pathIndex;
    private Texture texture;

    // Static texture cache — load once, reuse for all enemies
    private static Texture texVirus, texWorm, texTrojan, texDdos, texPhishing; // added texDdos for completeness
    private static boolean texturesLoaded = false;

    private static void loadTextures() {
        // : Load textures from sprites/ folder for each enemy type
        // Use try-catch in case textures are missing
        // Set texturesLoaded = true after loading
        if (texturesLoaded)
            return;

        try {
            texVirus = new Texture("sprites/virus.png");
        } catch (Exception e) {
            System.out.println("Virus texture missing");
        }

        try {
            texWorm = new Texture("sprites/worm.png");
        } catch (Exception e) {
            System.out.println("Worm texture missing");
        }

        try {
            texTrojan = new Texture("sprites/trojan.png");
        } catch (Exception e) {
            System.out.println("Trojan texture missing");
        }

        try {
            texPhishing = new Texture("sprites/phishing.png");
        } catch (Exception e) {
            System.out.println("Phishing texture missing");
        }
        try {// added texDdos for completeness
            texDdos = new Texture("sprites/ddos.png");
        } catch (Exception e) {
            System.out.println("DDOS texture missing");
        }

        texturesLoaded = true;
    }

    /**
     * @param id         Unique entity ID
     * @param x          Spawn X position
     * @param y          Spawn Y position
     * @param size       Hitbox size (square)
     * @param attackType One of: "VIRUS", "WORM", "TROJAN", "DDOS", "PHISHING"
     * @param health     Starting HP
     * @param speed      Movement speed (pixels/sec)
     * @param reward     Currency earned when killed
     */
    public Enemy(String id, float x, float y, float size,
            String attackType, float health, float speed, int reward) {
        super(id, x, y, size, size);
        this.attackType = attackType;
        this.health = health;
        this.maxHealth = health;
        this.speed = speed;
        this.reward = reward;
        this.pathIndex = 0;
        setKinematic(true); // enemies follow AI path — engine collision bounce must be skipped

        // : Call loadTextures() and assign the correct texture based on attackType
        loadTextures();

        switch (attackType) {
            case "VIRUS":
                texture = texVirus;
                break;

            case "WORM":
                texture = texWorm;
                break;

            case "TROJAN":
                texture = texTrojan;
                break;

            case "PHISHING":
                texture = texPhishing;
                break;
            case "DDOS":// added case for DDOS
                texture = texVirus;// reuse texture for now since we don't have a specific one, but ideally should
                                   // have its own texture
                break;
            default:
                texture = null;
        }
    }

    @Override
    public void update(float dt) {
        // : Sync hitbox center with current position
        hitbox.setPosition(position.x, position.y);
    }

    public void takeDamage(float amount) {
        // : Reduce health by amount, clamp to 0
        health -= amount;

        if (health < 0) {
            health = 0;
        }
    }

    public boolean isDead() {
        // : Return true if health <= 0
        return health <= 0;
    }

    public void nextWaypoint() {
        // : Increment pathIndex
        pathIndex++;
    }

    // --- Getters ---
    public float getHealth() {
        return health;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public float getSpeed() {
        return speed;
    }

    public String getAttackType() {
        return attackType;
    }

    public int getReward() {
        return reward;
    }

    public int getPathIndex() {
        return pathIndex;
    }

    public void setPathIndex(int i) {
        this.pathIndex = i;
    }

    @Override
    public boolean checkCollision(CollidableEntity other) {
        // : Return true if this entity's hitbox overlaps with other's hitbox
        // Enemies only physically collide with Server — towers use range-based targeting
        if (other == null || other.getHitbox() == null)
            return false;
        // Skip collision with towers — enemies walk past them, not into them
        if (other instanceof Tower)
            return false;
        return hitbox.overlaps(other.getHitbox());
    }

    @Override
    public void onCollisionEnter(CollidableEntity other) {
        // : Handle collision response (if any needed for enemies)
    }

    @Override
    public void renderShapes(ShapeRenderer renderer) {
        // : Draw health bar above enemy (background bar + colored fill based on HP
        // ratio)
        // : If no texture, draw a colored circle as fallback (use getEnemyColor())
        float barWidth = hitbox.getWidth();
        float barHeight = 4f;

        float healthRatio = maxHealth == 0 ? 0 : health / maxHealth;

        float barX = position.x;
        float barY = position.y + hitbox.getHeight() + 4;

        // health bar background
        renderer.setColor(Color.DARK_GRAY);
        renderer.rect(barX, barY, barWidth, barHeight);

        // health bar fill
        renderer.setColor(Color.GREEN);
        renderer.rect(barX, barY, barWidth * healthRatio, barHeight);

        // fallback if texture missing
        if (texture == null) {

            renderer.setColor(getEnemyColor());

            renderer.circle(
                    position.x + hitbox.getWidth() / 2,
                    position.y + hitbox.getHeight() / 2,
                    hitbox.getWidth() / 2);
        }
    }

    private Color getEnemyColor() {
        // : Return a unique color per attackType
        // VIRUS=RED, WORM=LIME, TROJAN=MAROON, DDOS=YELLOW, PHISHING=MAGENTA
        switch (attackType) {

            case "VIRUS":
                return Color.RED;

            case "WORM":
                return Color.LIME;

            case "TROJAN":
                return Color.MAROON;

            case "DDOS":
                return Color.YELLOW;

            case "PHISHING":
                return Color.MAGENTA;

            default:
                return Color.WHITE;
        }
    }

    @Override
    public void renderTextures(SpriteBatch batch) {
        // : If texture exists, draw it centered on position
        if (texture != null) {

            batch.draw(
                    texture,
                    position.x,
                    position.y,
                    hitbox.getWidth(),
                    hitbox.getHeight());
        }
    }
}
