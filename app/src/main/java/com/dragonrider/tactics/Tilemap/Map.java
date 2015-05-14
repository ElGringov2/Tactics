package com.dragonrider.tactics.Tilemap;

import android.content.res.AssetManager;

import com.dragonrider.tactics.utils.HeightMap;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.sprite.batch.SpriteBatch;
import org.andengine.entity.sprite.vbo.ISpriteVertexBufferObject;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by mge637 on 13/05/2015.
 * Gestion des map de combat
 */
public class Map {

    private int BaseTileID;
    private int SecondaryTileID;

    int Size;

    public Map(int baseTileID, int secondaryTileID, int Size) {
        BaseTileID = baseTileID;
        SecondaryTileID = secondaryTileID;
        this.Size = Size;
    }

    public HeightMap BaseLayer;
    public HeightMap TreeLayer;
    public HeightMap UpperLevelLayer;


    public String TilePath = "tile/terrain.png";

    private TiledTextureRegion mTextureRegion;

    public void CreateResources(TextureManager textureManager, AssetManager assetManager) {
        BitmapTextureAtlas mTexture = new BitmapTextureAtlas(textureManager,  1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        mTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mTexture, assetManager, TilePath, 0, 0, 32, 32);
        mTexture.load();

    }




    public void AttachToScene(Scene mScene, VertexBufferObjectManager vertexBufferObjectManager) {



        SpriteBatch spriteBatch = new SpriteBatch(mTextureRegion.getTexture(), Size * Size, vertexBufferObjectManager);

        for (int x = 0; x < Size; x++)
            for (int y = 0; y < Size; y++)
            {
               spriteBatch.draw(mTextureRegion.getTextureRegion(BaseTileID), x * 32, y * 32, 32, 32, 1.0f, 1.0f, 1.0f, 1.0f);
                if ((int)BaseLayer.get(x, y) != BaseTileID)
                    spriteBatch.draw(mTextureRegion.getTextureRegion((int)BaseLayer.get(x, y)), x * 32, y * 32, 32, 32, 1.0f, 1.0f, 1.0f, 1.0f);

            }

        spriteBatch.submit();
        mScene.attachChild(spriteBatch);
        mScene.registerTouchArea(spriteBatch);
    }
}
