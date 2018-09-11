package com.idleciv.model.modefier;

import com.idleciv.model.IModelModifier;
import com.idleciv.model.ModelGameState;
import com.idleciv.model.ModelIndustry;

public class ModifierAddPercent extends IModelModifier{

    int mPercent;
    int mIndustryIndex;

    public ModifierAddPercent(int percent, int industryIndex ){
        mPercent = percent;
        mIndustryIndex = industryIndex;
    }

    @Override
    public void apply(ModelGameState gameState) {

    }

    @Override
    public void remove(ModelGameState gameState) {

    }

    public String getDescription() {
        return "+" + mPercent + "% to " + ModelIndustry.getName(mIndustryIndex) + " production";
    }
}
