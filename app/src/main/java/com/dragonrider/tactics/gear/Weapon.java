package com.dragonrider.tactics.gear;

import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dragonrider.tactics.entity.Entity;

import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.Arrays;

/**
 * Created by dragonrider on 26/05/15.
 * Gestion des armes
 */
public class Weapon extends Wearable {

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
    private WeaponType weaponType;


    private Weapon(String PathToMaleGFX, String PathToFemaleGFX, int damage, int minDifficulty, int maxDifficulty, WeaponType weaponType) {
        super(PathToMaleGFX, PathToFemaleGFX);
        Damage = damage;
        MinDifficulty = minDifficulty;
        MaxDifficulty = maxDifficulty;


        this.weaponType = weaponType;
    }

    public int getDamage() {
        return Damage;
    }

    public Weapon setDamage(int damage) {
        Damage = damage;
        return this;
    }

    public int getMinDifficulty() {
        return MinDifficulty;
    }

    public Weapon setMinDifficulty(int minDifficulty) {
        MinDifficulty = minDifficulty;
        return this;
    }

    public int getMaxDifficulty() {
        return MaxDifficulty;
    }

    public Weapon setMaxDifficulty(int maxDifficulty) {
        MaxDifficulty = maxDifficulty;
        return this;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public enum WeaponType {
        WEAPON_SWORD,
        WEAPON_MACE,
        WEAPON_SPEAR,
        WEAPON_DAGGER,
        WEAPON_SABER,
        WEAPON_RAPIER,
        WEAPON_BOW,
        WEAPON_LONGBOW,
        WEAPON_HUNTINGBOW,
        WEAPON_ARROW,


    }


    /**
     * Obtient une arme
     * @param isMale Determine le sexe de la spritesheet
     * @param type Type d'arme
     * @param damage Dégats
     * @param minDifficulty Diff min
     * @param maxDifficulty Diff max
     * @param vertexBufferObjectManager Reference
     * @param textureManager Reference
     * @param assetManager Reference
     * @param IconID Id de l'icone
     * @return L'arme.
     */
    @NonNull
    public static Weapon getItem(boolean isMale, WeaponType type, int damage, int minDifficulty, int maxDifficulty, VertexBufferObjectManager vertexBufferObjectManager, TextureManager textureManager, AssetManager assetManager, int IconID) {
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
            case WEAPON_BOW:
                pathMale = "gfx/weapons/bow.png";
                pathFemale = "gfx/weapons/bow.png";
                Standard = true;
                break;
            case WEAPON_LONGBOW:
                pathMale = "gfx/weapons/greatbow.png";
                pathFemale = "gfx/weapons/greatbow.png";
                Standard = true;
                break;
            case WEAPON_HUNTINGBOW:
                pathMale = "gfx/weapons/recurvebow.png";
                pathFemale = "gfx/weapons/recurvebow.png";
                Standard = true;
                break;
            case WEAPON_ARROW:
                pathMale = "gfx/weapons/arrow.png";
                pathFemale = "gfx/weapons/arrow.png";
                Standard = true;
                break;

        }


        Weapon weapon = new Weapon(isMale ? pathMale : "", isMale ? "" : pathFemale, damage, minDifficulty, maxDifficulty, type);

        if (Standard)
            weapon.createResources(textureManager, assetManager);
        else
            weapon.createResources(textureManager, assetManager, 1152, 768, 6, 4);

        weapon.createSprite(vertexBufferObjectManager);
        weapon.createIcon(IconID, vertexBufferObjectManager);



        return weapon;
    }


    @Override
    public void Animate(long[] Duration, int[] IDs) {

        if (this.weaponType != WeaponType.WEAPON_DAGGER && this.weaponType != WeaponType.WEAPON_SPEAR) {

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
