package com.idleciv.model;

import android.content.Context;

import com.idleciv.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by jaapo on 23-12-2017.
 */

public class ModelIndustry {

    public static final int GatherFood = 0;
    public static final int GatherLumber = 1;
    public static final int GatherFibre = 2;
    public static final int GatherStone = 3;

    public static final int RopeMaking = 4;
    public static final int Weaving = 6;
    public static final int Sowing = 7;

    public static final int WoodCutting = 8;
    public static final int CookGrain = 9;
    public static final int SawPlanks = 10;

    public static final int ShipMaking = 11;

    public static final int OreMining = 12;
    public static final int GatherGrain = 13;
    public static final int GatherOre = 14;
    public static final int StoneToolCrafting = 15;
    public static final int StoneWeaponCrafting = 16;

    public int mIndustryIndex;
    public boolean mIsEnabled;
    public boolean mIsObsolete;
    public boolean mCanConsume;
    public boolean mCanProduce;

    public int mPopulationCurrent;
    public int mPopulationMax;
    public double mCapital;

    public int mIconResourceId;
    public int mNameResourceId;

    public ArrayList<ModelResourceAmount> mConsumptionList;
    public ArrayList<ModelResourceAmount> mProductionList;

    public transient ModelEpochState mEpochState;
    public transient HashSet<Listener> mListenerSet;

    public ModelIndustry(
            ModelEpochState epochState,
            int industryIndex,
            ModelResourceAmount [] consumedList,
            ModelResourceAmount [] producedList,
            int populationMax,
            int iconResourceId,
            int nameResourceId)
    {
        mEpochState = epochState;
        mListenerSet = new HashSet<>();

        mIndustryIndex = industryIndex;
        mIsEnabled = false;
        mConsumptionList = new ArrayList<>(Arrays.asList(consumedList));
        mProductionList = new ArrayList<>(Arrays.asList(producedList));
        mPopulationMax = populationMax;
        mIconResourceId = iconResourceId;
        mNameResourceId = nameResourceId;

        //Log.e("ModelIndustry", "create: " + mIndustryIndex);
        mPopulationCurrent = 0;
        mCapital = 1;
    }

    public void validate(ModelEpochState gameState) {
        mEpochState = gameState;
        mListenerSet = new HashSet<>();
        //Log.e("validate", "updateUI: " + mIndustryIndex);
    }

    public void tickDemand() {
        for (ModelResourceAmount resourceAmount: mConsumptionList) {
            mEpochState.mResourceStockMap.get(resourceAmount.mResourceIndex).addDemand(resourceAmount.mAmount * mPopulationCurrent);
        }
    }

    public void tickProduction() {
        //Check demand()
        mCanConsume = true;
        for (ModelResourceAmount resourceAmount: mConsumptionList) {
            if(!mEpochState.mResourceStockMap.get(resourceAmount.mResourceIndex).canSupply()){
                mCanConsume = false;
            }
        }
        if (mCanConsume) {
            for (ModelResourceAmount resourceAmount : mConsumptionList) {
                mEpochState.mResourceStockMap.get(resourceAmount.mResourceIndex).consume(resourceAmount.mAmount * mPopulationCurrent);
            }

            for (ModelResourceAmount resourceAmount : mProductionList) {
                mEpochState.mResourceStockMap.get(resourceAmount.mResourceIndex).produce(resourceAmount.mAmount * mPopulationCurrent);
            }
        }
    }

    public void addPop() {
        if(0 < mEpochState.mPopulationFree){
            mEpochState.mPopulationFree--;
            mPopulationCurrent++;
            mEpochState.updateUI();
            updateUI();
        }
    }


    public void removePop() {
        if(0 < mPopulationCurrent){
            mPopulationCurrent--;
            mEpochState.mPopulationFree++;
            mEpochState.updateUI();
            updateUI();
        }
    }

