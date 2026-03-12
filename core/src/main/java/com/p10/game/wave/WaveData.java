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
        this.enemyType = enemyType;
        this.enemyCount = enemyCount;
        this.spawnInterval = spawnInterval;
        this.healthMultiplier = healthMultiplier;
        this.speedMultiplier = speedMultiplier;
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

    public static WaveData ddosWave(int wave, int count, float hpMult, float spdMult) {
        return new WaveData("DDOS", count, 0.8f, hpMult, spdMult);
    }

    public static WaveData virusWave(int wave, int count, float hpMult, float spdMult) {
        return new WaveData("VIRUS", count, 1.5f, hpMult, spdMult);
    }

    public static WaveData phishingWave(int wave, int count, float hpMult, float spdMult) {
        return new WaveData("PHISHING", count, 0.6f, hpMult, spdMult);
    }

    public static WaveData trojanWave(int wave, int count, float hpMult, float spdMult) {
        return new WaveData("TROJAN", count, 2.0f, hpMult, spdMult);
    }

    public static WaveData wormWave(int wave, int count, float hpMult, float spdMult) {
        return new WaveData("WORM", count, 1.0f, hpMult, spdMult);
    }
}
