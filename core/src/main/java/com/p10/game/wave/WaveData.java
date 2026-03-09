package com.p10.game.wave;

/**
 * WaveData defines a single wave of enemies:
 * what type, how many, spawn timing, and difficulty multipliers.
 */
public class WaveData {
    private String enemyType;
    private int enemyCount;
    private float spawnInterval;
    private float healthMultiplier;
    private float speedMultiplier;

    /**
     * @param enemyType        One of: "DDOS", "VIRUS", "WORM", "TROJAN", "PHISHING"
     * @param enemyCount       Number of enemies in this wave
     * @param spawnInterval    Seconds between each enemy spawn
     * @param healthMultiplier Multiplier on base enemy HP (1.0 = normal)
     * @param speedMultiplier  Multiplier on base enemy speed (1.0 = normal)
     */
    public WaveData(String enemyType, int enemyCount, float spawnInterval,
            float healthMultiplier, float speedMultiplier) {
        // : Initialize all fields
    }

    public String getEnemyType() {
        return enemyType;
    }

    public int getEnemyCount() {
        return enemyCount;
    }

    public float getSpawnInterval() {
        return spawnInterval;
    }

    public float getHealthMultiplier() {
        return healthMultiplier;
    }

    public float getSpeedMultiplier() {
        return speedMultiplier;
    }
}
