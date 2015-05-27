package com.dragonrider.tactics.gear;

import android.content.res.AssetManager;
import android.util.Size;

import com.dragonrider.tactics.entity.Entity;

import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.Arrays;

/**
 * Created by dragonrider on 26/05/15.
 * Gestion des armes
 */
public class MeleeWeapon extends Wearable {

    /**
     * Dégâts de l'arme, compris entre 0 et 5
     */
    private int Damage ;

    /**
     * Difficulté minimum (l'attaque ne pourra pas se faire avec une difficulté inférieure
     */
    private int MinDifficulty;

    /**
     * Difficulté maximum (l'attaque ne pourra pas se faire avec une difficulté supérieure
     */
    private int MaxDifficulty;

    /**
     * Type d'arme. Immuable.
     */
    private MeleeWeaponType meleeWeaponType;


    private MeleeWeapon(String PathToMaleGFX, String PathToFemaleGFX, int damage, int minDifficulty, int maxDifficulty, MeleeWeaponType meleeWeaponType) {
        super(PathToMaleGFX, PathToFemaleGFX);
        Damage = damage;
        MinDifficulty = minDifficulty;
        MaxDifficulty = maxDifficulty;


        this.meleeWeaponType = meleeWeaponType;
    }

    public int getDamage() {
        return Damage;
    }

    public void setDamage(int damage) {
        Damage = damage;
    }

    public int getMinDifficulty() {
        return MinDifficulty;
    }

    public void setMinDifficulty(int minDifficulty) {
        MinDifficulty = minDifficulty;
    }

    public int getMaxDifficulty() {
        return MaxDifficulty;
    }

    public void setMaxDifficulty(int maxDifficulty) {
        MaxDifficulty = maxDifficulty;
    }

    public MeleeWeaponType getMeleeWeaponType() {
        return meleeWeaponType;
    }

    public enum MeleeWeaponType {
        WEAPON_SWORD,
        WEAPON_MACE,
        WEAPON_SPEAR,
        WEAPON_DAGGER,
        WEAPON_SABER,
        WEAPON_RAPIER,


    }

    public static MeleeWeapon getWeapon(boolean isMale, MeleeWeaponType type, int damage, int minDifficulty, int maxDifficulty, VertexBufferObjectManager vertexBufferObjectManager, TextureManager textureManager, AssetManager assetManager) {
        String pathMale = "";
        String pathFemale = "";
        boolean Standard = false;


        switch (type) {
            case WEAPON_SWORD:
                pathMale = "gfx/weapons/longsword_male.png";
                pathFemale = "gfx/weapons/longsword_female.png";
                break;
            case WEAPON_MACE:
                pathMale = "gfx/weapons/mace_male.png";
                pathFemale = "gfx/weapons/mace_female.png";
                break;
            case WEAPON_SPEAR:
                pathMale = "gfx/weapons/spear_male.png";
                pathFemale = "gfx/weapons/spear_female.png";
                Standard = true;
                break;
            case WEAPON_DAGGER:
                pathMale = "gfx/weapons/dagger_male.png";
                pathFemale = "gfx/weapons/dagger_female.png";
                Standard = true;
                break;
            case WEAPON_SABER:
                pathMale = "gfx/weapons/saber_male.png";
                pathFemale = "gfx/weapons/saber_female.png";
                break;
            case WEAPON_RAPIER:
                pathMale = "gfx/weapons/rapier_male.png";
                pathFemale = "gfx/weapons/rapier_female.png";
                break;
        }

        MeleeWeapon weapon = new MeleeWeapon(isMale ? pathMale : "", isMale ? "" : pathFemale, damage, minDifficulty, maxDifficulty, type);

        if (Standard)
            weapon.createResources(textureManager, assetManager);
        else
            weapon.createResources(textureManager, assetManager, 1152, 768, 6, 4);

        weapon.createSprite(vertexBufferObjectManager);



        return weapon;
    }


    @Override
    public void Animate(long[] Duration, int[] IDs) {





        if (this.meleeWeaponType != MeleeWeaponType.WEAPON_DAGGER && this.meleeWeaponType != MeleeWeaponType.WEAPON_SPEAR) {

            this.getSprite().setVisible(true);

            if (Arrays.equals(IDs, Entity.GetFramesID(Entity.ANIM_STATE.ATTACK_SLASH, Entity.ORIENTATION.NORTH)))
                IDs = new int[]{0, 1, 2, 3, 4, 5};
            else if (Arrays.equals(IDs, Entity.GetFramesID(Entity.ANIM_STATE.ATTACK_SLASH, Entity.ORIENTATION.WEST)))
                IDs = new int[]{6, 7, 8, 9, 10, 11};
            else if (Arrays.equals(IDs, Entity.GetFramesID(Entity.ANIM_STATE.ATTACK_SLASH, Entity.ORIENTATION.SOUTH)))
                IDs = new int[]{12, 13, 14, 15, 16, 17};
            else if (Arrays.equals(IDs, Entity.GetFramesID(Entity.ANIM_STATE.ATTACK_SLASH, Entity.ORIENTATION.EAST)))
                IDs = new int[]{18, 19, 20, 21, 22, 23};
            else { //Pas d'animation possible.
                this.getSprite().setVisible(false);
                return;
            }
        }


        super.Animate(Duration, IDs);
    }
}
