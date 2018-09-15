package com.idleciv.model;

import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jaapo on 8-1-2018.
 */

public class ModelResourceAmount {

    public int mResourceIndex;
    public int mAmount;

    public ModelResourceAmount(int resourceIndex, int amount)
    {
        mResourceIndex = resourceIndex;
        mAmount = amount;
    }

    public static ArrayList<ModelResourceAmount> multiply(ArrayList<ModelResourceAmount> originalList, int multiplier) {
        ArrayList<ModelResourceAmount> list = new ArrayList<>();
        for (ModelResourceAmount amount: originalList) {
            list.add(new ModelResourceAmount(amount.mResourceIndex, amount.mAmount * multiplier));
        }
        return list;
    }
}
