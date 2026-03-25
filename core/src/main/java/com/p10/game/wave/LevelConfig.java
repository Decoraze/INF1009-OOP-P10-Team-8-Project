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
    // added phishing fields to track phshing UI or not
    private boolean hasPhishing;
    // flag to enable worm propagation mechanic — 3 computers, death = next computer
    private boolean hasWorm;
    // additional grid layouts for worm level (computer 2 and 3)
    private List<int[][]> wormLayouts;

    // bool to indicate if level requires phishing UI
    public boolean hasPhishing() {// check if level needs to use phshing UI
        return hasPhishing;
    }

    private LevelConfig() {
        this.waves = new ArrayList<>();
        this.hasPhishing = false; // default: no phishing mechanic unless level enables it (only phishing level
                                  // and multiselect should have )
        this.hasWorm = false;
        this.wormLayouts = new ArrayList<>();
    }

    // --- Getters ---
    // check if this level uses the worm propagation mechanic
    public boolean hasWorm() {
        return hasWorm;
    }

    // get the grid layout for a specific worm computer (0-indexed)
    public int[][] getWormLayout(int computerIndex) {
        if (computerIndex == 0)
            return gridLayout; // first computer uses main layout
        return wormLayouts.get(computerIndex - 1); // 2nd and 3rd from wormLayouts list
    }

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
        cfg.educationalText = "LEVEL 1: DDoS ATTACK\n\nDDoS floods your server with junk traffic so real\nusers can't get through. Think of it like a crowd\nblocking a doorway — nobody gets in.\n\nUse FIREWALL [1] towers ($30) to filter out the\nbad traffic before it reaches your server.\n\nPlace towers on GREEN tiles next to the path.\nPress SPACE to start each wave.";

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
        cfg.educationalText = "LEVEL 2: VIRUS OUTBREAK\n\nViruses hide inside normal-looking files. When someone\nruns the file, the virus activates and spreads.\nThey're slow to move but tough to kill.\n\nUse ANTIVIRUS [2] towers ($80) to scan and catch\ninfected files before they do damage.\n\nFIREWALL won't help here — you need ANTIVIRUS!";
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
        cfg.educationalText = "LEVEL 3: PHISHING CAMPAIGN\n\nPhishing uses fake emails to trick people into giving\nup passwords or clicking bad links. It targets\npeople, not systems — that's what makes it dangerous.\n\nUse ENCRYPTION [3] towers ($55) to secure your data\nso even stolen credentials are useless.\n\nSPECIAL MECHANIC: If you lose all lives, you get\nONE CHANCE to identify a phishing email.\nSpot all the red flags and you revive with 1 life!";
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
        cfg.hasPhishing = true; // enable phishing mechanic for this level
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
        cfg.educationalText = "LEVEL 4: MIXED ASSAULT\n\nReal attackers don't use just one method — they hit\nyou from every angle at once. One firewall won't cut it.\n\nYou need LAYERED DEFENSE (Defense in Depth):\n[1] Firewall $30 — stops DDoS and Worms\n[2] Antivirus $80 — catches Viruses and Trojans\n[3] Encryption $55 — blocks Phishing\n[4] IDS $45 — detects Worms and Trojans\n\nMatch the RIGHT tower to each wave's threat!";
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
                { 0, 0, 0, 0, 1, 1, 1, 1, 1, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 },
                { 0, 0, 0, 0, 1, 1, 1, 0, 1, 0 },
                { 0, 0, 0, 0, 1, 0, 1, 0, 1, 0 },
                { 1, 1, 1, 1, 1, 0, 1, 1, 1, 0 },
        };
        return cfg;
    }

    public static LevelConfig level5_FullSpectrum() {
        LevelConfig cfg = new LevelConfig();
        cfg.levelName = "Full Spectrum";
        cfg.attackTheme = "All Threats";
        cfg.educationalText = "LEVEL 5: FULL SPECTRUM ASSAULT\n\n6 waves of mixed threats hitting hard and fast.\nYou need all 4 tower types but budget is tight.\n\nPlan ahead — check what each wave throws at you\nand place the right counters. Wrong towers = wasted\nmoney and dead server.\n\nThis is Defense in Depth under pressure.";
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
        cfg.educationalText = "LEVEL 6: SURVIVAL MODE\n\n10 waves. 5 lives. Every wave gets harder.\nThreats keep changing so you can't rely on one setup.\n\nManage your money carefully — you earn $50 bonus\nafter each wave plus rewards for kills.\n\nHow long can you hold out?";
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

    /*
     * Level 7: Worm Propagation — worm spreads across 3 computers.
     * Each computer has 2HP. When one dies, infection spreads to next.
     * Enemies carry forward. All 3 dead = game over. All enemies dead = win.
     * Educational: Worms self-replicate across networks without user interaction.
     */
    public static LevelConfig level7_Worm() {
        LevelConfig cfg = new LevelConfig();
        cfg.levelName = "Worm Propagation";
        cfg.attackTheme = "Worm";
        cfg.educationalText = "LEVEL 7: WORM PROPAGATION\n\nWorms spread across networks on their own — no one\nneeds to click anything. One infected machine can\ntake down a whole network in minutes.\n\nUse FIREWALL [1] + IDS [4] to block and detect\nworm traffic before it spreads.\n\nSPECIAL MECHANIC: You defend 3 COMPUTERS (2HP each).\nWhen a computer falls, the worm jumps to the next.\nYour towers reset but enemies keep coming!\n\nStop the worm before all 3 are infected.";
        cfg.towerPool = new String[] { "FIREWALL", "IDS" };
        cfg.handSize = 5;
        cfg.startCurrency = 120;
        cfg.startLives = 2; // 2HP per computer
        cfg.hasWorm = true;

        // waves — worm type enemies
        cfg.waves.add(WaveData.wormWave(1, 8, 1.0f, 1.0f));
        cfg.waves.add(WaveData.wormWave(2, 10, 1.2f, 1.1f));
        cfg.waves.add(WaveData.wormWave(3, 12, 1.4f, 1.2f));

        // computer 1 layout
        cfg.gridLayout = new int[][] {
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 1, 1, 1, 1, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 1, 1, 1, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 1, 1, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 1, 1, 1 },
        };

        // computer 2 layout — different path
        cfg.wormLayouts.add(new int[][] {
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 },
                { 0, 0, 0, 0, 1, 1, 1, 1, 0, 0 },
                { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 1, 1, 1, 1, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 1, 1, 1 },
        });

        // computer 3 layout — shortest path, hardest
        cfg.wormLayouts.add(new int[][] {
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 1, 1, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 1, 1, 1, 1, 1, 1 },
        });

        return cfg;
    }
}
