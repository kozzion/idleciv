package com.idleciv.model.modefier;

import com.idleciv.model.IModelModifier;
import com.idleciv.model.ModelGameState;
import com.idleciv.model.ModelIndustry;
import com.idleciv.model.ModelTechnology;

public class ModifierUnlockTechnology extends IModelModifier {

    public int mTechnologyIndex;

    public ModifierUnlockTechnology(int technologyIndex){
        mTechnologyIndex = technologyIndex;
    }

    @Override
    public void apply(ModelGameState gameState) {

    }

    @Override
    public void remove(ModelGameState gameState) {

    }

    @Override
    public String getDescription() {
        return "Unlock the " + ModelTechnology.getName(mTechnologyIndex) + " industry";
    }
}
