package com.p10.game.scenes;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.p10.core.entities.Entity;
import com.p10.core.interfaces.iAudio;
import com.p10.core.interfaces.iCollision;
import com.p10.core.interfaces.iEntityOps;
import com.p10.core.interfaces.iInput;
import com.p10.core.interfaces.iMovement;
import com.p10.core.interfaces.iSceneControl;
import com.p10.core.scene.Scene;
import com.p10.game.ai.EnemyAI;
import com.p10.game.ai.PathDefinition;
import com.p10.game.ai.TowerAI;
import com.p10.game.entities.Enemy;
import com.p10.game.entities.Server;
import com.p10.game.entities.Tower;
import com.p10.game.grid.GameCollisionHandler;
import com.p10.game.grid.GridManager;
import com.p10.game.grid.TowerPlacer;
import com.p10.game.ui.EduPopup;
import com.p10.game.ui.FontManager;
import com.p10.game.ui.HUD;
import com.p10.game.ui.PhishingPopup;
import com.p10.game.wave.GameState;
import com.p10.game.wave.LevelConfig;
import com.p10.game.wave.WaveManager;

/**
 * GameplayScene is the main game scene — handles the full game loop:
 * prep phase (buy/place towers) → wave phase (enemies spawn, towers fire) →
 * repeat.
 *
 * Game flow:
 * 1. onLoad: initialize grid, path, AI, state, spawn server
 * 2. update: edu popup → game over check → win check → HUD input → prep/wave
 * logic
 * 3. render: grid → entities → HUD → popups → game over overlay
 *
 */
public class GameplayScene extends Scene {
    private LevelConfig levelConfig;
    private GridManager gridManager;
    private TowerPlacer towerPlacer;
    private GameCollisionHandler collisionHandler;
    private WaveManager waveManager;
    private GameState gameState;
    private EnemyAI enemyAI;
    private TowerAI towerAI;
    private PathDefinition path;
    private HUD hud;
    private EduPopup eduPopup;
    private Server server;
    private float screenW, screenH;
    private boolean musicMuted = false; // M key toggles this
    // TODO @ChayHan: Pause state
    private boolean isPaused = false;
    // TODO @HuiYang: Win auto-transition timer
    // private float winTimer = 0f;
    // init music slider, pause button dimensions
    private float musicVolume = 1.0f;
    private static final float PAUSE_BTN_W = 180f;
    private static final float PAUSE_BTN_H = 40f;
    // Static field so LevelSelectScene can set which level to play
    private static LevelConfig selectedLevel = null;
    // phishing email popup — shows when player dies on phishing level
    private PhishingPopup phishingPopup;
    private ShapeRenderer overlayRenderer;
    private float waveTextTimer = 0f;

    public static void setSelectedLevel(LevelConfig cfg) {
        selectedLevel = cfg;
    }

    public GameplayScene(iCollision collision, iEntityOps entityOps, iSceneControl sceneCtrl,
            iInput input, iAudio audio, iMovement movement,
            float screenW, float screenH) {
        super("GameplayScene", collision, entityOps, sceneCtrl, input, movement, audio);
        this.screenW = screenW;
        this.screenH = screenH;
    }

    @Override
    public boolean shouldRenderEntities() {
        // null check for when scene hasn't loaded yet
        if (gameState == null || phishingPopup == null)
            return true;
        return !isPaused && !gameState.isGameOver() && !gameState.isGameWon() && !phishingPopup.isVisible();
    }

