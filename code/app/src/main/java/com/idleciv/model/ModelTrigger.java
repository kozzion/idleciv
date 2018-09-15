package com.idleciv.model;

import android.util.Log;

import java.util.ArrayList;

public class ModelTrigger {

    public static String TAG = ModelTrigger.class.getName();

    public static final int UnlockPopulationTab = 1;
    public static final int UnlockProductionTab = 2;
    public static final int UnlockTechnologyTab = 3;

    public boolean mIsEnabled;
    public boolean mIsResolved;

    int mTriggerIndex;
    int mResourceIndex;
    int mResourceAmount;


    public ModelTrigger(int triggerIndex, int resourceIndex, int resourceAmount){
        mTriggerIndex = triggerIndex;
        mResourceIndex = resourceIndex;
        mResourceAmount = resourceAmount;
        mIsEnabled = true;
        mIsResolved = false;
    }

    public boolean checkConditions(ModelEpochState gameState){
         return (mResourceAmount <= gameState.mResourceStockMap.get(mResourceIndex).mStock);
    }

    public void resolve(ModelGameState gameState){
        if(mTriggerIndex == UnlockPopulationTab){
            Log.e(TAG, "resolve: ");
            gameState.mUnlockedPopulation = true;
            gameState.mActivityMain.showPopup("Population tab now unlocked");
            gameState.mHasChanges = true;
        }

        if(mTriggerIndex == UnlockProductionTab){
            gameState.mUnlockedProduction = true;
            gameState.mActivityMain.showPopup("Production tab now unlocked");
            gameState.mHasChanges = true;
        }

        if(mTriggerIndex == UnlockTechnologyTab){
            gameState.mUnlockedTechnology = true;
            gameState.mActivityMain.showPopup("Technology tab now unlocked");
            gameState.mHasChanges = true;
        }
    }


    public static ArrayList<ModelTrigger> getTriggerList(ModelEpochState gameState){
        ArrayList<ModelTrigger> triggerList = new ArrayList<>();
        triggerList.add(new ModelTrigger(UnlockPopulationTab, ModelResourceStock.Food, 5));
        triggerList.add(new ModelTrigger(UnlockProductionTab, ModelResourceStock.Food, 10));
        triggerList.add(new ModelTrigger(UnlockTechnologyTab, ModelResourceStock.Food, 50));

        return triggerList;
    }

}
