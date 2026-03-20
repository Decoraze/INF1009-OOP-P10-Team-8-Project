package com.p10.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

/**
 * FontManager centralizes font loading using FreeType.
 * Loads .otf/.ttf fonts at runtime — no bitmap conversion needed.
 * All scenes and UI classes share fonts from here (DRY + SRP).
 */
public class FontManager {
    private static BitmapFont titleFont;
    private static BitmapFont bodyFont;
    private static BitmapFont smallFont;
    private static boolean loaded = false;

    // : Static load() method to initialize fonts from a .otf file in assets/fonts/
    public static void load() {
        if (loaded)
            return;
        // Use chosen font file here
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Vipnagorgialla Bd.otf"));
        // : Generate fonts at different sizes for title, body, small text
        FreeTypeFontParameter titleParam = new FreeTypeFontParameter();
        titleParam.size = 20;
        titleFont = gen.generateFont(titleParam);
        // Set linear filtering for better scaling
        FreeTypeFontParameter bodyParam = new FreeTypeFontParameter();
        bodyParam.size = 14;
        bodyFont = gen.generateFont(bodyParam);

        FreeTypeFontParameter smallParam = new FreeTypeFontParameter();
        smallParam.size = 11;
        smallFont = gen.generateFont(smallParam);

        gen.dispose();
        loaded = true;
    }

    // : Static getters for each font, call load() to ensure fonts are initialized
    public static BitmapFont getTitle() {
        load();
        return titleFont;
    }

    // : Add getBody() and getSmall() similarly
    public static BitmapFont getBody() {
        load();
        return bodyFont;
    }

    // : Add getBody() and getSmall() similarly
    public static BitmapFont getSmall() {
        load();
        return smallFont;
    }

    // : Static dispose() method to clean up fonts when game exits
    public static void dispose() {
        if (titleFont != null)
            titleFont.dispose();
        if (bodyFont != null)
            bodyFont.dispose();
        if (smallFont != null)
            smallFont.dispose();
        loaded = false;
    }
}