    @Override
    protected void onLoad() {
        // : Get levelConfig from selectedLevel (default to level1_DDoS if null)
        this.levelConfig = selectedLevel != null ? selectedLevel : LevelConfig.level1_DDoS();
        // : Initialize GridManager with levelConfig's grid layout
        this.gridManager = new GridManager(levelConfig.getGridLayout(), 48); // 48px tiles: 10x48=480w, 8x48=384h — fits
        this.overlayRenderer = new ShapeRenderer(); // 800x480 window
        // : Build path from gridManager
        this.path = gridManager.buildPath();
        // : Initialize EnemyAI, TowerAI, TowerPlacer, GameCollisionHandler
        this.enemyAI = new EnemyAI(path);
        this.towerAI = new TowerAI();
        this.towerPlacer = new TowerPlacer();
        this.collisionHandler = new GameCollisionHandler();
        // : Initialize GameState with starting currency and lives
        this.gameState = new GameState(levelConfig.getStartCurrency(), levelConfig.getStartLives());
        // set worm flag if this level has worm mechanic
        if (levelConfig.hasWorm()) {
            gameState.setHasWorm(true);
        }
        // : Initialize WaveManager with levelConfig's waves
        this.waveManager = new WaveManager(levelConfig.getWaves());
        // : Initialize HUD and EduPopup
        this.hud = new HUD(screenW, screenH);
        this.eduPopup = new EduPopup(screenW, screenH);
        // : Show edu popup with level name and educational text
        this.eduPopup.show(levelConfig.getLevelName(), levelConfig.getEducationalText());
        // create phishing popup for phishing level mechanic
        this.phishingPopup = new PhishingPopup(screenW, screenH);
        // : Place Server entity at end of path
        List<Vector2> waypoints = path.getWaypoints();
        Vector2 endPt = waypoints.get(waypoints.size() - 1);
        // Place server centered on last path tile — waypoints are at tile centers
        int ts = gridManager.getTileSize();
        // server HP matches level lives — worm level = 2HP, others = 10HP
        float serverHP = levelConfig.hasWorm() ? 2f : 10f;
        this.server = new Server("server-0", endPt.x - ts / 2f, endPt.y - ts / 2f, ts, ts, serverHP);
        entityOps.addEntity(server);
        // Start background music if available
        try {
            audio.playMusic("bgm");
        } catch (Exception e) {
            /* no audio file */ }
    }

    @Override
    protected void onUnload() {
        // clear all entities in one call — prevents entity persistence across scenes
        entityOps.removeAllEntities();
        // : Dispose HUD and EduPopup
        hud.dispose();
        eduPopup.dispose();
        try {
            audio.stopMusic();
        } catch (Exception e) {
            /* ignore */ }
    }

