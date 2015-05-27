package com.dragonrider.tactics.gear;

import android.content.res.AssetManager;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by mge637 on 13/05/2015.
 * Gere les objets
 */
public abstract class Item {
    public float Weight = 0.1f;
    public int StackCount = 1;
    public int MaxStack = 50;






    protected Sprite Icon;



    protected Sprite createIcon(int pIconIndex, VertexBufferObjectManager vbom) {
        Icon = new Sprite(0, 0, mIconTextureRegion.getTextureRegion(pIconIndex), vbom);
        return Icon;
    }




    /**
     * Obtient l'icone
     * @return L'icone
     */
    public Sprite getIcon() {
        return Icon;
    }

    /**
     * Stocke les icones
     */
    protected static TiledTextureRegion mIconTextureRegion;

    /**
     * Initialise les icons
     * @param textureManager Une reference de Texture Manager
     * @param assetManager Une reference d'Asset Manager
     */
    public static void InitializeTextureRegion(TextureManager textureManager, AssetManager assetManager) {
        BitmapTextureAtlas atlas = new BitmapTextureAtlas(textureManager, 120,120);
        mIconTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(atlas, assetManager, "gfx/icons.png", 0,0,10,10);
        atlas.load();
    }
}
