package com.idleciv.model;

import com.idleciv.R;
import com.idleciv.model.modefier.ModifierAddPercent;
import com.idleciv.model.modefier.ModifierUnlockIndustry;
import com.idleciv.model.modefier.ModifierUnlockResource;

import java.util.ArrayList;
import java.util.HashSet;

public class ModelTechnology {

    public static final int PrimitiveTools = 0;
    public static final int CopperTools = 1;
    public static final int BronzeTools = 2;
    public static final int IronTools = 3;
    public static final int SteelTools = 4;
    public static final int MachinedTools = 5;



    public static final int Archery = 6;
    public static final int Arches = 7;
    public static final int StoneWorking = 8;
    public static final int BrickWorking = 9;

    public transient ModelGameState mGameState;
    public transient HashSet<Listener> mListenerSet;

    public int mTechnologyIndex;
    public boolean mIsResearched;
    public ArrayList<IModelModifier> mModifierList;

    public boolean mHasChanges = true;

    public ModelTechnology(ModelGameState gameState, int technologyIndex, boolean isResearched, ArrayList<IModelModifier> modifiers) {
        mGameState = gameState;
        mListenerSet = new HashSet<>();
        mTechnologyIndex = technologyIndex;
        mIsResearched = isResearched;
        mModifierList = modifiers;
    }
    public void validate(ModelGameState gameState) {
        mGameState = gameState;
        mListenerSet = new HashSet<>();
        //Log.e("validate", "updateUI: " + mIndustryIndex);
    }
    public void research() {
        //Apply modifiers
        for (IModelModifier modifier: mModifierList) {
            modifier.apply(mGameState);
        }
        //Set to researched
        mIsResearched = true;
        mGameState.mHasChanges = true;
        mGameState.updateUI();
    }

    public static ArrayList<ModelTechnology> getTechnologyList(ModelGameState gameState){
        ArrayList<ModelTechnology> technologies = new ArrayList<>();

        ArrayList<IModelModifier> modifier0 = new ArrayList<>();
        modifier0.add(new ModifierAddPercent(100, ModelIndustry.GatherFood));
        modifier0.add(new ModifierUnlockIndustry(ModelIndustry.GatherLumber));

        technologies.add(new ModelTechnology(gameState, PrimitiveTools, false, modifier0));

        ArrayList<IModelModifier> modifier1 = new ArrayList<>();
        technologies.add(new ModelTechnology(gameState, CopperTools, false, modifier1));

        ArrayList<IModelModifier> modifier2 = new ArrayList<>();
        modifier2.add(new ModifierUnlockIndustry(ModelIndustry.GatherStone));
        modifier2.add(new ModifierUnlockResource(ModelResourceStock.Stone));
        technologies.add(new ModelTechnology(gameState, StoneWorking, false, modifier2));
        return technologies;
    }

    public static int getIcon(int technologyIndex){
        switch (technologyIndex){
            case PrimitiveTools:
                return R.drawable.scythe;
            case CopperTools:
                return R.drawable.scythe;
            default:
                return R.drawable.placeholder;
        }
    }

    public static String getName(int technologyIndex) {
        switch(technologyIndex){
            case PrimitiveTools:
                return "Primitive tools";
            case CopperTools:
                return "Copper tools";
            case StoneWorking:
                return "Stone working";
            default:
                return "Unnamed technology with index: "+ technologyIndex;
        }
    }

    public static String getDescription(int technologyIndex) {
        switch(technologyIndex){
            case PrimitiveTools:
                return "Allows simple farming and stoneworking";
            case CopperTools:
                return "Not the police";
            case StoneWorking:
                return "The limited working of stone enabling the production of stone houses";
            default:
                return "Undescribed technology with index: "+ technologyIndex;
        }
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


    public interface Listener {
        void updateTechnologyUI();
    }
}