package com.dragonrider.tactics.gear;

import android.content.res.AssetManager;
import android.support.annotation.Nullable;

import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by mge637 on 27/05/2015.ttt
 */
public class Armor extends Wearable {

    private int ArmorValue;

    private String pathToGFX;
    TiledTextureRegion tiledTextureRegion;

    private Armor(String pathToMaleGFX, String pathToFemaleGFX, int armorValue) {
        super(pathToMaleGFX, pathToFemaleGFX);

        this.ArmorValue = armorValue;
    }


    public int getArmorValue() {
        return ArmorValue;
    }

    public Armor setArmorValue(int armorValue) {
        ArmorValue = armorValue;
        return this;
    }

    public ArmorType getArmorType() {
        return armorType;
    }

    public enum ArmorType {
        ARMOR_CHEST,
        ARMOR_HAT,
        ARMOR_LEGS,
        ARMOR_ARMS,
        ARMOR_FEET,
    }

    public enum ArmorClass {
        ARMOR_CLASS_CLOTH,
        ARMOR_CLASS_LEATHER,
        ARMOR_CLASS_CHAIN,
        ARMOR_CLASS_PLATE,
        ARMOR_CLASS_GOLD,
    }

    private static String getPathToGFX(ArmorType armorType, ArmorClass armorClass, boolean isMale) {
        String sReturn = "";

        switch (armorClass) {
            case ARMOR_CLASS_CLOTH:
                sReturn = "gfx/armors/normal/";
                break;
            case ARMOR_CLASS_LEATHER:
                sReturn = "gfx/armors/leather/";
                break;
            case ARMOR_CLASS_CHAIN:
                sReturn = "gfx/armors/chain/";
                break;
            case ARMOR_CLASS_PLATE:
                sReturn = "gfx/armors/plate/";
                break;
            case ARMOR_CLASS_GOLD:
                sReturn = "gfx/armors/gold/";
                break;
        }



        switch (armorType) {
            case ARMOR_CHEST:
                sReturn += "chest_" + (isMale ? "male.png" : "female.png");
                break;
            case ARMOR_HAT:
                sReturn += "hat_" + (isMale ? "male.png" : "female.png");
                break;
            case ARMOR_LEGS:
                sReturn += "legs_" + (isMale ? "male.png" : "female.png");
                break;
            case ARMOR_ARMS:
                sReturn += "arms_" + (isMale ? "male.png" : "female.png");
                break;
            case ARMOR_FEET:
                sReturn += "boots_" + (isMale ? "male.png" : "female.png");
                break;
        }




        return sReturn;
    }


    private static int getIconID(ArmorType armorType, ArmorClass armorClass) {
        if (armorType == ArmorType.ARMOR_CHEST && armorClass == ArmorClass.ARMOR_CLASS_CLOTH) return 182;
        if (armorType == ArmorType.ARMOR_CHEST && armorClass == ArmorClass.ARMOR_CLASS_LEATHER) return 184;
        if (armorType == ArmorType.ARMOR_CHEST && armorClass == ArmorClass.ARMOR_CLASS_CHAIN) return 185;
        if (armorType == ArmorType.ARMOR_CHEST && armorClass == ArmorClass.ARMOR_CLASS_PLATE) return 186;
        if (armorType == ArmorType.ARMOR_CHEST && armorClass == ArmorClass.ARMOR_CLASS_GOLD) return 187;

        if (armorType == ArmorType.ARMOR_ARMS && armorClass == ArmorClass.ARMOR_CLASS_CLOTH) return 182;
        if (armorType == ArmorType.ARMOR_CHEST && armorClass == ArmorClass.ARMOR_CLASS_LEATHER) return 182;
        if (armorType == ArmorType.ARMOR_CHEST && armorClass == ArmorClass.ARMOR_CLASS_CHAIN) return 182;
        if (armorType == ArmorType.ARMOR_CHEST && armorClass == ArmorClass.ARMOR_CLASS_PLATE) return 182;
        if (armorType == ArmorType.ARMOR_CHEST && armorClass == ArmorClass.ARMOR_CLASS_GOLD) return 182;

        return 0;
    }

    private ArmorType armorType;


    /**
     * Obtient une armure
     * @param isMale Determine le sexe du spritesheet
     * @param armorClass La classe d'armure
     * @param type Le type d'armure
     * @param ArmorValue La valeur d'armure
     * @param armorColor La couleur (pour les vetements)
     * @param vertexBufferObjectManager Reference
     * @param textureManager Reference
     * @param assetManager Reference
     * @param IconID ID de l'icone
     * @return l'objet armure, ou null si le GFX n'existe pas.
     */
    @Nullable
    public static Armor getItem(boolean isMale, ArmorClass armorClass, ArmorType type, int ArmorValue, Color armorColor, VertexBufferObjectManager vertexBufferObjectManager, TextureManager textureManager, AssetManager assetManager, int IconID) {
        String path = getPathToGFX(type, armorClass, isMale);

        try {
            assetManager.open(path);
        } catch (IOException e) {
            return null;
        }
        Armor armor = new Armor(isMale ? path : "", isMale ? "" : path, ArmorValue);
        armor.armorType = type;
        armor.createResources(textureManager, assetManager);
        armor.createIcon(IconID, vertexBufferObjectManager);
        armor.createSprite(vertexBufferObjectManager);
        if (armorClass == ArmorClass.ARMOR_CLASS_CLOTH)
            armor.mSprite.setColor(armorColor);
        return armor;
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
    public void createResources(String pathToFemaleGFX, TextureManager textureManager, AssetManager assetManager, int TextureWidth, int TextureHeight, int TextureCol, int TextureRow) {
        BitmapTextureAtlas mBehindBodyTexture = new BitmapTextureAtlas(textureManager,  TextureWidth, TextureHeight, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        tiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBehindBodyTexture, assetManager, pathToGFX, 0, 0, TextureCol, TextureRow);
        mBehindBodyTexture.load();
    }


}
