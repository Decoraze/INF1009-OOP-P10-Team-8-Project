package com.p10.game.wave;

import java.util.List;

import com.p10.core.interfaces.iEntityOps;
import com.p10.game.ai.PathDefinition;
import com.p10.game.entities.Enemy;

/**
 * WaveManager controls enemy spawning across multiple waves.
 * Each wave spawns enemies at intervals. When all enemies in a wave are dead,
 * transitions back to prep phase for the next wave.
 *
 */
public class WaveManager {
    private List<WaveData> waves;
    private int currentWaveIndex;
    private int enemiesSpawned;
    private float spawnTimer;
    private int enemyCounter;
    private boolean allWavesDone;

    public WaveManager(List<WaveData> waves) {
        // : Initialize fields
        // currentWaveIndex=0, enemiesSpawned=0, spawnTimer=0, enemyCounter=0,
        // allWavesDone=false
        this.waves = waves;
        this.currentWaveIndex = 0;
        this.enemiesSpawned = 0;
        this.spawnTimer = 0;
        this.enemyCounter = 0;
        this.allWavesDone = false;
    }

    /**
     * Update wave spawning logic each frame.
     * 
     * @param dt        Delta time
     * @param entityOps Engine interface to add enemy entities
     * @param path      PathDefinition for enemy starting position
     * @param state     GameState to update wave progress
     */
    public void update(float dt, iEntityOps entityOps, PathDefinition path, GameState state) {
        // : If allWavesDone, return
        if (allWavesDone) {
            return;
        }
        // : If currentWaveIndex >= waves.size(), mark all done
        if (currentWaveIndex >= waves.size()) {
            allWavesDone = true;
            return;
        }
        // : Get current WaveData
        WaveData currentWave = waves.get(currentWaveIndex);
        // : Update state's current wave number
        state.setCurrentWave(currentWaveIndex + 1);
        // : Tick spawnTimer, if ready and enemies left to spawn:
        // - Calculate base health (60 * healthMultiplier) and speed (55 *
        // speedMultiplier)
        // - Create Enemy at path's first waypoint
        // - Add to entityOps
        // : If all enemies spawned, check if any are still alive
        // - If none alive: advance to next wave, give currency bonus, set prep phase
        spawnTimer += dt;
        if (spawnTimer >= currentWave.getSpawnInterval() && enemiesSpawned < currentWave.getEnemyCount()) {
            spawnTimer = 0;
            int baseHealth = (int) (60 * currentWave.getHealthMultiplier());
            float speed = 55 * currentWave.getSpeedMultiplier();
            com.badlogic.gdx.math.Vector2 start = path.getWaypoints().get(0);
            // Spawn enemy centered on first waypoint
            Enemy enemy = new Enemy(
                    "enemy-" + enemyCounter++,
                    start.x - 16, // center horizontally on waypoint
                    start.y - 16, // center vertically on waypoint
                    32, // hitbox size — smaller than tile for visual clarity
                    currentWave.getEnemyType(), // attackType
                    baseHealth, // health
                    speed, // speed
                    10 // reward
            );
            entityOps.addEntity(enemy);
            enemiesSpawned++;
        }
        if (enemiesSpawned >= currentWave.getEnemyCount()) {
            // Check if any enemies are still alive
            boolean anyAlive = entityOps.getAllEntities().stream().anyMatch(e -> e instanceof Enemy && e.isActive());
            if (!anyAlive) {
                currentWaveIndex++;
                enemiesSpawned = 0;
                spawnTimer = 0;
                state.addCurrency(50);
                // if no more waves, skip prep and go straight to win
                if (currentWaveIndex >= waves.size()) {
                    allWavesDone = true;
                } else {
                    state.setPrepPhase(true);
                }
            }
        }
    }

    public boolean isAllWavesDone() {
        return allWavesDone;
    }

    public int getCurrentWaveIndex() {
        return currentWaveIndex;
    }

    public int getTotalWaves() {
        return waves.size();
    }

    // reset current wave so it can be replayed after phishing success
    // sets spawn counter back to 0 so all enemies respawn
    public void replayCurrentWave() {
        enemiesSpawned = 0;
        spawnTimer = 0;
        allWavesDone = false;
    }
}