    @Override
    public void update(float dt) {
        // : Clean up inactive entities every frame
        cleanupEntities();
        // TODO @ChayHan: Pause toggle — P key
        // If P pressed (and not in popup/gameover/win): toggle isPaused
        // If isPaused: return immediately (skip all game logic)
        // : If edu popup visible, handle its input and return

        if (input.isKeyJustPressed(Keys.P) && !eduPopup.isVisible() && !gameState.isGameOver()
                && !gameState.isGameWon()) {
            isPaused = !isPaused;
        }

        if (isPaused) {
            handlePauseInput();
            return;
        }

        if (eduPopup.isVisible()) {
            eduPopup.handleInput(input);
            return;
        }
        // : If game over, wait for ENTER to go back to MainMenu
        // : If game over, check worm → phishing → normal game over (in priority order)
        if (gameState.isGameOver()) {
            // WORM MECHANIC: if worm level, advance to next computer instead of game over
            if (levelConfig.hasWorm() && gameState.advanceComputer()) {
                int nextComp = gameState.getCurrentComputer();
                int[][] newLayout = levelConfig.getWormLayout(nextComp);
                this.gridManager = new GridManager(newLayout, 48);
                this.path = gridManager.buildPath();
                this.enemyAI = new EnemyAI(path);
                entityOps.removeAllEntities();
                List<Vector2> wp = path.getWaypoints();
                Vector2 endPt = wp.get(wp.size() - 1);
                int ts = gridManager.getTileSize();
                this.server = new Server("server-comp-" + nextComp, endPt.x - ts / 2f, endPt.y - ts / 2f, ts, ts, 2f);
                entityOps.addEntity(server);
                gameState.setPrepPhase(true);
                waveManager.replayCurrentWave();
                return;
            }
            // PHISHING MECHANIC: if phishing level and not used yet, trigger popup
            if (levelConfig.hasPhishing() && !gameState.isPhishingUsed() && !gameState.isPhishingActive()) {
                gameState.setGameOver(false);
                gameState.setPhishingActive(true);
                phishingPopup.show();
                return;
            }
            // NORMAL GAME OVER: no special mechanics left, wait for ENTER
            if (input.isKeyJustPressed(Keys.ENTER)) {
                System.out.println("=== ENTER PRESSED, SWITCHING TO MAINMENU ===");
                sceneCtrl.switchScene("MainMenu");
            }
            return;
        }

        // : Handle phishing popup interaction when active
        if (gameState.isPhishingActive() && phishingPopup.isVisible()) {
            float mx = Gdx.input.getX();
            float my = screenH - Gdx.input.getY();

            if (Gdx.input.justTouched()) {
                int result = phishingPopup.handleClick(mx, my);
            }

            if (phishingPopup.allFound()) {
                if (input.isKeyJustPressed(Keys.ENTER)) {
                    phishingPopup.hide();
                    gameState.phishingSuccess();
                    entityOps.removeAllEntities();
                    List<Vector2> wp = path.getWaypoints();
                    Vector2 endPt = wp.get(wp.size() - 1);
                    int ts = gridManager.getTileSize();
                    this.server = new Server("server-respawn", endPt.x - ts / 2f, endPt.y - ts / 2f, ts, ts, 10f);
                    entityOps.addEntity(server);
                    gridManager.resetOccupied();
                    waveManager.replayCurrentWave();
                    gameState.setPrepPhase(true);
                }
                return;
            }

            if (phishingPopup.hasFailed()) {
                if (input.isKeyJustPressed(Keys.ENTER)) {
                    System.out.println("=== PHISHING FAILED, HIDING POPUP ===");
                    phishingPopup.hide();
                    gameState.phishingFailed();
                    sceneCtrl.switchScene("MainMenu"); // go straight to menu, skip game over screen
                }
                return;
            }

            return;
        }

        // : If game won, wait for ENTER
        if (gameState.isGameWon()) {
            if (input.isKeyJustPressed(Keys.ENTER)) {
                sceneCtrl.switchScene("LevelSelect");
            }
            return;
        }

        // : If all waves done, set game won
        if (waveManager.isAllWavesDone() && !gameState.isGameWon()) {
            gameState.setGameWon(true);
            entityOps.removeAllEntities();
        }

        // : Handle HUD input (tower selection)
        hud.handleInput(input, gameState, towerPlacer);
        // : Handle tower placement
        if (gameState.isPrepPhase()) {
            towerPlacer.handleInput(input, gridManager, gameState, entityOps, screenH);
            // TODO @ChayHan: Call towerPlacer.handleDrag() here for drag-and-drop
            // Get mouse pos: Gdx.input.getX(), screenH - Gdx.input.getY()
            // Pass mouseDown: Gdx.input.isTouched()
            float mouseX = Gdx.input.getX();
            float mouseY = screenH - Gdx.input.getY(); // Invert Y
            boolean mouseDown = Gdx.input.isTouched();
            towerPlacer.handleDrag(mouseX, mouseY, mouseDown, gridManager, gameState, entityOps);

            // If right-clicked sell Tower
            // edited as mouseX and Y were already declared with y inversion above for the
            if (Gdx.input.isButtonJustPressed(1)) {
                towerPlacer.handleSell(mouseX, mouseY, gridManager, gameState, entityOps);
            }
        }
        // : PREP PHASE: wait for SPACE to start wave
        if (gameState.isPrepPhase() && input.isKeyJustPressed(Keys.SPACE)) {
            gameState.setPrepPhase(false);
            waveTextTimer = 0f; // reset timer when wave starts
            // Play wave start sound
            try {
                audio.playSound("wave_start");
            } catch (Exception e) {
                /* no audio file */ }
        }
        // : WAVE PHASE:
        // - Update WaveManager (spawn enemies)
        if (!gameState.isPrepPhase()) {
            waveManager.update(dt, entityOps, path, gameState);
            waveTextTimer += dt; // tick wave text timer
        }
        // - Gather enemies and towers from entity list
        List<Enemy> enemies = new ArrayList<>();
        List<Tower> towers = new ArrayList<>();
        for (Entity e : entityOps.getAllEntities()) {
            if (e.isActive()) {
                e.update(dt);
                if (e instanceof Enemy)
                    enemies.add((Enemy) e);
                if (e instanceof Tower)
                    towers.add((Tower) e);
            }
        }
        // - Run EnemyAI movement for each enemy
        for (Enemy enemy : enemies) {
            enemyAI.update(enemy, dt);
        }
        // - Run TowerAI targeting for each tower
        for (Tower tower : towers) {
            towerAI.update(tower, enemies, dt, entityOps);
        }
        // - Run GameCollisionHandler
        collisionHandler.processCollisions(entityOps.getAllEntities(), gameState);
        // Safety net: force-deactivate any enemy at 0 HP that collision missed
        for (Enemy enemy : enemies) {
            if (enemy.isDead() && enemy.isActive()) {
                enemy.setActive(false);
            }
        }
        // M key toggles music mute/unmute
        if (input.isKeyJustPressed(Keys.M)) {
            musicMuted = !musicMuted;
            if (musicMuted) {
                try {
                    audio.stopMusic();
                } catch (Exception e) {
                }
            } else {
                try {
                    audio.playMusic("bgm");
                } catch (Exception e) {
                }
            }
        }
        // : ESC: pause btn
        if (input.isKeyJustPressed(Keys.ESCAPE)) {
            isPaused = true;
        }
    }

