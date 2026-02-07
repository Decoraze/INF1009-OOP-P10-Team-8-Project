package com.p10.core.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class Shape extends CollidableEntity {
    protected Color color; //
    protected boolean filled; //

    public Shape(String id, float x, float y, float w, float h, Color color) {
        super(id, x, y, w, h);
        this.color = color;
        this.filled = true;
    }

    public abstract void draw(ShapeRenderer renderer); //
}
