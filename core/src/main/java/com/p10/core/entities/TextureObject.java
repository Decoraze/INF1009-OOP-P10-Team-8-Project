package com.p10.core.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class TextureObject extends NonCollidableEntity {
    private Texture texture; //

    public TextureObject(String id, float x, float y, Texture texture) {
        super(id, x, y);
        this.texture = texture;
    }

    @Override
    public void update(float dt) {} // Usually static visuals

    
    // Required by Entity abstract methods
    @Override
    public void renderShapes(ShapeRenderer renderer) {	
    }
    
    @Override
    public void renderTextures(SpriteBatch batch) {
    	batch.draw(texture, getPosition().x, getPosition().y, getScale().x, getScale().y);
    }

}
