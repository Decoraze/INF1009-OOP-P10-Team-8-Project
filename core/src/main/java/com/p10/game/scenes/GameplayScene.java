package com.p10.game.scenes;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
import com.p10.game.entities.Projectile;
import com.p10.game.entities.Server;
import com.p10.game.entities.Tower;
import com.p10.game.grid.GameCollisionHandler;
import com.p10.game.grid.GridManager;
import com.p10.game.grid.TowerPlacer;
import com.p10.game.ui.EduPopup;
import com.p10.game.ui.HUD;
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
    private float winTimer = 0f;

    // Static field so LevelSelectScene can set which level to play
    private static LevelConfig selectedLevel = null;

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
    protected void onLoad() {
        // : Get levelConfig from selectedLevel (default to level1_DDoS if null)
        this.levelConfig = selectedLevel != null ? selectedLevel : LevelConfig.level1_DDoS();
        // : Initialize GridManager with levelConfig's grid layout
        this.gridManager = new GridManager(levelConfig.getGridLayout(), 48); // 48px tiles: 10x48=480w, 8x48=384h — fits
                                                                             // 800x480 window
        // : Build path from gridManager
        this.path = gridManager.buildPath();
        // : Initialize EnemyAI, TowerAI, TowerPlacer, GameCollisionHandler
        this.enemyAI = new EnemyAI(path);
        this.towerAI = new TowerAI();
        this.towerPlacer = new TowerPlacer();
        this.collisionHandler = new GameCollisionHandler();
        // : Initialize GameState with starting currency and lives
        this.gameState = new GameState(levelConfig.getStartCurrency(), levelConfig.getStartLives());
        // : Initialize WaveManager with levelConfig's waves
        this.waveManager = new WaveManager(levelConfig.getWaves());
        // : Initialize HUD and EduPopup
        this.hud = new HUD(screenW, screenH);
        this.eduPopup = new EduPopup(screenW, screenH);
        // : Show edu popup with level name and educational text
        this.eduPopup.show(levelConfig.getLevelName(), levelConfig.getEducationalText());
        // : Place Server entity at end of path
        List<Vector2> waypoints = path.getWaypoints();
        Vector2 endPt = waypoints.get(waypoints.size() - 1);
        // Place server centered on last path tile — waypoints are at tile centers
        int ts = gridManager.getTileSize();
        this.server = new Server("server-0", endPt.x - ts / 2f, endPt.y - ts / 2f, ts, ts, 10f);
        entityOps.addEntity(server);
        // Start background music if available
        try {
            audio.playMusic("bgm");
        } catch (Exception e) {
            /* no audio file */ }
    }

    @Override
    protected void onUnload() {
        // : Remove all game entities via entityOps
        for (Entity e : new ArrayList<>(entityOps.getAllEntities())) {
            entityOps.removeEntity(e.getId());
        }
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

        if (input.isKeyJustPressed(Keys.P) && !eduPopup.isVisible() && !gameState.isGameOver() && !gameState.isGameWon()) {
            isPaused = !isPaused;
        }

        if (isPaused) {
            return; // Skip all game logic if paused
        }

        if (eduPopup.isVisible()) {
            eduPopup.handleInput(input);
            return;
        }
        // : If game over, wait for ENTER to go back to MainMenu
        if (gameState.isGameOver()) {
            if (input.isKeyJustPressed(Keys.ENTER)) {
                sceneCtrl.switchScene("MainMenu");
            }
            return;
        }
        if (gameState.isGameWon()) {
            winTimer += dt;
            if (winTimer >= 3f || input.isKeyJustPressed(Keys.ENTER)) {
                sceneCtrl.switchScene("MainMenu");
            }
            return;
        }

        // : If all waves done, set game won
        if (waveManager.isAllWavesDone() && !gameState.isGameWon()) {
            gameState.setGameWon(true);
            winTimer = 0f;
            for (Entity e : new ArrayList<>(entityOps.getAllEntities())) {
                if (e instanceof Enemy || e instanceof Projectile) {
                    entityOps.removeEntity(e.getId());
                }
            }
        }
        // : Handle HUD input (tower selection)
        hud.handleInput(input, gameState, towerPlacer);
        // : Handle tower placement
        if (gameState.isInPrepPhase()) {
            towerPlacer.handleInput(input, gridManager, gameState, entityOps, screenH);
            // TODO @ChayHan: Call towerPlacer.handleDrag() here for drag-and-drop
            // Get mouse pos: Gdx.input.getX(), screenH - Gdx.input.getY()
            // Pass mouseDown: Gdx.input.isTouched()
            float mouseX = Gdx.input.getX();
            float mouseY = screenH - Gdx.input.getY(); // Invert Y
            boolean mouseDown = Gdx.input.isTouched();
            towerPlacer.handleDrag(mouseX, mouseY, mouseDown, gridManager, gameState, entityOps);

            // TODO @JunMing: Right-click sell — check Gdx.input.isButtonJustPressed(1)
            // If right-clicked, call towerPlacer.handleSell(mouseX, mouseY, gridManager,
            // gameState, entityOps)
            if (Gdx.input.isButtonJustPressed(1))
            {
            	towerPlacer.handleSell(input.getMousePosition().x, input.getMousePosition().y, gridManager, gameState, entityOps);
            }
        }
        // : PREP PHASE: wait for SPACE to start wave
        if (gameState.isInPrepPhase() && input.isKeyJustPressed(Keys.SPACE)) {
            gameState.setInPrepPhase(false);
            // Play wave start sound
            try {
                audio.playSound("wave_start");
            } catch (Exception e) {
                /* no audio file */ }
        }
        // : WAVE PHASE:
        // - Update WaveManager (spawn enemies)
        if (!gameState.isInPrepPhase()) {
            waveManager.update(dt, entityOps, path, gameState);
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
        // : ESC to return to MainMenu
        if (input.isKeyJustPressed(Keys.ESCAPE)) {
            sceneCtrl.switchScene("MainMenu");
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

    @Override
    public void renderShapes(ShapeRenderer renderer) {
        // Fill entire screen with dark background first
        renderer.setColor(0.08f, 0.08f, 0.15f, 1f);
        renderer.rect(0, 0, screenW, screenH);
        // : Render grid on top
        gridManager.renderGrid(renderer);
        // TODO @JunMing: Render tower range circles on all placed towers
        // Loop entityOps.getAllEntities(), if Tower → call tower.renderRange(renderer)
        List<Entity> allEntities = entityOps.getAllEntities();
        Tower tower;
        for (int i = 0; i < allEntities.size(); i++)
        {
        	if (allEntities.get(i) instanceof Tower)
        	{
        		tower = (Tower) allEntities.get(i);
        		tower.renderRange(renderer);
        	}
        }
        // TODO @JunMing: Render hover range preview when placing tower
        // Get mouse pos (with Y inversion), call towerPlacer.renderHoverRange(renderer,
        // gridManager, mx, my)
        float mouseX = input.getMousePosition().x;
        float mouseY = Gdx.graphics.getHeight() - input.getMousePosition().y;
        towerPlacer.renderHoverRange(renderer, gridManager, mouseX, mouseY);
        // TODO @ChayHan: Render drag ghost
        towerPlacer.renderDragGhost(renderer);

        // TODO @ChayHan: If isPaused, draw dark overlay (GL_BLEND, black rect 0.6f
        // alpha)
        // : Render HUD shapes

        if (isPaused) {
            Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
            Gdx.gl.glBlendFunc(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
            renderer.setColor(0, 0, 0, 0.6f);
            renderer.rect(0, 0, screenW, screenH);
            Gdx.gl.glDisable(Gdx.gl.GL_BLEND);
        }

        hud.renderShapes(renderer, gameState, towerPlacer);// edited to reflect the new tower placer input handling
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
    }

    @Override
    public void renderTextures(SpriteBatch batch) {
        // : Render HUD text
        hud.renderText(batch, gameState, getNextWaveEnemyType());
        // TODO @ChayHan: Render instructions
        hud.renderInstructions(batch, gameState, towerPlacer);

        // TODO @ChayHan: If isPaused, draw "PAUSED" text + "Press P to resume"
        // : Render edu popup text if visible
        if (isPaused) {
            hud.getFont().draw(batch, "PAUSED", screenW / 2 - 50, screenH / 2 + 20);
            hud.getFont().draw(batch, "Press P to resume", screenW / 2 - 90, screenH / 2 - 20);
        }

        if (eduPopup.isVisible()) {
            eduPopup.renderText(batch);
        }
        // : Render game over / win overlay text
        if (gameState.isGameOver()) {
            // Render "Game Over" text
            hud.getFont().draw(batch, "GAME OVER - Press ENTER", screenW / 2 - 100, screenH / 2);
        } else if (gameState.isGameWon()) {
            // Render "You Win!" text
            hud.getFont().draw(batch, "YOU WIN! - Press ENTER", screenW / 2 - 100, screenH / 2);
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
    }
}
