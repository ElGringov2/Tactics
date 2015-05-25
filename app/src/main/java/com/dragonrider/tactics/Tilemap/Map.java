package com.dragonrider.tactics.Tilemap;

import android.content.res.AssetManager;

import com.dragonrider.tactics.utils.LayerMap;
import com.dragonrider.tactics.utils.TileInfo;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.batch.SpriteBatch;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by mge637 on 13/05/2015.
 * Gestion des map de combat
 */
public class Map {


    int Size;


    public Map(int Size) {
        this.Size = Size;

    }

    public LayerMap BaseLayer;
    public LayerMap TreeLayer;
    public LayerMap UpperLevelLayer;


    public String TilePath = "tile/terrain.png";

    private TiledTextureRegion mTextureRegion;

    public void CreateResources(TextureManager textureManager, AssetManager assetManager) {
        BitmapTextureAtlas mTexture = new BitmapTextureAtlas(textureManager,  1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        mTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mTexture, assetManager, TilePath, 0, 0, 32, 32);
        mTexture.load();

    }




    public void AttachBaseLayerToScene(Scene mScene, VertexBufferObjectManager vertexBufferObjectManager) {



        SpriteBatch spriteBatch = new SpriteBatch(mTextureRegion.getTexture(), BaseLayer.getFullSize() + TreeLayer.getFullSize(), vertexBufferObjectManager);

        for (int x = 1; x < Size - 2; x++)
            for (int y = 1; y < Size - 2; y++)
            {

                for (TileInfo info : BaseLayer.get(x, y))
                    spriteBatch.draw(mTextureRegion.getTextureRegion(info.getTileID()), x * 32, y * 32, 32, 32, 1.0f, 1.0f, 1.0f, 1.0f);




                for (TileInfo info : TreeLayer.get(x, y))
                    spriteBatch.draw(mTextureRegion.getTextureRegion(info.getTileID()), x * 32, y * 32, 32, 32, 1.0f, 1.0f, 1.0f, 1.0f);

            }

        spriteBatch.submit();
        mScene.attachChild(spriteBatch);
        mScene.registerTouchArea(spriteBatch);



    }


    public void AttachTopLayerToScene(Scene mScene, VertexBufferObjectManager vertexBufferObjectManager) {


        SpriteBatch spriteBatch = new SpriteBatch(mTextureRegion.getTexture(), UpperLevelLayer.getFullSize() , vertexBufferObjectManager);

        for (int x = 1; x < Size - 2; x++)
            for (int y = 1; y < Size - 2; y++) {

                for (TileInfo info : UpperLevelLayer.get(x, y))
                    spriteBatch.draw(mTextureRegion.getTextureRegion(info.getTileID()), x * 32, y * 32, 32, 32, 1.0f, 1.0f, 1.0f, 1.0f);


            }

        spriteBatch.submit();
        mScene.attachChild(spriteBatch);
        mScene.registerTouchArea(spriteBatch);
    }

//    public int getGridSize() {
//        return gridSize;
//    }

//
//
//    public float getMapWeight(int pX, int pY) {
//
//
//
//
//        float fWeight = this.BaseLayer.get(pX * 2 + 1, pY * 2 + 1) + this.BaseLayer.get(pX * 2 + 1, pY * 2 + 2)  + this.BaseLayer.get(pX * 2 + 2, pY * 2 + 1)  + this.BaseLayer.get(pX * 2 + 2, pY * 2 + 2);
//
//
//        fWeight += this.TreeLayer.get(pX * 2 + 1, pY * 2 + 1) + this.TreeLayer.get(pX * 2 + 1, pY * 2 + 2)  + this.TreeLayer.get(pX * 2 + 2, pY * 2 + 1)  + this.TreeLayer.get(pX * 2 + 2, pY * 2 + 2);
//        fWeight += this.UpperLevelLayer.get(pX * 2 + 1, pY * 2 + 1) + this.UpperLevelLayer.get(pX * 2 + 1, pY * 2 + 2)  + this.UpperLevelLayer.get(pX * 2 + 2, pY * 2 + 1)  + this.UpperLevelLayer.get(pX * 2 + 2, pY * 2 + 2);
//
//
//        return fWeight;
//
//
//
//
//    }
}
