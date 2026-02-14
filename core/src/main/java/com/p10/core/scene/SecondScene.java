package com.p10.core.scene;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.p10.core.entities.CircleEntity;
import com.p10.core.entities.Entity;
import com.p10.core.entities.RectangleEntity;
import com.p10.core.interfaces.iAudio;
import com.p10.core.interfaces.iCollision;
import com.p10.core.interfaces.iEntityOps;
import com.p10.core.interfaces.iInput;
import com.p10.core.interfaces.iMovement;
import com.p10.core.interfaces.iSceneControl;

public class SecondScene extends Scene {

    public SecondScene(
            iCollision collision,
            iEntityOps entityOps,
            iSceneControl sceneCtrl,
            iInput input,
            iAudio audio,
            iMovement movement) {
        super("SecondScene", collision, entityOps, sceneCtrl, input, movement, audio);
    }

    @Override
    protected void onLoad() {
        System.out.println("[SecondScene] Loading entities...");
        // Player
        CircleEntity player = new CircleEntity("player2", 200, 240, 25, Color.YELLOW);
        player.setKinematic(true);// playuer is set to kinematic here (check entity and collision for usage) so
                                  // thatr the collision push-apart doesn't affect the player movement.
        entityOps.addEntity(player);
        System.out.println("[SecondScene] Spawned player (CircleEntity)");
        // AI entities
        entityOps.addEntity(new RectangleEntity("ai-box1", 150, 300, 40, 40, Color.CYAN));
        System.out.println("[SecondScene] Spawned ai-box1 (RectangleEntity)");
        entityOps.addEntity(new RectangleEntity("ai-box2", 650, 150, 60, 30, Color.MAGENTA));
        System.out.println("[SecondScene] Spawned ai-box2 (RectangleEntity)");
    }

    @Override
    protected void onUnload() {
        System.out.println("[SecondScene] unload()");
        entityOps.removeEntity("player2");
        entityOps.removeEntity("ai-box1");
        entityOps.removeEntity("ai-box2");
        System.out.println("[SecondScene] Unload complete.");
    }

    @Override
    public void update(float dt) {
        // Player movement
        Entity player = entityOps.getEntity("player2");
        if (player != null)
            movement.applyPlayerMovement(player, dt, input);

        // AI movement
        Entity box1 = entityOps.getEntity("ai-box1");
        if (box1 != null)
            movement.applyMovement(box1, dt);
        Entity box2 = entityOps.getEntity("ai-box2");
        if (box2 != null)
            movement.applyMovement(box2, dt);

        // Scene switching
        if (input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            sceneCtrl.switchScene("FirstScene");
        }
        if (input.isKeyJustPressed(Input.Keys.Q)) {
            sceneCtrl.switchScene("ThirdScene");
        }
    }

    @Override
    public void renderShapes(ShapeRenderer renderer) {
        // draw border
        // renderer.rect(20, 20, 760, 440);
    }

    @Override
    public void renderTextures(SpriteBatch batch) { // render the extures these will be used for later
        // no textures for this scene
    }
}