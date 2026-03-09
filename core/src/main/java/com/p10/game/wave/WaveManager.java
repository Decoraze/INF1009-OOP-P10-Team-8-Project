package com.p10.game.wave;

import java.util.List;

import com.p10.core.interfaces.iEntityOps;
import com.p10.game.ai.PathDefinition;

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
        // : If currentWaveIndex >= waves.size(), mark all done
        // : Get current WaveData
        // : Update state's current wave number
        // : Tick spawnTimer, if ready and enemies left to spawn:
        // - Calculate base health (60 * healthMultiplier) and speed (55 *
        // speedMultiplier)
        // - Create Enemy at path's first waypoint
        // - Add to entityOps
        // : If all enemies spawned, check if any are still alive
        // - If none alive: advance to next wave, give currency bonus, set prep phase
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
}
