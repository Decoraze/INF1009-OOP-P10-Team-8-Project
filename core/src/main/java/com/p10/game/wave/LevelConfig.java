package com.p10.game.wave;

import java.util.ArrayList;
import java.util.List;

/**
 * LevelConfig defines everything about a level:
 * name, educational text, wave definitions, tower pool, grid layout, starting
 * resources.
 *
 * Contains static factory methods for each predefined level.
 *
 */
public class LevelConfig {
    private String levelName;
    private String attackTheme;
    private String educationalText;
    private List<WaveData> waves;
    private String[] towerPool;
    private int handSize;
    private int startCurrency;
    private int startLives;
    private int[][] gridLayout;

    private LevelConfig() {
        this.waves = new ArrayList<>();
    }

    // --- Getters ---
    public String getLevelName() {
        return levelName;
    }

    public String getAttackTheme() {
        return attackTheme;
    }

    public String getEducationalText() {
        return educationalText;
    }

    public List<WaveData> getWaves() {
        return waves;
    }

    public String[] getTowerPool() {
        return towerPool;
    }

    public int getHandSize() {
        return handSize;
    }

    public int getStartCurrency() {
        return startCurrency;
    }

    public int getStartLives() {
        return startLives;
    }

    public int[][] getGridLayout() {
        return gridLayout;
    }

    // --- PREDEFINED LEVELS ---

    /**
     * Level 1: DDoS Attack — many weak, fast enemies. Firewall is the counter.
     * Educational: DDoS floods servers with traffic, firewalls filter malicious
     * requests.
     */
    public static LevelConfig level1_DDoS() {
        LevelConfig cfg = new LevelConfig();
        // : Set levelName, attackTheme, educationalText
        // : Set towerPool, handSize, startCurrency=100, startLives=20
        // : Add 3 waves of DDOS enemies with increasing difficulty
        // : Define gridLayout (0=buildable, 1=path, 2=blocked)
        return cfg;
    }

    /**
     * Level 2: Virus Outbreak — few tanky enemies. Antivirus is the counter.
     * Educational: Viruses disguise as legitimate programs, antivirus scans for
     * signatures.
     */
    public static LevelConfig level2_Virus() {
        LevelConfig cfg = new LevelConfig();
        // : Set level config for virus-themed level
        // : Waves include VIRUS and TROJAN enemies
        // : startCurrency=120, startLives=15
        return cfg;
    }

    /**
     * Level 3: Phishing Campaign — fast, weak enemies. Encryption is the counter.
     * Educational: Phishing tricks users into revealing credentials, encryption
     * protects data.
     */
    public static LevelConfig level3_Phishing() {
        LevelConfig cfg = new LevelConfig();
        // : Set level config for phishing-themed level
        // : Waves of PHISHING enemies with high speed, low health
        // : startCurrency=100, startLives=12
        return cfg;
    }

    /**
     * Level 4: Mixed Assault — all enemy types. Defense in depth required.
     * Educational: Real attacks combine multiple vectors, layered security is
     * needed.
     */
    public static LevelConfig level4_Mixed() {
        LevelConfig cfg = new LevelConfig();
        // : Set level config for mixed-threat level
        // : 5 waves with different enemy types (DDOS, VIRUS, PHISHING, TROJAN,
        // WORM)
        // : startCurrency=150, startLives=10
        return cfg;
    }
}
