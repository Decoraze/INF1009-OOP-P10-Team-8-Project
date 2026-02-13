package com.p10.core.scene;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.p10.core.entities.CircleEntity;
import com.p10.core.entities.Entity;
import com.p10.core.entities.RectangleEntity;
import com.p10.core.entities.TriangleEntity;
import com.p10.core.interfaces.iCollision;
import com.p10.core.interfaces.iEntityOps;
import com.p10.core.interfaces.iInput;
import com.p10.core.interfaces.iMovement;
import com.p10.core.interfaces.iSceneControl;

public class FirstScene extends Scene {

    public FirstScene(
            iCollision collision,
            iEntityOps entityOps,
            iSceneControl sceneCtrl,
            iInput input,
            iMovement movement) {
        super("FirstScene", collision, entityOps, sceneCtrl, input, movement);
    }

    @Override
    protected void onLoad() {
        System.out.println("[FirstScene] Loading entities...");
        // Player
        entityOps.addEntity(new CircleEntity("player", 200, 240, 25, Color.RED));
        System.out.println("[FirstScene] Spawned player (CircleEntity)");
        // AI entities
        entityOps.addEntity(new RectangleEntity("ai-rect", 500, 180, 50, 50, Color.GREEN));
        System.out.println("[FirstScene] Spawned ai-rect (RectangleEntity)");
        entityOps.addEntity(new TriangleEntity("ai-tri", 600, 350,
                new Vector2[] { new Vector2(580, 330), new Vector2(620, 330), new Vector2(600, 380) }, Color.BLUE));
        System.out.println("[FirstScene] Spawned ai-tri (TriangleEntity)");
        entityOps.addEntity(new CircleEntity("ai-circle", 350, 100, 20, Color.ORANGE));
        System.out.println("[FirstScene] Spawned ai-circle (CircleEntity)");
    }

    @Override
    protected void onUnload() {
        System.out.println("[FirstScene] unload()");
        entityOps.removeEntity("player");
        entityOps.removeEntity("ai-rect");
        entityOps.removeEntity("ai-tri");
        entityOps.removeEntity("ai-circle");
        System.out.println("[FirstScene] Unload complete.");
    }

    @Override
    public void update(float dt) {
        // Player movement
        Entity player = entityOps.getEntity("player");
        if (player != null)
            movement.applyPlayerMovement(player, dt, input);

        // AI movement
        Entity aiRect = entityOps.getEntity("ai-rect");
        if (aiRect != null)
            movement.applyMovement(aiRect, dt);
        Entity aiTri = entityOps.getEntity("ai-tri");
        if (aiTri != null)
            movement.applyMovement(aiTri, dt);
        Entity aiCircle = entityOps.getEntity("ai-circle");
        if (aiCircle != null)
            movement.applyMovement(aiCircle, dt);

        // Scene switching
        if (input.isKeyJustPressed(Input.Keys.ENTER)) {
            input.playSound("jump");
            sceneCtrl.switchScene("SecondScene");
        }
        if (input.isKeyJustPressed(Input.Keys.NUM_3)) {
            sceneCtrl.switchScene("ThirdScene");
        }
    }

    @Override
    public void renderShapes(ShapeRenderer renderer) {
        // Simple visual “panel”
        // renderer.rect(80, 80, 640, 320);
    }

    @Override
    public void renderTextures(SpriteBatch batch) { // render the extures these will be used for later
        // no textures for this scene
    }
}
