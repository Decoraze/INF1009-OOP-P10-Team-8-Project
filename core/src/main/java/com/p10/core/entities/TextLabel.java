package com.p10.core.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class TextLabel extends NonCollidableEntity {
    private String text; //

    public TextLabel(String id, float x, float y, String text) {
        super(id, x, y);
        this.text = text;
    }

    @Override
    public void update(float dt) {}


    // Required by Entity abstract methods
    
    // Implementation for drawing text
    @Override
    public void renderShapes(ShapeRenderer renderer) {
    }
    
    @Override
    public void renderTextures(SpriteBatch batch) {
    }

}
