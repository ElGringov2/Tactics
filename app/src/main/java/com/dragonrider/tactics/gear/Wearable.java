package com.dragonrider.tactics.gear;

import android.content.res.AssetManager;

import com.dragonrider.tactics.entity.Entity;

import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by mge637 on 13/05/2015.l
 */
public class Wearable extends Gear {
    // Dans l'ordre de 0 a 5, avec 2 = character
    public int DrawOrder = 3;

    private TiledTextureRegion mTextureRegion;
    private AnimatedSprite mSprite;

    private String pathToGFX = "";


    public Wearable(String PathToGFX, int DrawOrder) {
        this.pathToGFX = PathToGFX;
        this.DrawOrder = DrawOrder;
    }

    public void CreateResources(TextureManager textureManager, AssetManager assetManager) {
        BitmapTextureAtlas mBehindBodyTexture = new BitmapTextureAtlas(textureManager,  832, 1344, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        mTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBehindBodyTexture, assetManager, pathToGFX, 0, 0, 13, 21);
        mBehindBodyTexture.load();


    }


    public AnimatedSprite CreateSprite(float PositionX, float PositionY, VertexBufferObjectManager vertexBufferObjectManager) {
        mSprite = new AnimatedSprite(PositionX, PositionY, mTextureRegion, vertexBufferObjectManager);
        return mSprite;
    }


    public void setPosition(float PositionX, float PositionY) {
        mSprite.setPosition(PositionX, PositionY);
    }


    public void Animate(Entity.ANIM_STATE animation, Entity.ORIENTATION orientation) {

    }

    public void Animate(long[] Duration, int[] IDs) {
        mSprite.animate(Duration, IDs);
    }
}
