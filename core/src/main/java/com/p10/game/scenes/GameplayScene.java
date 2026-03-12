package com.p10.game.scenes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
import com.p10.game.entities.Server;
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
        this.levelConfig = selectedLevel != null ? selectedLevel : LevelConfig.getLevel1DDoS();
        // : Initialize GridManager with levelConfig's grid layout
        this.gridManager = new GridManager(levelConfig.getGridLayout());
        // : Build path from gridManager
        this.path = gridManager.buildPath();
        // : Initialize EnemyAI, TowerAI, TowerPlacer, GameCollisionHandler
        this.enemyAI = new EnemyAI(path);
        this.towerAI = new TowerAI();
        this.towerPlacer = new TowerPlacer(gridManager);
        this.collisionHandler = new GameCollisionHandler();
        // : Initialize GameState with starting currency and lives
        this.gameState = new GameState(levelConfig.getStartingCurrency(), levelConfig.getStartingLives());
        // : Initialize WaveManager with levelConfig's waves
        this.waveManager = new WaveManager(levelConfig.getWaves());
        // : Initialize HUD and EduPopup
        this.hud = new HUD(gameState);
        this.eduPopup = new EduPopup(levelConfig.getLevelName(), levelConfig.getEducationalText());
        // : Show edu popup with level name and educational text
        this.eduPopup.show();
        // : Place Server entity at end of path
        this.server = new Server(path.getEndPoint());
        entityOps.addEntity(server);
    }

    @Override
    protected void onUnload() {
        // : Remove all game entities via entityOps
        entityOps.clearEntities();
        // : Dispose HUD and EduPopup
        hud.dispose();
        eduPopup.dispose();
    }

    @Override
    public void update(float dt) {
        // : Clean up inactive entities every frame
            cleanupEntities();
        // : If edu popup visible, handle its input and return
        if (eduPopup.isVisible()) {
            if (input.isKeyJustPressed("ENTER")) {
                eduPopup.hide();
            }
            return;
        }
        // : If game over, wait for ENTER to go back to MainMenu
        if (gameState.isGameOver()) {
            if (input.isKeyJustPressed("ENTER")) {
                sceneCtrl.changeScene("MainMenuScene");
            }
            return;
        }
        // : If all waves done, set game won
        if (waveManager.isAllWavesDone() && !gameState.isGameWon()) {
            gameState.setGameWon(true);
        }
        // : Handle HUD input (tower selection)
         hud.updateInput(input);
        // : Handle tower placement
        if (hud.isPlacingTower()) {
            towerPlacer.update(input, hud.getSelectedTowerType(), entityOps);
        }
        // : PREP PHASE: wait for SPACE to start wave
        if (!gameState.isInWave() && input.isKeyJustPressed("SPACE")) {
            gameState.setInWave(true);
        }
        // : WAVE PHASE:
        // - Update WaveManager (spawn enemies)
            if (gameState.isInWave()) {
                waveManager.update(dt, entityOps, path, gameState);
            }
        // - Gather enemies and towers from entity list

        // - Run EnemyAI movement for each enemy
            enemyAI.update(entityOps);
        // - Run TowerAI targeting for each tower
            towerAI.update(entityOps);
        // - Update all entities
            entityOps.updateAll(dt);
        // - Run GameCollisionHandler
            collisionHandler.handleCollisions(entityOps, gameState);
        // - Mark dead enemies as inactive
            entityOps.markEnemiesDead(gameState);
        // : ESC to return to MainMenu
        if (input.isKeyJustPressed("ESCAPE")) {
            sceneCtrl.changeScene("MainMenuScene");
        }   
    }

    private void cleanupEntities() {
        // : Remove all inactive entities from entityOps
            entityOps.removeInactiveEntities();
    }

    @Override
    public void renderShapes(ShapeRenderer renderer) {
        // : Render grid
        gridManager.render(renderer);
        // : Render tower range indicator if placing tower
        if (hud.isPlacingTower()) {
            towerPlacer.render(renderer);
        }
        // : Render HUD shapes
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
        hud.render(batch);
        // : Render edu popup text if visible
        if (eduPopup.isVisible()) {
            eduPopup.render(batch);
        }
        // : Render game over / win overlay text
        if (gameState.isGameOver()) {
            // Render "Game Over" text
            hud.renderGameOver(batch);
        } else if (gameState.isGameWon()) {
            // Render "You Win!" text
            hud.renderGameWon(batch);
        }
    }

    private String getNextWaveEnemyType() {
        // : Return the enemy type of the current/next wave for HUD display
        return null;
    }

    @Override
    public void dispose() {
        // : Dispose HUD and EduPopup
        hud.dispose();
        eduPopup.dispose();
    }
}
