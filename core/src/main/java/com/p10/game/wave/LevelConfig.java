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
        cfg.educationalText = "LEVEL 1: DDoS ATTACK\n\nDDoS (Distributed Denial of Service) floods servers with massive traffic.\n\nYour defense: FIREWALL towers [1] - $30 each\nPlace towers on GREEN tiles next to the path.\nPress [SPACE] to start each wave.\n\nOnly FIREWALL deals full damage to DDoS attacks!\nWrong tower types deal ZERO damage.";
        // : Set towerPool, handSize, startCurrency=100, startLives=20
        cfg.towerPool = new String[] { "Firewall" };
        cfg.handSize = 5;
        cfg.startCurrency = 100;
        cfg.startLives = 10;
        // : Add 3 waves of DDOS enemies with increasing difficulty
        cfg.waves.add(WaveData.ddosWave(1, 10, 1.0f, 1.0f));
        cfg.waves.add(WaveData.ddosWave(2, 15, 1.2f, 1.2f));
        cfg.waves.add(WaveData.ddosWave(3, 20, 1.4f, 1.4f));
        // : Define gridLayout (0=buildable, 1=path, 2=blocked)
        cfg.gridLayout = new int[][] {
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 1, 1, 1, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 1, 1, 1, 1, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 1, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 1, 1, 1, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 1, 1, 1 },
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
        cfg.educationalText = "LEVEL 2: VIRUS OUTBREAK\n\nViruses disguise as legitimate programs to infect your system.\nThey are slow but tough — high HP enemies.\n\nYour defense: ANTIVIRUS towers [2] - $80 each\nMore expensive but deadly against viruses.\n\nFIREWALL will NOT work here — use ANTIVIRUS!";
        cfg.towerPool = new String[] { "Antivirus" };
        cfg.handSize = 5;
        cfg.startCurrency = 120;
        cfg.startLives = 15;
        cfg.waves.add(WaveData.virusWave(1, 5, 1.0f, 1.0f));
        cfg.waves.add(WaveData.virusWave(2, 7, 1.1f, 1.1f));
        cfg.waves.add(WaveData.virusWave(3, 10, 1.2f, 1.2f));
        cfg.gridLayout = new int[][] {
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 },
                { 0, 0, 1, 1, 1, 1, 1, 1, 0, 0 },
                { 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 1, 1, 1, 1, 1, 1, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 1, 1, 1 },
        };
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
        cfg.levelName = "Phishing Campaign";
        cfg.attackTheme = "Phishing";
        cfg.educationalText = "LEVEL 3: PHISHING CAMPAIGN\n\nPhishing tricks users into revealing passwords via fake emails.\nFast but fragile — many weak enemies rushing in.\n\nYour defense: ENCRYPTION towers [3] - $55 each\nPlace towers early — phishing attacks are FAST!\n\nOnly ENCRYPTION blocks phishing attempts!";
        cfg.towerPool = new String[] { "Encryption" };
        cfg.handSize = 5;
        cfg.startCurrency = 100;
        cfg.startLives = 12;
        cfg.waves.add(WaveData.phishingWave(1, 8, 1.5f, 0.5f)); // Changed to 8
        cfg.waves.add(WaveData.phishingWave(2, 10, 1.7f, 0.9f));
        cfg.waves.add(WaveData.phishingWave(3, 12, 2.0f, 1.1f));
        cfg.gridLayout = new int[][] {
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 1, 1, 1, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 1, 1, 1, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 1, 1, 1, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 1, 1, 1, 1 },
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
        cfg.educationalText = "LEVEL 4: MIXED ASSAULT\n\nReal cyberattacks combine MULTIPLE threat vectors.\nYou will face DDoS, Viruses, Phishing, Trojans and Worms.\n\nYou need LAYERED DEFENSE (Defense in Depth):\n[1] Firewall $30 — blocks DDoS/Worms\n[2] Antivirus $80 — kills Viruses/Trojans\n[3] Encryption $55 — stops Phishing\n[4] IDS $45 — detects Worms/Trojans\n\nMatch the RIGHT tower to each threat!";
        cfg.towerPool = new String[] { "Firewall", "Antivirus", "Encryption" };
        cfg.handSize = 5;
        cfg.startCurrency = 150;
        cfg.startLives = 10;
        cfg.waves.add(WaveData.ddosWave(1, 10, 1.0f, 1.0f));
        cfg.waves.add(WaveData.virusWave(2, 7, 1.1f, 1.1f));
        cfg.waves.add(WaveData.phishingWave(3, 15, 1.5f, 0.8f));
        cfg.waves.add(WaveData.trojanWave(4, 5, 0.8f, 1.5f));
        cfg.waves.add(WaveData.wormWave(5, 8, 1.2f, 1.0f));
        cfg.gridLayout = new int[][] {
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 1, 1, 1, 1, 1, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 1, 1, 1, 1, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 },
                { 0, 1, 1, 1, 1, 1, 0, 1, 0, 0 },
                { 0, 1, 0, 0, 0, 1, 1, 1, 0, 0 },
                { 0, 1, 1, 1, 1, 1, 0, 0, 0, 0 },
        };
        return cfg;
    }

    public static LevelConfig level5_FullSpectrum() {
        LevelConfig cfg = new LevelConfig();
        cfg.levelName = "Full Spectrum";
        cfg.attackTheme = "All Threats";
        cfg.educationalText = "LEVEL 5: FULL SPECTRUM ASSAULT\n\nAdvanced persistent threats use EVERY attack vector.\nYou must build a complete security stack.\n\nAll towers available — budget is tight.\nPrioritize counters for each wave's threat type.\n\nDefense in Depth is your only option!";
        cfg.towerPool = new String[] { "FIREWALL", "ANTIVIRUS", "ENCRYPTION", "IDS" };
        cfg.handSize = 6;
        cfg.startCurrency = 120;
        cfg.startLives = 8;
        cfg.waves.add(WaveData.ddosWave(1, 12, 1.2f, 1.2f));
        cfg.waves.add(WaveData.virusWave(2, 8, 1.4f, 1.0f));
        cfg.waves.add(WaveData.wormWave(3, 10, 1.3f, 1.3f));
        cfg.waves.add(WaveData.phishingWave(4, 15, 1.5f, 1.2f));
        cfg.waves.add(WaveData.trojanWave(5, 6, 2.0f, 0.8f));
        cfg.waves.add(WaveData.ddosWave(6, 20, 1.6f, 1.5f));
        cfg.gridLayout = new int[][] {
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 1, 0, 0, 1, 1, 1, 1, 0, 0 },
                { 0, 1, 1, 1, 1, 0, 0, 1, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 },
                { 0, 0, 1, 1, 1, 1, 1, 1, 0, 0 },
                { 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 1, 1, 1, 1, 1, 1, 1, 1 },
        };
        return cfg;
    }

    public static LevelConfig level6_Survival() {
        LevelConfig cfg = new LevelConfig();
        cfg.levelName = "Survival Mode";
        cfg.attackTheme = "Endless";
        cfg.educationalText = "LEVEL 6: SURVIVAL MODE\n\nWave after wave of escalating threats.\nNo end in sight — how long can you last?\n\nEvery wave gets harder.\nManage your economy carefully.\n\nThis is the ultimate test of your cybersecurity knowledge!";
        cfg.towerPool = new String[] { "FIREWALL", "ANTIVIRUS", "ENCRYPTION", "IDS" };
        cfg.handSize = 6;
        cfg.startCurrency = 200;
        cfg.startLives = 5;
        cfg.waves.add(WaveData.ddosWave(1, 8, 1.0f, 1.0f));
        cfg.waves.add(WaveData.virusWave(2, 6, 1.2f, 1.0f));
        cfg.waves.add(WaveData.phishingWave(3, 12, 1.0f, 1.3f));
        cfg.waves.add(WaveData.trojanWave(4, 5, 1.5f, 1.0f));
        cfg.waves.add(WaveData.wormWave(5, 10, 1.3f, 1.2f));
        cfg.waves.add(WaveData.ddosWave(6, 15, 1.5f, 1.4f));
        cfg.waves.add(WaveData.virusWave(7, 10, 1.8f, 1.2f));
        cfg.waves.add(WaveData.phishingWave(8, 20, 1.5f, 1.5f));
        cfg.waves.add(WaveData.trojanWave(9, 8, 2.5f, 1.0f));
        cfg.waves.add(WaveData.wormWave(10, 15, 2.0f, 1.8f));
        cfg.gridLayout = new int[][] {
                { 1, 1, 1, 1, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 1, 1, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 1, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 1, 1, 1, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 1, 1, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 },
        };
        return cfg;
    }
}