    private void cleanupEntities() {
        // : Remove all inactive entities from entityOps
        List<Entity> dead = new ArrayList<>();
        for (Entity e : entityOps.getAllEntities()) {
            if (!e.isActive())
                dead.add(e);
        }
        for (Entity e : dead) {
            entityOps.removeEntity(e.getId());
        }
    }

    // paude input handling method. handles button clicks, volume slider and
    // unpausing the game
    private void handlePauseInput() {
        float cx = screenW / 2f;
        float midY = screenH / 2f;

        // Button positions (must match renderPauseMenu)
        float continueY = midY + 30;
        float restartY = midY - 20;
        float exitY = midY - 70;
        float volSliderX = cx - 80;
        float volSliderY = midY - 120;
        float sliderW = 160f;
        // clean esc from the pause UI
        if (input.isKeyJustPressed(Keys.ESCAPE) || input.isKeyJustPressed(Keys.P)) {
            isPaused = false;
            return;
        }
        if (Gdx.input.justTouched()) {
            float mx = Gdx.input.getX();
            float my = screenH - Gdx.input.getY();

            // Continue button
            if (mx >= cx - PAUSE_BTN_W / 2 && mx <= cx + PAUSE_BTN_W / 2
                    && my >= continueY && my <= continueY + PAUSE_BTN_H) {
                isPaused = false;
            }
            // Restart button
            if (mx >= cx - PAUSE_BTN_W / 2 && mx <= cx + PAUSE_BTN_W / 2
                    && my >= restartY && my <= restartY + PAUSE_BTN_H) {
                isPaused = false;
                sceneCtrl.switchScene("GameplayScene");
            }
            // Exit button
            if (mx >= cx - PAUSE_BTN_W / 2 && mx <= cx + PAUSE_BTN_W / 2
                    && my >= exitY && my <= exitY + PAUSE_BTN_H) {
                isPaused = false;
                sceneCtrl.switchScene("MainMenu");
            }
            // Volume slider
            if (mx >= volSliderX && mx <= volSliderX + sliderW
                    && my >= volSliderY - 5 && my <= volSliderY + 15) {
                musicVolume = (mx - volSliderX) / sliderW;
                musicVolume = Math.max(0f, Math.min(1f, musicVolume));
                try {
                    audio.setMusicVolume(musicVolume);
                } catch (Exception e) {
                    /* no audio */ }
            }
        }
    }

