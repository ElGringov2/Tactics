package com.dragonrider.tactics.utils;

import android.content.res.AssetManager;

import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.FontManager;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.util.adt.color.Color;

/**
 * Created by dragonrider on 15/05/15.
 * Gere les polices de caratere
 */
public class Fonts {

    public static Font NormalBlackFont;
    public static Font BigBlackFont;
    public static Font SmallBlackFont;
    public static Font NormalWhiteFont;
    public static Font BigWhiteFont;
    public static Font SmallWhiteFont;

    public static void Init(float fElementHeight, TextureManager textureManager, FontManager fontManager, AssetManager assets) {
        BitmapTextureAtlas fontAtlas = new BitmapTextureAtlas(textureManager, 1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);




        NormalWhiteFont = FontFactory.createFromAsset(fontManager, textureManager, 1024, 1024, assets, "fonts/GOUDY.TTF", fElementHeight, true, Color.WHITE_ARGB_PACKED_INT);
        NormalWhiteFont.load();

        NormalBlackFont = FontFactory.createFromAsset(fontManager, textureManager, 1024, 1024, assets, "fonts/GOUDY.TTF", fElementHeight, true, Color.BLACK_ARGB_PACKED_INT);
        NormalBlackFont.load();


        BigWhiteFont = FontFactory.createFromAsset(fontManager, textureManager, 1024, 1024, assets, "fonts/GOUDY.TTF", fElementHeight * 1.3f, true, Color.WHITE_ARGB_PACKED_INT);
        BigWhiteFont.load();

        BigBlackFont = FontFactory.createFromAsset(fontManager, textureManager, 1024, 1024, assets, "fonts/GOUDY.TTF", fElementHeight * 1.3f, true, Color.BLACK_ARGB_PACKED_INT);
        BigBlackFont.load();


        SmallWhiteFont = FontFactory.createFromAsset(fontManager, textureManager, 1024, 1024, assets, "fonts/GOUDY.TTF", fElementHeight * 0.66f, true, Color.WHITE_ARGB_PACKED_INT);
        SmallWhiteFont.load();

        SmallBlackFont = FontFactory.createFromAsset(fontManager, textureManager, 1024, 1024, assets, "fonts/GOUDY.TTF", fElementHeight * 0.66f, true, Color.BLACK_ARGB_PACKED_INT);
        SmallBlackFont.load();
    }
}
