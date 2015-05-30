package com.dragonrider.tactics.GameMechanics;

import android.app.AlarmManager;
import android.support.annotation.Nullable;

import com.dragonrider.tactics.gear.Armor;
import com.dragonrider.tactics.gear.Item;
import com.dragonrider.tactics.gear.Weapon;
import com.dragonrider.tactics.gear.Wearable;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by dragonrider on 17/05/15.
 */
public class Character {
    private String Name = "Personnage";
    private int Endurance = 10;

    private List<Wound> Wounds = new ArrayList<>();

    public Armor getChestArmor() {
        return chestArmor;
    }

    public void setChestArmor(Armor chestArmor) {
        this.chestArmor = chestArmor;
    }

    public Armor getLegArmor() {
        return legArmor;
    }

    public void setLegArmor(Armor legArmor) {
        this.legArmor = legArmor;
    }

    public Armor getFeetArmor() {
        return feetArmor;
    }

    public void setFeetArmor(Armor feetArmor) {
        this.feetArmor = feetArmor;
    }

    public Armor getArmArmor() {
        return armArmor;
    }

    public void setArmArmor(Armor armArmor) {
        this.armArmor = armArmor;
    }

    public Armor getHatArmor() {
        return hatArmor;
    }

    public void setHatArmor(Armor hatArmor) {
        this.hatArmor = hatArmor;
    }

    public Weapon getPrimaryHand() {
        return primaryHand;
    }

    public void setPrimaryHand(Weapon primaryHand) {
        this.primaryHand = primaryHand;
    }

    public Weapon getSecondaryHand() {
        return secondaryHand;
    }

    public void setSecondaryHand(Weapon secondaryHand) {
        this.secondaryHand = secondaryHand;
    }




    public enum Skills {
        ARCHERY,
        SWORD,
        MACE,
        DESTRUCTION,
        RESTORATION,
        NECROMANCY,
        SURVIVAL,
        RESISTANCE,
        CARPENTER,
        WEAPONSMITH,
        ARMORSMITH

    }

    private Float[] SkillsExperience;

    public Character() {
        this.SkillsExperience = new Float[Skills.values().length];

    }


    /**
     * Obtient l'experience dans une compétence
     * @param SkillID L'ID du skill dans l'enum Skills
     * @return la valeur en float de l'experience comprise entre 0.0f et 1.0f
     */
    public float getExperience(int SkillID) {
        return Math.round((double)this.SkillsExperience[SkillID] - Math.floor(this.SkillsExperience[SkillID]));
    }


    /**
     * Obtient la valeur de la compétence
     * @param SkillID L'ID du skill dans l'enum Skills
     * @return la valeur en int de la compétence
     */
    public int getSkillValue(int SkillID) {
        return (int)Math.floor(this.SkillsExperience[SkillID]);
    }




    public void LevelUpSkill(int SkillID, int Difficulty) {
        float value = getSkillValue(SkillID);
        int gain = -Difficulty;

        this.SkillsExperience[SkillID] += gain;


    }

    public void CriticalLevelUpSkill(int SkillID) {

    }

    public enum ActionRating {
        CRITICAL_FAILURE,
        SPECIAL_FAILURE,
        FAILURE,
        SUCCESS,
        SPECIAL_SUCCESS,
        CRITICAL_SUCCESS,
    }

    public ActionRating getSkillRoll(int SkillID, int seed) {
        Random r = new Random(seed);
        int score = r.nextInt(99) + 1;

        int value = getSkillValue(SkillID);

        if (score == 1) return ActionRating.CRITICAL_SUCCESS;
        if (score <= value / 5) return ActionRating.CRITICAL_SUCCESS;
        if (score <= value / 2) return ActionRating.SPECIAL_SUCCESS;
        if (score <= value) return ActionRating.SUCCESS;
        if (score < 100 - (100 - value) / 2) return ActionRating.FAILURE;
        if (score < 100 - (100 - value) / 5) return ActionRating.SPECIAL_FAILURE;
        return ActionRating.CRITICAL_FAILURE;
    }


    private Armor chestArmor;
    private Armor legArmor;
    private Armor feetArmor;
    private Armor armArmor;
    private Armor hatArmor;

    private Weapon primaryHand;
    private Weapon secondaryHand;


    private List<Item> mInventory = new ArrayList<>();

    public List<Item> getInventory() {
        return mInventory;
    }


    public List<Wearable> getAllWearables() {
        ArrayList<Wearable> aReturn = new ArrayList<>();

        aReturn.add(chestArmor);
        aReturn.add(legArmor);
        aReturn.add(feetArmor);
        aReturn.add(armArmor);
        aReturn.add(hatArmor);
        aReturn.add(primaryHand);
        aReturn.add(secondaryHand);


        return aReturn;
    }


    public void Equip(Wearable item) {

        if (mInventory.contains(item))
            mInventory.remove(item);



        if (item instanceof Armor) {
            if (((Armor)item).getArmorType() == Armor.ArmorType.ARMOR_ARMS)
            {
                Unequip(armArmor);
                armArmor = (Armor)item;
            }
            if (((Armor)item).getArmorType() == Armor.ArmorType.ARMOR_HAT)
            {
                Unequip(hatArmor);
                hatArmor = (Armor)item;
            }
            if (((Armor)item).getArmorType() == Armor.ArmorType.ARMOR_CHEST)
            {
                Unequip(chestArmor);
                chestArmor = (Armor)item;
            }
            if (((Armor)item).getArmorType() == Armor.ArmorType.ARMOR_LEGS)
            {
                Unequip(legArmor);
                legArmor = (Armor)item;
            }
            if (((Armor)item).getArmorType() == Armor.ArmorType.ARMOR_FEET)
            {
                Unequip(feetArmor);
                feetArmor = (Armor)item;
            }
        }


    }


    public void Unequip(Wearable item) {

        if (item == null) return;

        if (item.equals(armArmor))
        {
            mInventory.add(item);
            armArmor = null;
        }
        if (item.equals(hatArmor))
        {
            mInventory.add(item);
            hatArmor = null;
        }
        if (item.equals(chestArmor))
        {
            mInventory.add(item);
            chestArmor = null;
        }
        if (item.equals(legArmor))
        {
            mInventory.add(item);
            legArmor = null;
        }
        if (item.equals(feetArmor))
        {
            mInventory.add(item);
            feetArmor = null;
        }

        if (item.equals(primaryHand))
        {
            mInventory.add(item);
            primaryHand = null;
        }
        if (item.equals(secondaryHand))
        {
            mInventory.add(item);
            secondaryHand = null;
        }

    }


    public void addItem(Item item) {

        mInventory.add(item);
        //TODO Poids de l'item
    }
}
