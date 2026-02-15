package com.p10.core.scene;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.p10.core.interfaces.iAudio;
import com.p10.core.interfaces.iCollision;
import com.p10.core.interfaces.iEntityOps;
import com.p10.core.interfaces.iInput;
import com.p10.core.interfaces.iMovement;
import com.p10.core.interfaces.iSceneControl;

public abstract class Scene {
    // protected List<Entity> entities = new ArrayList<>();
    protected final String name;

    // Interfaces
    protected final iCollision collision;
    protected final iEntityOps entityOps;
    protected final iSceneControl sceneCtrl;
    protected final iInput input;
    protected final iMovement movement;
    protected final iAudio audio;

    private boolean loaded = false;

    protected Scene(
            String name,
            iCollision collision,
            iEntityOps entityOps,
            iSceneControl sceneCtrl,
            iInput input,
            iMovement movement,
            iAudio audio) {
        this.name = name;
        this.collision = collision;
        this.entityOps = entityOps;
        this.sceneCtrl = sceneCtrl;
        this.input = input;
        this.audio = audio;
        this.movement = movement;
    }

    public final String getName() {
        return name;
    }

    public final boolean isLoaded() {
        return loaded;
    }

    /** Called once when this scene becomes active (first time). */
    public final void load() {
        if (loaded)
            return;
        loaded = true;
        onLoad();
    }

    /** Called when this scene stops being active (every time you switch away). */
    public final void unload() {
        if (!loaded)
            return;
        onUnload();
        loaded = false;
    }

    /** Put “spawn entities / init scene state” here. */
    protected abstract void onLoad();

    /** Put “remove entities / free scene state” here. */
    protected abstract void onUnload();

    /** Per-frame update. */
    public abstract void update(float dt);

    /** Scene-level shape drawing (background UI boxes, etc.). */
    public abstract void renderShapes(ShapeRenderer renderer);

    /** Scene-level texture drawing (background image, UI textures, etc.). */
    public abstract void renderTextures(SpriteBatch batch);

    /** Optional: Scene cleanup when app closes (not required but nice). */
    public void dispose() {
        // default no-op
    }
    /*
     * // methods for UML compliance
     * public void addEntity(Entity e) {// add entity to the scene
     * entities.add(e);
     * }
     * 
     * public void removeEntity(String id) { // remove any entities (same logic as
     * entitymanage)
     * for (Entity obj : entities) {
     * if (obj.getId().equals(id)) {
     * entities.remove(obj);
     * break;
     * }
     * }
     * }
     * 
     * public List<Entity> getEntities() { // getting entity list that needs
     * return new ArrayList<>(entities);
     * }
     */
}
