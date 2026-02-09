package com.p10.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.p10.core.interfaces.iCollision;
import com.p10.core.interfaces.iEntityOps;
import com.p10.core.interfaces.iInput;
import com.p10.core.interfaces.iMovement;
import com.p10.core.interfaces.iSceneControl;
import com.p10.core.managers.CollisionManager;
import com.p10.core.managers.EntityManager;
import com.p10.core.managers.InputOutputManager;
import com.p10.core.managers.MovementManager;
import com.p10.core.managers.SceneManager;

/*
 * ApplicationCore - Main Function ish
 * 1) Initialise 5 Managers
 * 2) Coordinate Game Loop of updating & rendering
 * 3) Manage LibGDX comonents
 * 4) Handle Cleanup on exit
 */

public class ApplicationCore extends ApplicationAdapter {
    // Managers
    private EntityManager entityManager;
    private CollisionManager collisionManager;
    private MovementManager movementManager;
    private SceneManager sceneManager;
    private InputOutputManager inputOutputManager;

    // Rendering (LibGDX)
    private SpriteBatch batch; // Draws textures
    private ShapeRenderer shapeRenderer; // Draws shapes
    private OrthographicCamera camera; // Viewport camera dk why name so long

    // Debugging Purposes
    private BitmapFont debugFont; // For FPS and debug text
    private float deltaTime; // Time between frames
    private int fps; // Frames per second NOT FIRST PERSON SHOOTER GAMES PLEASE NOTE

    // Game Config (adjust here if needed for window size)
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 480;
    private static final boolean DEBUG_MODE = true; // Show FPS and debug info

    @Override
    public void create() { // initialise managers scene and components
        System.out.println("[ApplicationCore] Initializing A-Engine");
        // Initialize rendering components
        initializeRendering();

        // Initialize all 5 managers
        initializeManagers();

        // Set up initial scene
        initializeScene();

        System.out.println("[ApplicationCore] Complete Initial");
        System.out.println("[ApplicationCore] Window: " + WINDOW_WIDTH + "x" + WINDOW_HEIGHT);
    }

    // Render Game every 60 times / s or 60FPS maybe can do 120...
    @Override
    public void render() {
        // Get time since last frame
        deltaTime = Gdx.graphics.getDeltaTime();
        fps = Gdx.graphics.getFramesPerSecond();

        // Clear screen (dark blue background)
        ScreenUtils.clear(0.1f, 0.1f, 0.2f, 1.0f);// use this to change the background

        // Update camera
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        // GAME LOOP ORDER (probs won't change)
        // 1. Input
        // 2. Update managers
        // 3. Render

        handleInput();
        updateManagers(deltaTime);
        renderGame();

        // Debug info "handler"
        if (DEBUG_MODE) {
            renderDebugInfo();
        }
    }

    // window resizing like your dynamic size for html
    @Override
    public void resize(int width, int height) {
        System.out.println("[ApplicationCore] Window resized: " + width + "x" + height);
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
        // Notify managers of resize (so they can update their entities/scenes)
        if (sceneManager != null) {
            sceneManager.onResize(width, height);
        }

        if (entityManager != null) {
            entityManager.onResize(width, height);
        }
    }

    // teh graceful closer
    /**
     * Called when application closes
     * Cleanup all resources to prevent memory leaks, data might stay in RAM dats
     * why
     */
    @Override
    public void dispose() {
        System.out.println("[ApplicationCore] Shutting down...");

        // Dispose managers
        if (entityManager != null)
            entityManager.dispose();
        if (collisionManager != null)
            collisionManager.dispose();
        if (movementManager != null)
            movementManager.dispose();
        if (sceneManager != null)
            sceneManager.dispose();
        if (inputOutputManager != null)
            inputOutputManager.dispose();

        // Dispose LibGDX components
        if (batch != null)
            batch.dispose();
        if (shapeRenderer != null)
            shapeRenderer.dispose();
        if (debugFont != null)
            debugFont.dispose();

        System.out.println("[ApplicationCore] Shutdown complete.");
    }

    // initialise stuff like Rendering, Managers, Scene
    /*
     * Initialize LibGDX rendering components
     */
    private void initializeRendering() {
        System.out.println("[ApplicationCore] Initializing rendering...");

        // Create camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Create rendering tools
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        // Create debug font
        debugFont = new BitmapFont();
        debugFont.getData().setScale(1.5f);

        System.out.println("[ApplicationCore] Rendering initialized.");
    }

    /*
     * Initialize all 5 required managers
     */
    private void initializeManagers() {
        System.out.println("[ApplicationCore] Initializing managers...");

        // Create all managers
        entityManager = new EntityManager();
        collisionManager = new CollisionManager();
        movementManager = new MovementManager();
        sceneManager = new SceneManager();
        inputOutputManager = new InputOutputManager();

        System.out.println("[ApplicationCore] ✓ EntityManager initialized");
        System.out.println("[ApplicationCore] ✓ CollisionManager initialized");
        System.out.println("[ApplicationCore] ✓ MovementManager initialized");
        System.out.println("[ApplicationCore] ✓ SceneManager initialized");
        System.out.println("[ApplicationCore] ✓ InputOutputManager initialized");
    }

