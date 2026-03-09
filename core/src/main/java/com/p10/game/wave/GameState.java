package com.p10.game.wave;

/**
 * GameState tracks all runtime game data:
 * currency, lives, wave progress, score, phase state, shop selection.
 *
 * Also contains static tower type definitions and pricing.
 *
 */
public class GameState {
    private int currency;
    private int lives;
    private int currentWave;
    private int totalWaves;
    private int score;
    private boolean gameOver;
    private boolean gameWon;
    private boolean waveInProgress;
    private boolean prepPhase;

    // Tower shop definitions
    public static final String[] ALL_TOWER_TYPES = { "FIREWALL", "ANTIVIRUS", "ENCRYPTION", "IDS" };
    public static final int[] TOWER_PRICES = { 30, 80, 55, 45 };
    // FIREWALL=$30, IDS=$45, ENCRYPTION=$55, ANTIVIRUS=$80

    private String selectedTowerType;

    public GameState(int startCurrency, int lives) {
        // : Initialize all fields
        // currency, lives from params
        // currentWave=0, score=0, gameOver=false, gameWon=false
        // waveInProgress=false, prepPhase=true, selectedTowerType=null
    }

    public boolean canAfford(String towerType) {
        // : Return true if currency >= price of towerType
        return false;
    }

    public static int getPrice(String towerType) {
        // : Look up price from ALL_TOWER_TYPES/TOWER_PRICES arrays
        return 999;
    }

    /**
     * Get the cheapest effective tower for a given enemy attack type.
     * Ensures fairness — player can always afford at least one counter.
     * DDOS/WORM → FIREWALL ($30)
     * VIRUS → ANTIVIRUS ($80)
     * TROJAN → IDS ($45)
     * PHISHING → ENCRYPTION ($55)
     */
    public static String getCheapestCounter(String attackType) {
        // : Implement the mapping above
        return "FIREWALL";
    }

    public void purchaseTower(String towerType) {
        // : Deduct price from currency if affordable
    }

    public void addCurrency(int amount) {
        currency += amount;
    }

    public void spendCurrency(int amount) {
        currency -= amount;
    }

    public void addScore(int amount) {
        score += amount;
    }

    // --- Getters/Setters ---
    public int getCurrency() {
        return currency;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int l) {
        this.lives = l;
        if (lives <= 0) {
            lives = 0;
            gameOver = true;
        }
    }

    public int getCurrentWave() {
        return currentWave;
    }

    public void setCurrentWave(int w) {
        this.currentWave = w;
    }

    public int getTotalWaves() {
        return totalWaves;
    }

    public void setTotalWaves(int t) {
        this.totalWaves = t;
    }

    public int getScore() {
        return score;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean b) {
        this.gameOver = b;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public void setGameWon(boolean b) {
        this.gameWon = b;
    }

    public boolean isWaveInProgress() {
        return waveInProgress;
    }

    public void setWaveInProgress(boolean b) {
        this.waveInProgress = b;
    }

    public boolean isPrepPhase() {
        return prepPhase;
    }

    public void setPrepPhase(boolean b) {
        this.prepPhase = b;
    }

    public String getSelectedTowerType() {
        return selectedTowerType;
    }

    public void setSelectedTowerType(String t) {
        this.selectedTowerType = t;
    }
}
