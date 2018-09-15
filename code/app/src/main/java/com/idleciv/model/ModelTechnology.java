package com.idleciv.model;

import android.content.Context;

import com.idleciv.R;
import com.idleciv.model.modefier.ModifierUnlockIndustry;
import com.idleciv.model.modefier.ModifierUnlockResource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class ModelTechnology {

    public static final int Fire = 1;
    public static final int RopeMaking = 2;
    public static final int StoneWorking = 3;
    public static final int Hunting = 4;

    public static final int StoneTools = 5;
    public static final int CopperTools = 6;
    public static final int BronzeTools = 7;
    public static final int IronTools = 8;
    public static final int SteelTools = 9;
    public static final int MachinedTools = 10;


    public static final int BrickWorking = 11;
    public static final int Arches = 12;

    public static final int Tribalism = 13;


    public transient ModelEpochState mEpochState;
    public transient HashSet<Listener> mListenerSet;

    public int mTechnologyIndex;
    public int mIconResourceId;
    public int mNameResourceId;
    public int mDescriptionResourceId;
    public boolean mIsResearched;
    public int [] mRequiredTechnologyIndexArray;
    public ArrayList<ModelResourceAmount> mResourceCostList;
    public ArrayList<ModelModifier> mModifierList;

    public boolean mHasChanges = true;

    public ModelTechnology(
            ModelEpochState epochState,
            int technologyIndex,
            int [] requiredTechnologyIndexArray,
            ModelResourceAmount [] resourceCostArray,
            ModelModifier[] modifierArray,
            int iconResourceId,
            int nameResourceId,
            int descriptionResourceId) {
        mEpochState = epochState;
        mListenerSet = new HashSet<>();
        mTechnologyIndex = technologyIndex;
        mIsResearched = false;
        mRequiredTechnologyIndexArray = requiredTechnologyIndexArray;
        mResourceCostList = new ArrayList<ModelResourceAmount>(Arrays.asList(resourceCostArray));
        mModifierList = new ArrayList<ModelModifier>(Arrays.asList(modifierArray));
        mIconResourceId = iconResourceId;
        mNameResourceId = nameResourceId;
        mDescriptionResourceId = descriptionResourceId;
    }

    public void validate(ModelEpochState gameState) {
        mEpochState = gameState;
        mListenerSet = new HashSet<>();
        //Log.e("validate", "updateUI: " + mIndustryIndex);
    }
    public void research() {
        //Apply modifiers
        for (ModelModifier modifier: mModifierList) {
            modifier.apply(mEpochState);
        }
        //Set to researched
        mIsResearched = true;
        mEpochState.mHasChanges = true;
        mEpochState.updateUI();
    }

    public static ArrayList<ModelTechnology> getTechnologyList(ModelEpochState gameState){
        ArrayList<ModelTechnology> technologies = new ArrayList<>();


        technologies.add(new ModelTechnology(
                gameState,
                Fire,
                new int []{},
                new ModelResourceAmount[]{
                        new ModelResourceAmount(ModelResourceStock.Food, 50)
                },
                new ModelModifier[]{
                        new ModifierUnlockResource(ModelResourceStock.Grain),
                        new ModifierUnlockResource(ModelResourceStock.Lumber),
                        new ModifierUnlockIndustry(ModelIndustry.GatherGrain),
                        new ModifierUnlockIndustry(ModelIndustry.GatherLumber),
                        new ModifierUnlockIndustry(ModelIndustry.CookGrain)
                },
                R.drawable.technology_fire,
                R.string.technology_fire_name,
                R.string.technology_fire_description));

        technologies.add(new ModelTechnology(
                gameState,
                RopeMaking,
                new int []{},
                new ModelResourceAmount[]{
                        new ModelResourceAmount(ModelResourceStock.Food, 50)
                },
                new ModelModifier[]{
                        new ModifierUnlockResource(ModelResourceStock.Fibre),
                        new ModifierUnlockResource(ModelResourceStock.Rope),
                        new ModifierUnlockIndustry(ModelIndustry.GatherFibre),
                        new ModifierUnlockIndustry(ModelIndustry.RopeMaking),
                },
                R.drawable.resource_rope,
                R.string.technology_rope_making_name,
                R.string.technology_rope_making_description));

        technologies.add(new ModelTechnology(
                gameState,
                StoneWorking,
                new int []{Fire, RopeMaking},
                new ModelResourceAmount[]{
                        new ModelResourceAmount(ModelResourceStock.Food, 50)
                },
                new ModelModifier[]{
                        new ModifierUnlockIndustry(ModelIndustry.GatherStone),
                        new ModifierUnlockResource(ModelResourceStock.StoneTool),
                        new ModifierUnlockResource(ModelResourceStock.Stone)},
                R.drawable.resource_stone_tools,
                R.string.technology_stone_working_name,
                R.string.technology_stone_working_description));


        technologies.add(new ModelTechnology(
                gameState,
                StoneWorking,
                new int []{Fire, RopeMaking},
                new ModelResourceAmount[]{
                        new ModelResourceAmount(ModelResourceStock.Food, 50)
                },
                new ModelModifier[]{
                        new ModifierUnlockIndustry(ModelIndustry.GatherStone),
                        new ModifierUnlockResource(ModelResourceStock.StoneTool),
                        new ModifierUnlockResource(ModelResourceStock.Stone)},
                R.drawable.resource_stone_tools,
                R.string.technology_stone_working_name,
                R.string.technology_stone_working_description));


        technologies.add(new ModelTechnology(
                gameState,
                Hunting,
                new int []{Fire, RopeMaking, StoneWorking},
                new ModelResourceAmount[]{
                },
                new ModelModifier[]{
                        new ModifierUnlockIndustry(ModelIndustry.StoneWeaponCrafting),
                        new ModifierUnlockResource(ModelResourceStock.StoneWeapon)},
                R.drawable.placeholder,
                R.string.technology_hunting_name,
                R.string.technology_hunting_description));

        technologies.add(new ModelTechnology(
                gameState,
                CopperTools,
                new int []{StoneWorking,Fire},
                new ModelResourceAmount[]{
                        new ModelResourceAmount(ModelResourceStock.Food, 50)
                },
                new ModelModifier[]{
                        new ModifierUnlockIndustry(ModelIndustry.GatherOre),
                        new ModifierUnlockIndustry(ModelIndustry.WoodCutting)},
                R.drawable.resource_copper_tools,
                R.string.technology_copper_tools_name,
                R.string.technology_copper_tools_description));


        return technologies;
    }

    public int getIcon(){
        return mIconResourceId;
    }

    public String getName(Context context) {
        return context.getString(mNameResourceId);
    }

    public String getDescription(Context context) {
        return context.getString(mDescriptionResourceId);
    }


    public void updateUI() {
        if(mHasChanges) {
            for (ModelTechnology.Listener listener : mListenerSet) {
                listener.updateTechnologyUI();
            }
        }
    }

    public void addListener(ModelTechnology.Listener listener) {
        mListenerSet.add(listener);
        listener.updateTechnologyUI();
    }

    public void removeListener(ModelTechnology.Listener listener) {
        mListenerSet.remove(listener);
    }

    public void clear() {
        mListenerSet = new HashSet<>();
    }

    public boolean isVisible() {

        for (int prerequisiteTechnologyIndex: mRequiredTechnologyIndexArray) {
            if(!mEpochState.mTechnologyMap.get(prerequisiteTechnologyIndex).mIsResearched){
                return false;
            }
        }
        return true;
    }


    public interface Listener {
        void updateTechnologyUI();
    }
}