package com.idleciv.model;

import android.util.Log;

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
    public static final int PrimitiveToolMaking = 5;
    public static final int Weaving = 6;
    public static final int Sowing = 7;



    public int mIndustryIndex;
    public boolean mIsEnabled;
    public boolean mCanConsume;
    public boolean mCanProduce;

    public int mPopulationIndustry;
    public double mCapital;
    public double mProduction;


    public ArrayList<ModelResourceAmount> mConsumedList;
    public ArrayList<ModelResourceAmount> mProducedList;

    public transient ModelGameState mGameState;
    public transient  HashSet<Listener> mListenerSet;

    public ModelIndustry(ModelGameState gameState, int industryIndex, boolean isEnabled, ModelResourceAmount [] consumedList, ModelResourceAmount [] producedList)
    {
        mGameState = gameState;
        mListenerSet = new HashSet<>();

        mIndustryIndex = industryIndex;
        mIsEnabled = isEnabled;
        mConsumedList = new ArrayList<>(Arrays.asList(consumedList));
        mProducedList = new ArrayList<>(Arrays.asList(producedList));

        //Log.e("ModelIndustry", "create: " + mIndustryIndex);
        mPopulationIndustry = 0;
        mCapital = 1;
    }

    public void validate(ModelGameState gameState) {
        mGameState = gameState;
        mListenerSet = new HashSet<>();
        //Log.e("validate", "updateUI: " + mIndustryIndex);
    }

    public void tickDemand() {
    }

    public void tickProduction() {
        mProduction = mPopulationIndustry;
        //Check demand()
        mCanConsume = true;
        for (ModelResourceAmount resourceAmount: mConsumedList) {
            if(mGameState.mResourceStockMap.get(resourceAmount.mResourceIndex).mStock < resourceAmount.mAmount * mPopulationIndustry){
                mCanConsume = false;
            }
        }
        if (mCanConsume) {
            for (ModelResourceAmount resourceAmount : mConsumedList) {
                mGameState.mResourceStockMap.get(resourceAmount.mResourceIndex).mStock -= resourceAmount.mAmount * mPopulationIndustry;
            }

            for (ModelResourceAmount resourceAmount : mProducedList) {
                mGameState.mResourceStockMap.get(resourceAmount.mResourceIndex).mStock += resourceAmount.mAmount * mPopulationIndustry;
            }
        }
    }

    public void addPop() {
        if(0 < mGameState.mPopulationFree){
            mGameState.mPopulationFree--;
            mPopulationIndustry++;
            mGameState.updateUI();
            updateUI();
        }
    }


    public void removePop() {
        if(0 < mPopulationIndustry){
            mPopulationIndustry--;
            mGameState.mPopulationFree++;
            mGameState.updateUI();
            updateUI();
        }
    }

    public static ArrayList<ModelIndustry> getIndustryList(ModelGameState gameState) {
        ArrayList<ModelIndustry> industryList = new ArrayList<ModelIndustry>();

        industryList.add(
                new ModelIndustry(gameState, GatherFood, true,
                        new ModelResourceAmount[]{},
                        new ModelResourceAmount[]{new ModelResourceAmount(ModelResourceStock.Food, 1)}));
        industryList.add(
                new ModelIndustry(gameState, GatherLumber,  false,
                        new ModelResourceAmount[]{},
                        new ModelResourceAmount[]{new ModelResourceAmount(ModelResourceStock.Lumber, 1)}));
        industryList.add(
                new ModelIndustry(gameState, GatherStone,  false,
                        new ModelResourceAmount[]{},
                        new ModelResourceAmount[]{new ModelResourceAmount(ModelResourceStock.Stone, 1)}));
        return industryList;
    }

    public static String getName(int industryIndex) {
        switch (industryIndex) {
            case GatherFood:
                return "Gather food";
            case GatherLumber:
                return "Gather lumber";
            case GatherFibre:
                return "Gather fibre";
            case GatherStone:
                return "Gather stone";

            default:
                return "unnamed industry index : " + industryIndex;
        }
    }

    public static int getIcon(int industryIndex) {
        switch (industryIndex) {
            case GatherFood:
                return R.drawable.wheat;
            case GatherLumber:
                return R.drawable.lumber;
            case GatherFibre:
                return R.drawable.placeholder;
            case GatherStone:
                return R.drawable.stone;

            default:
                return R.drawable.placeholder;
        }
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
