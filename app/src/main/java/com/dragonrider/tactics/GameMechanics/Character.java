package com.dragonrider.tactics.GameMechanics;

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




    public void LevelUpSkill(int SkillID) {
        float gain = getSkillValue(SkillID);

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



}
