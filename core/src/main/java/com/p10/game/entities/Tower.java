package com.p10.game.entities;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.p10.core.entities.CollidableEntity;

/**
 * Tower represents a network defense tool (FIREWALL, ANTIVIRUS, ENCRYPTION,
 * IDS)
 * placed on the grid to shoot at enemies.
 *
 * Each tower type has different range, fireRate, damage, and effectiveness
 * against specific enemy types (damage multiplier system).
 *
 */
public class Tower extends CollidableEntity {
    private String towerType;
    private float range;
    private float fireRate;
    private float damage;
    private float cooldownTimer;
    private com.p10.game.ai.TargetStrategy strategy;
    private Texture texture;
    // NEW FIELDS FOR TOWERS
    private static final Map<String, float[]> TOWER_STATS = new HashMap<>();// range, fireRate, damage OCP compliant
                                                                            // because just add/modify entries instead
                                                                            // of changing code logic
    static {
        TOWER_STATS.put("FIREWALL", new float[] { 140f, 0.7f, 5f });
        TOWER_STATS.put("ANTIVIRUS", new float[] { 120f, 0.9f, 8f });
        TOWER_STATS.put("ENCRYPTION", new float[] { 130f, 0.8f, 6f });
        TOWER_STATS.put("IDS", new float[] { 160f, 0.5f, 4f });
    }

    private static final Map<String, Color> TOWER_COLORS = new HashMap<>();// OCP compliant color mapping for towers,
                                                                           // can add new tower types without changing
                                                                           // code logic
    static {
        TOWER_COLORS.put("FIREWALL", Color.ORANGE);
        TOWER_COLORS.put("ANTIVIRUS", Color.GREEN);
        TOWER_COLORS.put("ENCRYPTION", Color.CYAN);
        TOWER_COLORS.put("IDS", Color.PURPLE);
    }

    private static final Map<String, Texture> TEXTURE_CACHE = new HashMap<>();// Cache to store loaded textures and
                                                                              // avoid redundant loading
    private static boolean texturesLoaded = false;// Flag to ensure textures are loaded only once
    // Static block to load textures when the class is first accessed

    private static void loadTextures() {
        if (texturesLoaded)
            return;
        for (String type : TOWER_STATS.keySet()) {
            try {
                Texture tex = new Texture("sprites/" + type.toLowerCase() + ".png");
                tex.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
                TEXTURE_CACHE.put(type, tex);
            } catch (Exception e) {
                System.out.println("Tower texture missing: " + type.toLowerCase() + ".png");
            }
        }
        texturesLoaded = true;
    }

    // get range and color for tower type, used in LevelSelectScene to show range
    // circles on hover and color-code tower options
    public static float getRangeForType(String type) {
        float[] stats = TOWER_STATS.get(type);
        return stats != null ? stats[0] : 120f;
    }

    // OCP compliant color getter for tower types, used in LevelSelectScene and
    // Tower rendering
    public static Color getColorForType(String type) {
        Color c = TOWER_COLORS.get(type);
        return c != null ? c : Color.WHITE;
    }

    // Helper function to draw range circle, can be used in both Tower and
    // LevelSelectScene (OCP)
    public static void drawRangeCircle(ShapeRenderer renderer, float cx, float cy, float range, Color color) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        renderer.setColor(color.r, color.g, color.b, 1.0f);
        renderer.circle(cx, cy, range);
        renderer.setColor(color.r, color.g, color.b, 0.3f);
        renderer.circle(cx, cy, range);
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    /**
     * @param id        Unique entity ID
     * @param x         Grid-snapped X position
     * @param y         Grid-snapped Y position
     * @param w         Hitbox width
     * @param h         Hitbox height
     * @param towerType One of: "FIREWALL", "ANTIVIRUS", "ENCRYPTION", "IDS"
     */
    public Tower(String id, float x, float y, float w, float h, String towerType) {
        super(id, x, y, w, h);
        this.towerType = towerType;
        this.cooldownTimer = 0f;
        setKinematic(true);
        this.strategy = com.p10.game.ai.TargetStrategy.NEAREST;

        // : Based on towerType, assign range, fireRate, damage values:
        // FIREWALL: range=140, fireRate=0.7, damage=5
        // ANTIVIRUS: range=120, fireRate=0.9, damage=8
        // ENCRYPTION: range=130, fireRate=0.8, damage=6
        // IDS: range=160, fireRate=0.5, damage=4
        // : Load the correct texture from sprites/ folder

        // Assign tower stats (changed from switch-case to map lookup for OCP compliance
        // and easier maintenance)
        float[] stats = TOWER_STATS.getOrDefault(towerType, new float[] { 120f, 0.8f, 5f });
        this.range = stats[0];
        this.fireRate = stats[1];
        this.damage = stats[2];

        loadTextures();
        this.texture = TEXTURE_CACHE.get(towerType);
    }

