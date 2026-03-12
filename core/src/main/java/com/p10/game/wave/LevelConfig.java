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
        cfg.levelName = "DDoS Attack";
        cfg.attackTheme = "DDoS";
        cfg.educationalText = "DDoS floods servers with traffic, firewalls filter malicious requests.";
        // : Set towerPool, handSize, startCurrency=100, startLives=20
        cfg.towerPool = new String[]{"Firewall"};
        cfg.handSize = 5;
        cfg.startCurrency = 100;
        cfg.startLives = 20;
        // : Add 3 waves of DDOS enemies with increasing difficulty
        cfg.waves.add(WaveData.ddosWave(1, 10, 1.0f, 1.0f));
        cfg.waves.add(WaveData.ddosWave(2, 15, 1.2f, 1.2f));
        cfg.waves.add(WaveData.ddosWave(3, 20, 1.4f, 1.4f));
        // : Define gridLayout (0=buildable, 1=path, 2=blocked)
        cfg.gridLayout = new int[][]{
            {0, 0, 0, 0, 0},
            {0, 1, 1, 1, 0},
            {0, 0, 0, 0, 0}
        };
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
        // : Waves of VIRUS enemies with moderate speed, high health
         cfg.levelName = "Virus Outbreak";
         cfg.attackTheme = "Virus";
         cfg.educationalText = "Viruses disguise as legitimate programs, antivirus scans for signatures.";
         cfg.towerPool = new String[]{"Antivirus"};
         cfg.handSize = 5;
         cfg.startCurrency = 120;
         cfg.startLives = 15;
         cfg.waves.add(WaveData.virusWave(1, 5, 1.0f, 1.0f));
         cfg.waves.add(WaveData.virusWave(2, 7, 1.1f, 1.1f));
         cfg.waves.add(WaveData.virusWave(3, 10, 1.2f, 1.2f));
         cfg.gridLayout = new int[][]{
             {0, 0, 0, 0, 0},
             {0, 1, 1, 1, 0},
             {0, 0, 0, 0, 0}
         };
         return cfg;
        // : Waves include VIRUS and TROJAN enemies
        // : startCurrency=120, startLives=15
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
            cfg.levelName = "Phishing Campaign";
            cfg.attackTheme = "Phishing";
            cfg.educationalText = "Phishing tricks users into revealing credentials, encryption protects data.";
            cfg.towerPool = new String[]{"Encryption"};
            cfg.handSize = 5;
            cfg.startCurrency = 100;
            cfg.startLives = 12;
            cfg.waves.add(WaveData.phishingWave(1, 10, 1.5f, 0.8f));
            cfg.waves.add(WaveData.phishingWave(2, 15, 1.7f, 0.7f));
            cfg.waves.add(WaveData.phishingWave(3, 20, 2.0f, 0.6f));
            cfg.gridLayout = new int[][]{
                {0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0},
                {0, 0, 0, 0, 0}
            };
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
        cfg.levelName = "Mixed Assault";
        cfg.attackTheme = "Mixed Threats";
        cfg.educationalText = "Real attacks combine multiple vectors, layered security is needed.";
        cfg.towerPool = new String[]{"Firewall", "Antivirus", "Encryption"};
        cfg.handSize = 5;
        cfg.startCurrency = 150;
        cfg.startLives = 10;
        cfg.waves.add(WaveData.ddosWave(1, 10, 1.0f, 1.0f));
        cfg.waves.add(WaveData.virusWave(2, 7, 1.1f, 1.1f));
        cfg.waves.add(WaveData.phishingWave(3, 15, 1.5f, 0.8f));
        cfg.waves.add(WaveData.trojanWave(4, 5, 0.8f, 1.5f));
        cfg.waves.add(WaveData.wormWave(5, 8, 1.2f, 1.0f));
        cfg.gridLayout = new int[][]{
            {0, 0, 0, 0, 0},
            {0, 1, 1, 1, 0},
            {0, 0, 0, 0, 0}
        };
        return cfg;
    }
}