    public static ArrayList<ModelIndustry> getIndustryList(ModelEpochState gameState) {
        ArrayList<ModelIndustry> industryList = new ArrayList<ModelIndustry>();

        industryList.add(
                new ModelIndustry( gameState, GatherFood,
                        new ModelResourceAmount[]{
                                new ModelResourceAmount(ModelResourceStock.Food, 10)
                        },
                        new ModelResourceAmount[]{
                                new ModelResourceAmount(ModelResourceStock.Food, 20)
                        },
                        1,
                        R.drawable.industry_gather_food,
                        R.string.industry_gather_food_name));

        industryList.add(
                new ModelIndustry(gameState, GatherLumber,
                        new ModelResourceAmount[]{
                                new ModelResourceAmount(ModelResourceStock.Food, 10)
                        },
                        new ModelResourceAmount[]{
                                new ModelResourceAmount(ModelResourceStock.Lumber, 10)
                        },
                        1,
                        R.drawable.industry_gather_lumber,
                        R.string.industry_gather_lumber_name));

        industryList.add(
                new ModelIndustry(gameState, GatherGrain,
                        new ModelResourceAmount[]{
                                new ModelResourceAmount(ModelResourceStock.Food, 10)
                        },
                        new ModelResourceAmount[]{
                                new ModelResourceAmount(ModelResourceStock.Grain, 10)
                        },
                        1,
                        R.drawable.industry_gather_grain,
                        R.string.industry_gather_grain_name));

        industryList.add(
                new ModelIndustry(gameState, GatherStone,
                        new ModelResourceAmount[]{
                                new ModelResourceAmount(ModelResourceStock.Food, 10)
                        },
                        new ModelResourceAmount[]{
                                new ModelResourceAmount(ModelResourceStock.Stone, 10)
                        },
                        1,
                        R.drawable.industry_gather_stone,
                        R.string.industry_gather_stone_name));

        industryList.add(
                new ModelIndustry(gameState, CookGrain,
                        new ModelResourceAmount[]{
                                new ModelResourceAmount(ModelResourceStock.Food, 10),
                                new ModelResourceAmount(ModelResourceStock.Grain, 5),
                                new ModelResourceAmount(ModelResourceStock.Lumber, 5)
                        },
                        new ModelResourceAmount[]{
                                new ModelResourceAmount(ModelResourceStock.Food, 20)
                        },
                        2,
                        R.drawable.industry_cook_grain,
                        R.string.industry_cook_grain_name));

        industryList.add(
                new ModelIndustry(gameState, WoodCutting,
                        new ModelResourceAmount[]{
                                new ModelResourceAmount(ModelResourceStock.Food, 10)
                        },
                        new ModelResourceAmount[]{
                                new ModelResourceAmount(ModelResourceStock.Lumber, 20)
                        },
                        1,
                        R.drawable.industry_wood_cutting,
                        R.string.industry_wood_cutting_name));

        industryList.add(
                new ModelIndustry(gameState, SawPlanks,
                        new ModelResourceAmount[]{
                                new ModelResourceAmount(ModelResourceStock.Food, 10),
                                new ModelResourceAmount(ModelResourceStock.Lumber, 10)
                        },
                        new ModelResourceAmount[]{
                                new ModelResourceAmount(ModelResourceStock.Planks, 10)
                        },
                        1,
                        R.drawable.placeholder,
                        R.string.industry_saw_planks_name));

        industryList.add(
                new ModelIndustry(gameState, OreMining,
                        new ModelResourceAmount[]{
                                new ModelResourceAmount(ModelResourceStock.Food, 10),
                                new ModelResourceAmount(ModelResourceStock.Planks, 10)
                        },
                        new ModelResourceAmount[]{
                                new ModelResourceAmount(ModelResourceStock.Ore, 1)
                        },
                        1,
                        R.drawable.industry_ore_mining,
                        R.string.industry_saw_planks_name));

        industryList.add(
                new ModelIndustry(gameState, ShipMaking,
                        new ModelResourceAmount[]{
                                new ModelResourceAmount(ModelResourceStock.Food, 10),
                                new ModelResourceAmount(ModelResourceStock.Planks, 10),
                                new ModelResourceAmount(ModelResourceStock.Cloth, 10),
                                new ModelResourceAmount(ModelResourceStock.Rope, 10)
                        },
                        new ModelResourceAmount[]{
                                new ModelResourceAmount(ModelResourceStock.Ship, 1)
                        },
                        1,
                        R.drawable.placeholder,
                        R.string.industry_saw_planks_name));

        return industryList;
    }

    public String getName(Context context) {
        return context.getString(mNameResourceId);
    }

    public int getIcon() {
        return mIconResourceId;
    }



    public void updateUI() {
        for (Listener listener: mListenerSet) {
            listener.updateIndustryUI();
        }
    }

    public void addListener(Listener listener) {
        mListenerSet.add(listener);
        listener.updateIndustryUI();
    }

    public void removeListener(Listener listener) {
        mListenerSet.remove(listener);
    }

    public void clear() {
        mListenerSet = new HashSet<>();
    }


    public interface Listener {
        void updateIndustryUI();
    }
}