    // we moved loadTex to static block to ensure textures are loaded only once and
    // cached for all tower instances, improving performance and memory usage
    @Override
    public void update(float dt) {
        // : Sync hitbox position with entity position
        hitbox.setPosition(position.x, position.y);
        // : Tick down cooldownTimer by dt
        if (cooldownTimer > 0) {
            cooldownTimer -= dt;
        }
    }

    public boolean canFire() {
        // : Return true if cooldownTimer <= 0
        return cooldownTimer <= 0;
    }

    public void resetCooldown() {
        // : Set cooldownTimer = 1/fireRate
        cooldownTimer = 1f / fireRate;
    }

    /**
     * Returns the damage multiplier when this tower attacks a specific enemy.
     * Correct tower vs threat = 1.5x, partially effective = 0.2x, wrong tower =
     * 0.05x
     * <p>
     * Matchups:
     * FIREWALL → strong vs DDOS, WORM | weak vs VIRUS
     * ANTIVIRUS → strong vs VIRUS, TROJAN | weak vs WORM
     * ENCRYPTION → strong vs PHISHING, MITM | weak vs TROJAN
     * IDS → strong vs WORM, TROJAN | weak vs DDOS
     */
    public float getDamageMultiplier(Enemy enemy) {
        // : Implement the damage multiplier matrix above

        String attack = enemy.getAttackType();

        switch (towerType) {

            case "FIREWALL":
                if (attack.equals("DDOS") || attack.equals("WORM"))
                    return 1.5f;
                if (attack.equals("VIRUS"))
                    return 0.2f;
                break;

            case "ANTIVIRUS":
                if (attack.equals("VIRUS") || attack.equals("TROJAN"))
                    return 1.5f;
                if (attack.equals("WORM"))
                    return 0.2f;
                break;

            case "ENCRYPTION":
                if (attack.equals("PHISHING") || attack.equals("MITM"))
                    return 1.5f;
                if (attack.equals("TROJAN"))
                    return 0.2f;
                break;

            case "IDS":
                if (attack.equals("WORM") || attack.equals("TROJAN"))
                    return 1.5f;
                if (attack.equals("DDOS"))
                    return 0.2f;
                break;
        }
        return 0.05f;
    }

    public Color getTowerColor() {
        // : Return unique color per towerType
        // FIREWALL=ORANGE, ANTIVIRUS=GREEN, ENCRYPTION=CYAN, IDS=PURPLE
        return getColorForType(towerType);// instead of switch-case, use OCP compliant color getter that looks up in
                                          // map, returns white if type not found
    }

    // --- Getters/Setters ---
    public String getTowerType() {
        return towerType;
    }

    public float getRange() {
        return range;
    }

    public float getDamage() {
        return damage;
    }

    public float getFireRate() {
        return fireRate;
    }

    public com.p10.game.ai.TargetStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(com.p10.game.ai.TargetStrategy s) {
        this.strategy = s;
    }

    @Override
    public boolean checkCollision(CollidableEntity other) {
        // : Return true if hitboxes overlap
        if (other == null || other.getHitbox() == null)
            return false;

        return this.hitbox.overlaps(other.getHitbox());
    }

    @Override
    public void onCollisionEnter(CollidableEntity other) {
        // : Handle collision response (if any needed for towers)

    }

    @Override
    public void renderShapes(ShapeRenderer renderer) {
        // : If no texture, draw a colored rectangle as fallback
        if (texture == null) {

            renderer.setColor(getTowerColor());
            renderer.rect(position.x, position.y,
                    getHitbox().getWidth(),
                    getHitbox().getHeight());
        }
    }

    @Override
    public void renderTextures(SpriteBatch batch) {
        // : If texture exists, draw it centered on position
        if (texture != null) {

            batch.draw(texture,
                    position.x,
                    position.y,
                    getHitbox().getWidth(),
                    getHitbox().getHeight());
        }
    }

    public void renderRange(ShapeRenderer renderer) {
        // Get centering of tower on grid
        float centerX = getPosition().x + (getHitbox().width / 2);
        float centerY = getPosition().y + (getHitbox().height / 2);

        // draw range circle using helper function that can be reused in
        // LevelSelectScene for showing range on hover
        drawRangeCircle(renderer, centerX, centerY, this.range, getTowerColor());
    }
}
