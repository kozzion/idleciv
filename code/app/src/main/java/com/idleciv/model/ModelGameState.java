package com.idleciv.model;

import java.util.ArrayList;

/**
 * Created by jaapo on 2-1-2018.
 */

public class ModelGameState {



    public static int mCurrentVersionNumber = 1;
    public int mVersionNumber;
    public int mPopulation;
    public ArrayList<ModelCost> mPopulationCostList;
    public ArrayList<ModelIndustry> mIndustryList;
    public ModelObjective mObjective;

    public ModelGameState()
    {
        mVersionNumber = mCurrentVersionNumber;
        mPopulation = 1;
        mIndustryList = new ArrayList<>();
        mPopulationCostList = new ArrayList<>();

        ModelCost cost0 = new ModelCost();
        cost0.mResourceCostList.add(new ModelResourceAmount(ResourceType.Food, 1));
        ModelCost cost1 = new ModelCost();
        cost1.mResourceCostList.add(new ModelResourceAmount(ResourceType.Food, 4));
        ModelCost cost2 = new ModelCost();
        cost2.mResourceCostList.add(new ModelResourceAmount(ResourceType.Food, 20));
        cost2.mResourceCostList.add(new ModelResourceAmount(ResourceType.Lumber, 10));

        mPopulationCostList.add(cost0);
        mPopulationCostList.add(cost1);
        mPopulationCostList.add(cost2);

        mIndustryList.add(
                new ModelIndustry(ResourceType.Food));
        mIndustryList.add(
                new ModelIndustry(ResourceType.Lumber));
        mIndustryList.add(
                new ModelIndustry(ResourceType.Ward));
    }

    public boolean buyPopulation()
    {
        if(0 < mPopulationCostList.size() )
        {
            if(checkCost(mPopulationCostList.get(0))) {
                payCost(mPopulationCostList.get(0));
                mPopulationCostList.remove(0);
                mPopulation++;
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
        if(mVersionNumber != mCurrentVersionNumber) {
            return new ModelGameState();
        } else {
            for (ModelIndustry industry : mIndustryList) {
                industry.validate();
            }
            return this;
        }

    }

    public void dispose() {
        for (ModelIndustry industry : mIndustryList) {
            industry.clear();
        }
    }
}