    @Override
    public void renderShapes(ShapeRenderer renderer) {
        // Fill entire screen with dark background first
        renderer.setColor(0.08f, 0.08f, 0.15f, 1f);
        renderer.rect(0, 0, screenW, screenH);
        // : Render grid on top only when placing towers (towerPlacer has a boolean for
        // this)
        gridManager.renderGrid(renderer);
        if (towerPlacer.getSelectedTowerType() != null || towerPlacer.isDragging()) {
            gridManager.renderGridLines(renderer);
        }

        // Loop entityOps.getAllEntities(), if Tower → call tower.renderRange(renderer)
        // End filled batch for line-based range rendering
        renderer.end();
        renderer.begin(ShapeRenderer.ShapeType.Line);
        // Only show range on the tower the mouse is hovering over
        List<Entity> allEntities = entityOps.getAllEntities();
        float mouseX = input.getMousePosition().x;
        float mouseY = Gdx.graphics.getHeight() - input.getMousePosition().y;
        for (int i = 0; i < allEntities.size(); i++) {
            if (allEntities.get(i) instanceof Tower) {
                Tower tower = (Tower) allEntities.get(i);
                // Get mouse pos (with Y inversion), call towerPlacer.renderHoverRange(renderer,
                // gridManager, mx, my)
                float cx = tower.getPosition().x + tower.getHitbox().width / 2;
                float cy = tower.getPosition().y + tower.getHitbox().height / 2;
                float dx = mouseX - cx;
                float dy = mouseY - cy;
                float dist = (float) Math.sqrt(dx * dx + dy * dy);
                if (dist <= tower.getHitbox().width) {
                    tower.renderRange(renderer);
                }
            }
        }
        towerPlacer.renderHoverRange(renderer, gridManager, mouseX, mouseY);
        // TODO @ChayHan: Render drag ghost
        towerPlacer.renderDragGhost(renderer);
        renderer.end();
        // : Render entities (towers, enemies, projectiles) on top
        renderer.begin(ShapeRenderer.ShapeType.Filled); // Switch to filled for entity rendering
        // TODO @ChayHan: If isPaused, draw dark overlay (GL_BLEND, black rect 0.6f
        // alpha)
        // : Render HUD shapes

        // skip HUD when phishing popup is covering the screen
        if (!phishingPopup.isVisible()) {
            hud.renderShapes(renderer, gameState, towerPlacer);// edited to reflect the new tower placer input handling
        }

        // : Render game over / win overlay shapes
        if (gameState.isGameOver() || gameState.isGameWon()) {
            // Render semi-transparent overlay
            renderer.setColor(0, 0, 0, 0.5f);
            renderer.rect(0, 0, screenW, screenH);
        }
        // : Render edu popup if visible
        if (eduPopup.isVisible()) {
            eduPopup.renderShapes(renderer);
        }
        // : Render phishing popup shapes if active
        if (phishingPopup.isVisible()) {
            phishingPopup.renderShapes(renderer);
        }
        // solid black background for clean pause screen — hides all entities
        if (isPaused) {
            renderer.setColor(0, 0, 0, 1f);
            renderer.rect(0, 0, screenW, screenH);
            renderPauseShapes(renderer);
            return; // skip all other rendering
        }
    }

    // pause shapes handler method to make sure that the correct shapes are rendered
    // when the game is paused. called from renderShapes when isPaused is true
    private void renderPauseShapes(ShapeRenderer renderer) {
        float cx = screenW / 2f;
        float midY = screenH / 2f;

        // Panel background
        renderer.setColor(0.12f, 0.12f, 0.2f, 0.95f);
        renderer.rect(cx - 120, midY - 140, 240, 230);

        // Continue button (green)
        renderer.setColor(0.2f, 0.7f, 0.2f, 1f);
        renderer.rect(cx - PAUSE_BTN_W / 2, midY + 30, PAUSE_BTN_W, PAUSE_BTN_H);

        // Restart button (yellow)
        renderer.setColor(0.8f, 0.7f, 0.1f, 1f);
        renderer.rect(cx - PAUSE_BTN_W / 2, midY - 20, PAUSE_BTN_W, PAUSE_BTN_H);

        // Exit button (red)
        renderer.setColor(0.8f, 0.2f, 0.2f, 1f);
        renderer.rect(cx - PAUSE_BTN_W / 2, midY - 70, PAUSE_BTN_W, PAUSE_BTN_H);

        // Volume slider track
        float volSliderX = cx - 80;
        float volSliderY = midY - 120;
        renderer.setColor(0.3f, 0.3f, 0.3f, 1f);
        renderer.rect(volSliderX, volSliderY, 160, 10);

        // Volume slider fill
        renderer.setColor(0.2f, 0.6f, 1f, 1f);
        renderer.rect(volSliderX, volSliderY, 160 * musicVolume, 10);

        // Volume slider knob
        renderer.setColor(1f, 1f, 1f, 1f);
        renderer.circle(volSliderX + 160 * musicVolume, volSliderY + 5, 8);

    }

