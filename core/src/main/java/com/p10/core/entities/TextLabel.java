package com.p10.core.entities;

public class TextLabel extends NonCollidableEntity {
    private String text; //

    public TextLabel(String id, float x, float y, String text) {
        super(id, x, y);
        this.text = text;
    }

    @Override
    public void update(float dt) {}

    @Override
    public void render() {
        // Implementation for drawing text
    }


}
