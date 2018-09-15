package com.idleciv.model.modefier;

import android.content.Context;

import com.idleciv.model.ModelModifier;
import com.idleciv.model.ModelEpochState;

public class ModifierAddPercent extends ModelModifier {

    int mPercent;
    int mIndustryIndex;

    public ModifierAddPercent(int percent, int industryIndex ){
        mPercent = percent;
        mIndustryIndex = industryIndex;
    }

    @Override
    public void apply(ModelEpochState gameState) {

    }

    @Override
    public void remove(ModelEpochState gameState) {

    }

    public String getDescription(Context context, ModelEpochState gameState) {
        return "+" + mPercent + "% to " + gameState.mIndustryMap.get(mIndustryIndex).getName(context) + " production";
    }
}
