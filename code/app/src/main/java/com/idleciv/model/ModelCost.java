package com.idleciv.model;

import java.util.ArrayList;

/**
 * Created by jaapo on 8-1-2018.
 */

public class ModelCost {

    public ArrayList<ModelResourceAmount> mResourceCostList;

    public ModelCost()
    {
        mResourceCostList = new ArrayList<>();
    }

    public ModelCost(ArrayList<ModelResourceAmount> resourceCostList)
    {
        mResourceCostList = new ArrayList<>(resourceCostList);
    }
}

