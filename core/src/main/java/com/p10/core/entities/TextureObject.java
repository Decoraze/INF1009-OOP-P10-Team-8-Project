package com.p10.core.entities;

import com.badlogic.gdx.graphics.Texture;

public class TextureObject extends NonCollidableEntity {
    private Texture texture; //

    public TextureObject(String id, float x, float y, Texture texture) {
        super(id, x, y);
        this.texture = texture;
    }

    @Override
    public void update(float dt) {} // Usually static visuals

    @Override
    public void render() {
        // implementation will call SpriteBatch in the Manager
    }

}
