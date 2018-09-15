package com.idleciv.model.modefier;

import android.content.Context;

import com.idleciv.model.ModelModifier;
import com.idleciv.model.ModelEpochState;
import com.idleciv.model.ModelIndustry;

public class ModifierUnlockIndustry extends ModelModifier {

    public int mIndustryIndex;

    public ModifierUnlockIndustry(int industryIndex){
        mIndustryIndex = industryIndex;
    }

    @Override
    public void apply(ModelEpochState gameState) {
        ModelIndustry industry = gameState.mIndustryMap.get(mIndustryIndex);
        industry.mIsEnabled = true;
        gameState.updateUI();
    }

    @Override
    public void remove(ModelEpochState gameState) {

    }

    public String getDescription(Context context, ModelEpochState gameState) {
        return "Unlock the " + gameState.mIndustryMap.get(mIndustryIndex).getName(context) + " industry";
    }
}
