package com.dragonrider.tactics.ui.hud;

import android.content.res.AssetManager;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.batch.SpriteBatch;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;

/**
 * Created by dragonrider on 15/05/15. pouet
 */
public class CustomHUD extends HUD {
    public CustomHUD(VertexBufferObjectManager vbom, TextureManager textureManager, AssetManager assetManager, int ScreenWidth, int ScreenHeight) {
        BitmapTextureAtlas textureAtlas = new BitmapTextureAtlas(textureManager, 544, 320, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        TiledTextureRegion region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAtlas, assetManager, "ui/ui.png", 0, 0, 17, 10);
        textureAtlas.load();


        int iHudSize = ScreenHeight - ScreenWidth;
        int iCapacity = (iHudSize / 32) * (ScreenWidth / 32);




        spriteBatch = new SpriteBatch(0, 0, textureAtlas, iCapacity, vbom);

        for (int i = 0; i < ScreenWidth / 32; i++)
            for (int j = 0; j < iHudSize / 32; j++) {
                if (i == 0 && j == 0)
                    spriteBatch.draw(region.getTextureRegion(160), i * 32, j * 32, 32, 32, 1f, 1f, 1f, 1f);
                else if (i == 0 && j == iHudSize / 32 - 1)
                    spriteBatch.draw(region.getTextureRegion(126), i * 32, j * 32, 32, 32, 1f, 1f, 1f, 1f);
                else if (i == 0)
                    spriteBatch.draw(region.getTextureRegion(143), i * 32, j * 32, 32, 32, 1f, 1f, 1f, 1f);
                else if (i == ScreenWidth / 32 - 1 && j == iHudSize / 32 - 1)
                    spriteBatch.draw(region.getTextureRegion(128), i * 32, j * 32, 32, 32, 1f, 1f, 1f, 1f);
                else if (i == ScreenWidth / 32 - 1 && j == 0)
                    spriteBatch.draw(region.getTextureRegion(162), i * 32, j * 32, 32, 32, 1f, 1f, 1f, 1f);
                else if (i == ScreenWidth / 32 - 1)
                    spriteBatch.draw(region.getTextureRegion(145), i * 32, j * 32, 32, 32, 1f, 1f, 1f, 1f);
                else if (j == 0)
                    spriteBatch.draw(region.getTextureRegion(161), i * 32, j * 32, 32, 32, 1f, 1f, 1f, 1f);
                else if (j == iHudSize / 32 - 1)
                    spriteBatch.draw(region.getTextureRegion(127), i * 32, j * 32, 32, 32, 1f, 1f, 1f, 1f);

                else
                    spriteBatch.draw(region.getTextureRegion(144), i * 32, j * 32, 32, 32, 1f, 1f, 1f, 1f);
            }
        spriteBatch.submit();

        this.attachChild(spriteBatch);
        this.registerTouchArea(spriteBatch);

    }

    private SpriteBatch spriteBatch;
}
