package com.idleciv.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by jaapo on 2-1-2018.
 */

public class ModelEpochState {

    public static final String TAG = ModelEpochState.class.getName();

    public ModelGameState mGameState;

    //TODO this needs to happen one level higher


    private transient  HashSet<ModelEpochState.GameStateListener> mListenerSet;

    public boolean mHasChanges; // for research

    public int mPopulationTotal;
    public int mPopulationFree;
    public ArrayList<ModelCost> mPopulationCostList;

    public HashMap<Integer, ModelResourceStock> mResourceStockMap;
    public HashMap<Integer, ModelIndustry> mIndustryMap;
    public HashMap<Integer, ModelTechnology> mTechnologyMap;
    public HashMap<Integer, ModelTrigger> mTriggerMap;

    public ModelObjective mObjective;
    public ModelTime mTime;

    public List<String> mNotificationQueue;



    public ModelEpochState(ModelGameState gameState)
    {
        mGameState = gameState;
        mListenerSet = new HashSet<>();


        mPopulationTotal = 1;
        mPopulationFree = 1;

        mTime = new ModelTime(this);

        mResourceStockMap = new HashMap<>();
        for (ModelResourceStock resourceStock: ModelResourceStock.getResourceList(this)) {
            mResourceStockMap.put(resourceStock.mResourceIndex, resourceStock);
        }

        mTechnologyMap = new HashMap<>();
        for (ModelTechnology technology: ModelTechnology.getTechnologyList(this)) {
            mTechnologyMap.put(technology.mTechnologyIndex, technology);
        }

        mIndustryMap = new HashMap<>();
        for (ModelIndustry industry: ModelIndustry.getIndustryList(this)) {
            mIndustryMap.put(industry.mIndustryIndex, industry);
        }

        mTriggerMap = new HashMap<>();
        for (ModelTrigger trigger: ModelTrigger.getTriggerList(this)) {
            mTriggerMap.put(trigger.mTriggerIndex, trigger);
        }

        mPopulationCostList = new ArrayList<>();

        loadPopIncreaes();

        //Enable initial resources, industries and techs
        mResourceStockMap.get(ModelResourceStock.Food).mIsEnabled = true;
        mIndustryMap.get(ModelIndustry.GatherFood).mIsEnabled = true;
        //mTechnologyMap.get(ModelResourceStock.Food).mIsEnabled = true;

        mHasChanges = true;
    }

    public void validate(ModelGameState gameState)
    {
        mGameState = gameState;
        mListenerSet = new HashSet<>();
        //Log.e("validate", "validating");
        mTime.validate(this);
        for (ModelResourceStock resourceStock: mResourceStockMap.values()) {
            resourceStock.validate(this);
        }

        for (ModelIndustry industry : mIndustryMap.values()) {
            industry.validate(this);
        }

        for (ModelTechnology technology: mTechnologyMap.values()) {
            technology.validate(this);
        }
        mHasChanges = true;
    }

    public void loadPopIncreaes() {
        ModelCost cost0 = new ModelCost();
        cost0.mResourceCostList.add(new ModelResourceAmount(ModelResourceStock.Food, 5));

        ModelCost cost1 = new ModelCost();
        cost1.mResourceCostList.add(new ModelResourceAmount(ModelResourceStock.Food, 10));

        ModelCost cost2 = new ModelCost();
        cost2.mResourceCostList.add(new ModelResourceAmount(ModelResourceStock.Food, 20));
        cost2.mResourceCostList.add(new ModelResourceAmount(ModelResourceStock.Lumber, 10));

        ModelCost cost3 = new ModelCost();
        cost3.mResourceCostList.add(new ModelResourceAmount(ModelResourceStock.Food, 20));
        cost3.mResourceCostList.add(new ModelResourceAmount(ModelResourceStock.Lumber, 10));
        cost3.mResourceCostList.add(new ModelResourceAmount(ModelResourceStock.Stone, 10));

        mPopulationCostList.add(cost0);
        mPopulationCostList.add(cost1);
        mPopulationCostList.add(cost2);
        mPopulationCostList.add(cost3);

    }