    // pause text rendering method to draw the "PAUSED" title and button labels on
    // top of the pause menu shapes. called from renderTextures when isPaused is
    // true
    private void renderPauseText(SpriteBatch batch) {
        BitmapFont title = FontManager.getTitle();
        BitmapFont body = FontManager.getBody();
        BitmapFont small = FontManager.getSmall();
        float cx = screenW / 2f;
        float midY = screenH / 2f;

        // Title
        title.setColor(1f, 1f, 1f, 1f);
        title.draw(batch, "PAUSED", cx - 40, midY + 100);

        // Button labels (centered on buttons)
        body.setColor(1f, 1f, 1f, 1f);
        body.draw(batch, "Continue", cx - 30, midY + 55);
        body.draw(batch, "Restart", cx - 28, midY + 5);
        body.draw(batch, "Exit to Menu", cx - 42, midY - 45);

        // Volume label
        small.setColor(0.8f, 0.8f, 0.8f, 1f);
        small.draw(batch, "Volume: " + (int) (musicVolume * 100) + "%", cx - 35, midY - 100);
    }

    @Override
    public void renderTextures(SpriteBatch batch) {
        // when paused, only render pause text — nothing else
        if (isPaused) {
            renderPauseText(batch);
            return;
        }

        // when edu popup is showing, only render edu popup text
        if (eduPopup.isVisible()) {
            eduPopup.renderText(batch);
            return;
        }

        // when phishing popup is showing, only render phishing text
        if (phishingPopup.isVisible()) {
            phishingPopup.renderText(batch);
            return;
        }

        // same for game over and win
        if (gameState.isGameOver() || gameState.isGameWon()) {
            batch.end();
            overlayRenderer.begin(ShapeRenderer.ShapeType.Filled);
            overlayRenderer.setColor(0, 0, 0, 0.95f);
            overlayRenderer.rect(0, 0, screenW, screenH);
            overlayRenderer.end();
            batch.begin();
            if (gameState.isGameOver()) {
                hud.getFont().draw(batch, "GAME OVER - Press ENTER", screenW / 2 - 100, screenH / 2);
            } else {
                hud.getFont().draw(batch, "YOU WIN! - Press ENTER", screenW / 2 - 100, screenH / 2);
            }
            return;
        }
        // normal gameplay — render HUD text
        hud.renderText(batch, gameState, getNextWaveEnemyType());
        // only show instructions if prep phase OR wave text timer under 10s
        if (gameState.isPrepPhase() || waveTextTimer < 10f) {
            hud.renderInstructions(batch, gameState, towerPlacer);
        }
    }

    private String getNextWaveEnemyType() {
        // : Return the enemy type of the current/next wave for HUD display
        if (waveManager.getCurrentWaveIndex() < waveManager.getTotalWaves()) {
            return levelConfig.getWaves().get(waveManager.getCurrentWaveIndex()).getEnemyType();
        }
        return "NONE";
    }

    @Override
    public void dispose() {
        // : Dispose HUD and EduPopup
        hud.dispose();
        eduPopup.dispose();
        overlayRenderer.dispose();
    }
    // no dispose needed for phishingPopup — uses FontManager shared fonts
}
