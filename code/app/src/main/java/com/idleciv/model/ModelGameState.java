package com.idleciv.model;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by jaapo on 2-1-2018.
 */

public class ModelGameState {



    public static int mCurrentVersionNumber = 2;
    public int mVersionNumber;
    public int mPopulationTotal;
    public int mPopulationFree;
    public ArrayList<ModelCost> mPopulationCostList;
    public ArrayList<ModelIndustry> mIndustryList;
    public ArrayList<ModelTechnology> mTechnologyAvailableList;
    public ArrayList<ModelTechnology> mTechnologyBougthList;
    public ModelObjective mObjective;

    private transient  HashSet<ModelGameState.GameStateListener> mListenerSet;


    public ModelGameState()
    {
        mListenerSet = new HashSet<>();



        mVersionNumber = mCurrentVersionNumber;
        mPopulationTotal = 1;
        mPopulationFree = 1;
        mIndustryList = new ArrayList<>();
        mPopulationCostList = new ArrayList<>();
        mTechnologyAvailableList = new ArrayList<>();
        mTechnologyBougthList = new ArrayList<>();
        loadTechnologies();
        loadPopIncreaes();

        mIndustryList.add(
                new ModelIndustry(this, ResourceType.Food));
        mIndustryList.add(
                new ModelIndustry(this, ResourceType.Lumber));
        mIndustryList.add(
                new ModelIndustry(this, ResourceType.Stone));
    }



    public void loadTechnologies() {
        mTechnologyAvailableList.addAll(ModelTechnology.getTechnologies(this));
    }

    public void loadPopIncreaes() {
        ModelCost cost0 = new ModelCost();
        cost0.mResourceCostList.add(new ModelResourceAmount(ResourceType.Food, 1));
        ModelCost cost1 = new ModelCost();
        cost1.mResourceCostList.add(new ModelResourceAmount(ResourceType.Food, 4));
        ModelCost cost2 = new ModelCost();
        cost2.mResourceCostList.add(new ModelResourceAmount(ResourceType.Food, 20));
        cost2.mResourceCostList.add(new ModelResourceAmount(ResourceType.Lumber, 10));

        ModelCost cost3 = new ModelCost();
        cost2.mResourceCostList.add(new ModelResourceAmount(ResourceType.Food, 20));
        cost2.mResourceCostList.add(new ModelResourceAmount(ResourceType.Lumber, 10));
        cost2.mResourceCostList.add(new ModelResourceAmount(ResourceType.Stone, 10));

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
        return true;
    }





    private boolean checkCost(ModelCost cost)
    {
        for (ModelResourceAmount resourceAmount: cost.mResourceCostList) {
            if(resourceAmount.mAmount > mIndustryList.get(resourceAmount.mResourceIndex).mStock)
            {
                return false;
            }
        }
        return true;
    }

    private void payCost(ModelCost cost)
    {
        for (ModelResourceAmount resourceAmount: cost.mResourceCostList) {
            mIndustryList.get(resourceAmount.mResourceIndex).mStock -= resourceAmount.mAmount;
        }
    }


    public ModelGameState validate()
    {
        mListenerSet = new HashSet<>();
        if(mVersionNumber != mCurrentVersionNumber) {
            return new ModelGameState();
        } else {
            for (ModelIndustry industry : mIndustryList) {
                industry.validate(this);
            }
            return this;
        }

    }

    public void dispose() {
        for (ModelIndustry industry : mIndustryList) {
            industry.clear();
        }
    }



    public void updateUI() {
        for (ModelGameState.GameStateListener listener: mListenerSet) {
            listener.updateGameState(this);
        }
    }

    public void addListener(ModelGameState.GameStateListener listener) {
        mListenerSet.add(listener);
        listener.updateGameState(this);
    }

    public void removeListener(ModelGameState.GameStateListener listener) {
        mListenerSet.remove(listener);
    }



    public void clear() {
        mListenerSet = new HashSet<>();
    }


    public interface GameStateListener {
        void updateGameState(ModelGameState gameState);
    }
}