    /*
     * Set up the initial scene
     */
    private void initializeScene() {
        System.out.println("[ApplicationCore] Setting up initial scene...");

        // TODO: Create and register scenes
        // sceneManager.registerScene("menu", new MenuScene());
        // sceneManager.registerScene("game", new GameScene());
        // sceneManager.switchScene("menu");
        // might add one mroe here depends if want stop scene or game end scene

        System.out.println("[ApplicationCore] Scene setup complete.");
    }

    // input handler and yes that's it...
    private void handleInput() {
        // Input is handled by InputOutputManager
        inputOutputManager.handleInput();
        // KEYBOARD TEST
        if (inputOutputManager.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ANY_KEY)) {
            System.out.println("[TEST] A key was detected!");
        }

        // Space Bar Jump Sound
        if (inputOutputManager.isKeyJustPressed(com.badlogic.gdx.Input.Keys.SPACE)) {
            // This tells the manager to look for "jump" in the soundMap and play it
            inputOutputManager.playSound("jump");
        }

        // 3. Add your Mouse test
        if (inputOutputManager.isMouseButtonPressed(com.badlogic.gdx.Input.Buttons.LEFT)) {
            System.out.println("[TEST] Mouse Clicked at: " + inputOutputManager.getMousePosition());
        }

        // Main Game Music
        if (inputOutputManager.isKeyJustPressed(com.badlogic.gdx.Input.Keys.M)) {
            inputOutputManager.playMusic("bgm");
        }
        if (inputOutputManager.isKeyJustPressed(com.badlogic.gdx.Input.Keys.N)) {
            inputOutputManager.stopMusic(); //
            System.out.println("[AUDIO] Music Stopped");
        }
    }

    // update managers. ORDER MATTERS AH DON'T ANYHOW CHANGE PLEASE (if want to
    // change call/text 98554963 Aurelius)
    private void updateManagers(float deltaTime) {
        // 1. Update current scene (may add/remove entities)
        sceneManager.update(deltaTime);

        // 2. Update entity positions (movement)
        movementManager.updateMovement(entityManager.getAllEntities(), deltaTime);

        // 3. Check for collisions (after movement)
        collisionManager.checkCollisions(entityManager.getCollidableEntities());

        // 4. Update all entities (custom logic like animations and sounds)
        entityManager.updateAll(deltaTime);
    }

    // game rendering
    private void renderGame() {
        // Render shapes
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        sceneManager.renderShapes(shapeRenderer);
        entityManager.renderShapes(shapeRenderer);
        shapeRenderer.end();

        // Render textures
        batch.begin();
        sceneManager.renderTextures(batch);
        entityManager.renderTextures(batch);
        batch.end();
    }

    // render debug items
    private void renderDebugInfo() {
        batch.begin();

        debugFont.draw(batch, "FPS: " + fps, 10, WINDOW_HEIGHT - 10);
        debugFont.draw(batch, "Entities: " + entityManager.getEntityCount(), 10, WINDOW_HEIGHT - 35);
        debugFont.draw(batch, "Scene: " + sceneManager.getCurrentSceneName(), 10, WINDOW_HEIGHT - 60);

        // Mouse Test (Mouse Input) - Chay Han
        com.badlogic.gdx.math.Vector2 mPos = inputOutputManager.getMousePosition();
        debugFont.draw(batch, "Mouse: " + mPos.x + ", " + mPos.y, 10, WINDOW_HEIGHT - 60);

        // Keyboard Test (Keyboard Input) - Chay Han
        String keyStatus = inputOutputManager.isKeyPressed(com.badlogic.gdx.Input.Keys.ANY_KEY) ? "Key Pressed" : "No Key";
        debugFont.draw(batch, "Keyboard: " + keyStatus, 10, WINDOW_HEIGHT - 85);

        batch.end();
    }

    /**
     * Get the entity operations interface
     * Used by Scene to spawn/remove entities
     */
    public iEntityOps getEntityOps() {
        return (iEntityOps) entityManager;
    }

    /**
     * Get the collision interface
     * Used by Scene to check collisions
     */
    public iCollision getCollision() {
        return (iCollision) collisionManager;
    }

    /**
     * Get the movement interface
     * Used by Scene to apply movement/physics
     */
    public iMovement getMovement() {
        return (iMovement) movementManager;
    }

    /**
     * Get the scene control interface
     * Used by Scene to switch scenes
     */
    public iSceneControl getSceneControl() {
        return (iSceneControl) sceneManager;
    }

    /**
     * Get the input interface
     * Used by Scene to check input and play sounds
     */
    public iInput getInput() {
        return (iInput) inputOutputManager;
    }
}
