package com.dragonrider.tactics.gear;

import android.content.res.AssetManager;

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
public class Wearable extends Item {

    private TiledTextureRegion mMaleTextureRegion;
    private TiledTextureRegion mFemaleTextureRegion;
    protected AnimatedSprite mSprite;

    private String pathToMaleGFX;
    private String pathToFemaleGFX;




    public Wearable(String pathToMaleGFX, String pathToFemaleGFX) {
        this.pathToMaleGFX = pathToMaleGFX;
        this.pathToFemaleGFX = pathToFemaleGFX;
    }

    /**
     * Crée la ressource du sprite avec des valeurs personnalisées
     * @param textureManager Une reference vers le texturemanager
     * @param assetManager Une reference vers l'asset manager
     * @param TextureWidth Largeur de la texture
     * @param TextureHeight Hauteur de la textures
     * @param TextureCol Nombre de sprite en colonnes
     * @param TextureRow Nombre de sprite en lignes
     */
    public void createResources(TextureManager textureManager, AssetManager assetManager, int TextureWidth, int TextureHeight, int TextureCol, int TextureRow) {
        BitmapTextureAtlas mBehindBodyTexture = new BitmapTextureAtlas(textureManager,  TextureWidth, TextureHeight, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        if (pathToFemaleGFX.equals(""))
            mMaleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBehindBodyTexture, assetManager, pathToMaleGFX, 0, 0, TextureCol, TextureRow);
        else
            mFemaleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBehindBodyTexture, assetManager, pathToFemaleGFX, 0, 0, TextureCol, TextureRow);
        mBehindBodyTexture.load();
    }

    /**
     * Crée la ressource du sprite avec des valeurs standard (832px*1344px avec 13*21 sprite)
     * @param textureManager Une reference vers le texturemanager
     * @param assetManager Une reference vers l'asset manager
     */
    public void createResources(TextureManager textureManager, AssetManager assetManager) {
        createResources(textureManager, assetManager, 832, 1344, 13, 21);
    }



    /**
     * Crée le sprite animé a une position spéciale
     * @param pPositionX Coordonée X
     * @param pPositionY Coordonée Y
     * @param vertexBufferObjectManager Reference du VBOM
     * @return Le sprite en question (accessible par la suite par getSprite();
     */
    public AnimatedSprite createSprite(float pPositionX, float pPositionY, VertexBufferObjectManager vertexBufferObjectManager) {

        if (pathToFemaleGFX.equals(""))
            return CreateMaleSprite(pPositionX, pPositionY, vertexBufferObjectManager);
        else
            return CreateFemaleSprite(pPositionX, pPositionY, vertexBufferObjectManager);
    }


    /**
     * Crée le sprite animé
     * @param vertexBufferObjectManager Reference du VBOM
     * @return Le sprite en question (accessible par la suite par getSprite();
     */
    public AnimatedSprite createSprite(VertexBufferObjectManager vertexBufferObjectManager) {
        if (pathToFemaleGFX.equals(""))
            return CreateMaleSprite(0, 0, vertexBufferObjectManager);
        else
            return CreateFemaleSprite(0, 0, vertexBufferObjectManager);
    }


    /**
     * Obtient le sprite, pour peu qu'ils soit au préalable crée avec createSprite
     * @return le sprite en question
     */
    public AnimatedSprite getSprite() {
        return this.mSprite;
    }


    /**
     * Crée le sprite pour un homme
     * @param PositionX Position X
     * @param PositionY Position Y
     * @param vertexBufferObjectManager Reference du VBOM
     * @return Le sprite.
     */
    private AnimatedSprite CreateMaleSprite(float PositionX, float PositionY, VertexBufferObjectManager vertexBufferObjectManager) {
        mSprite = new AnimatedSprite(PositionX, PositionY, mMaleTextureRegion, vertexBufferObjectManager);
        return mSprite;
    }
    /**
     * Crée le sprite pour une femme
     * @param PositionX Position X
     * @param PositionY Position Y
     * @param vertexBufferObjectManager Reference du VBOM
     * @return Le sprite.
     */
    private AnimatedSprite CreateFemaleSprite(float PositionX, float PositionY, VertexBufferObjectManager vertexBufferObjectManager) {
        mSprite = new AnimatedSprite(PositionX, PositionY, mFemaleTextureRegion, vertexBufferObjectManager);
        return mSprite;
    }


    /**
     * Anime le sprite
     * @param Duration Durée d'animation (de chaque frame)
     * @param IDs Liste des frames
     */
    public void Animate(long[] Duration, int[] IDs) {
        mSprite.animate(Duration, IDs);
    }
}
