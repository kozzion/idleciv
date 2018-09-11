package com.idleciv.model;

import com.idleciv.R;
import com.idleciv.model.modefier.ModifierAddPercent;
import com.idleciv.model.modefier.ModifierUnlockIndustry;

import java.util.ArrayList;

public class ModelTechnology {

    public static final int PrimitiveTools = 0;
    public static final int CopperTools = 1;
    public static final int BronzeTools = 2;
    public static final int IronTools = 3;
    public static final int SteelTools = 4;
    public static final int MachinedTools = 5;

    public static final int Archery = 6;
    public static final int Arches = 7;

    public transient ModelGameState mGameState;
    public int mTechnologyIndex;
    public boolean mIsResearched;
    public ArrayList<IModelModifier> mModifierList;

    public ModelTechnology(ModelGameState gameState, int technologyIndex, boolean isResearched, ArrayList<IModelModifier> modifiers) {
        mGameState = gameState;
        mTechnologyIndex = technologyIndex;
        mIsResearched = isResearched;
        mModifierList = modifiers;
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
            default:
                return "Undescribed technology with index: "+ technologyIndex;
        }
    }

    public interface TechnologyListener {
        void updateTechnology(ModelTechnology technology);
    }
}