    public boolean buyPopulation()
    {
        if(0 < mPopulationCostList.size() )
        {
            if(checkCost(mPopulationCostList.get(0))) {
                payCost(mPopulationCostList.get(0));
                mPopulationCostList.remove(0);
                mPopulationTotal++;
                mPopulationFree++;
            }
        }
        mHasChanges = true;
        return true;
    }





    private boolean checkCost(ModelCost cost)
    {
        for (ModelResourceAmount resourceAmount: cost.mResourceCostList) {
            if(resourceAmount.mAmount > mResourceStockMap.get(resourceAmount.mResourceIndex).mStock)
            {
                return false;
            }
        }
        return true;
    }

    private void payCost(ModelCost cost)
    {
        for (ModelResourceAmount resourceAmount: cost.mResourceCostList) {
            mResourceStockMap.get(resourceAmount.mResourceIndex).mStock -= resourceAmount.mAmount;
        }
    }

    public void dispose() {
        for (ModelIndustry industry : mIndustryMap.values()) {
            industry.clear();
        }
    }


    public void addListener(ModelEpochState.GameStateListener listener) {
        mListenerSet.add(listener);
        listener.updateGameStateUI();
    }

    public void removeListener(ModelEpochState.GameStateListener listener) {
        mListenerSet.remove(listener);
    }



    public void clear() {
        mListenerSet = new HashSet<>();
    }

    public ArrayList<ModelIndustry> getEnabledIndustryList() {
        ArrayList<ModelIndustry> enabledIndustryList = new ArrayList<>();
        for (ModelIndustry industry: mIndustryMap.values()) {
            if (industry.mIsEnabled){
                enabledIndustryList.add(industry);
            }
        }
        return enabledIndustryList;
    }


    public ArrayList<ModelResourceStock> getEnabledResourceList() {
        ArrayList<ModelResourceStock> enabledResourceList = new ArrayList<>();
        for (ModelResourceStock resourceStock: mResourceStockMap.values()) {
            if (resourceStock.mIsEnabled){
                enabledResourceList.add(resourceStock);
            }
        }
        return enabledResourceList;
    }

    public ArrayList<ModelTechnology> getAvailebleTechnologyList() {
        ArrayList<ModelTechnology> availebleTechnologyList = new ArrayList<>();
        for (ModelTechnology technology: mTechnologyMap.values()) {
            if ((technology.mIsResearched == false) && (technology.isVisible())){
                availebleTechnologyList.add(technology);
            }
        }
        return availebleTechnologyList;
    }

    public void updateState(double elapsedSeconds) {
        mTime.updateState(elapsedSeconds);
    }

    public void updateUI() {
        if(mHasChanges){
            for (ModelEpochState.GameStateListener listener: mListenerSet) {
                listener.updateGameStateUI();
            }
            mHasChanges = false;
        }

        mTime.updateUI();
        for (ModelResourceStock resourceStock: mResourceStockMap.values()) {
            resourceStock.updateUI();
        }
        for (ModelIndustry industry: mIndustryMap.values()) {
            industry.updateUI();
        }
        for (ModelTechnology technology: mTechnologyMap.values()) {
            technology.updateUI();
        }
    }

    public void tickYear() {
        for (ModelResourceStock resourceStock: mResourceStockMap.values()) {
            resourceStock.clearDemandAndProduction();
        }
        for (ModelIndustry industry: mIndustryMap.values()) {
            industry.tickDemand();
        }
        for (ModelIndustry industry: mIndustryMap.values()) {
            industry.tickProduction();
        }
        mResourceStockMap.get(ModelResourceStock.Food).produce(mPopulationFree);


        for (ModelTrigger trigger: mTriggerMap.values()) {
            Log.e(TAG, "trigger: " + trigger.mResourceIndex );
            if((trigger.mIsEnabled) && (!trigger.mIsResolved)){
                Log.e(TAG, "trigger 1 : " + trigger.mResourceIndex );
                if(trigger.checkConditions(this)){
                    Log.e(TAG, "trigger 2: " + trigger.mResourceIndex );
                    trigger.mIsResolved = true;
                    trigger.resolve(this.mGameState);
                }
            }
        }
    }

    public interface GameStateListener {
        void updateGameStateUI();
    }
}
