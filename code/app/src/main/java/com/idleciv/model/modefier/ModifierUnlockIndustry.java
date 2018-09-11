package com.idleciv.model.modefier;

import com.idleciv.model.IModelModifier;
import com.idleciv.model.ModelGameState;
import com.idleciv.model.ModelIndustry;

public class ModifierUnlockIndustry extends IModelModifier {

    public int mIndustryIndex;

    public ModifierUnlockIndustry(int industryIndex){
        mIndustryIndex = industryIndex;
    }

    @Override
    public void apply(ModelGameState gameState) {
        ModelIndustry industry = gameState.mIndustryMap.get(mIndustryIndex);
        industry.mIsEnabled = true;
        gameState.updateUI();
    }

    @Override
    public void remove(ModelGameState gameState) {

    }

    public String getDescription() {
        return "Unlock the " + ModelIndustry.getName(mIndustryIndex) + " industry";
    }
}
