package com.idleciv.model.modefier;

import android.content.Context;

import com.idleciv.model.ModelModifier;
import com.idleciv.model.ModelEpochState;

public class ModifierUnlockTechnology extends ModelModifier {

    public int mTechnologyIndex;

    public ModifierUnlockTechnology(int technologyIndex){
        mTechnologyIndex = technologyIndex;
    }

    @Override
    public void apply(ModelEpochState gameState) {

    }

    @Override
    public void remove(ModelEpochState gameState) {

    }

    @Override
    public String getDescription(Context context, ModelEpochState gameState) {
        return "Unlock the " + gameState.mTechnologyMap.get(mTechnologyIndex).getName(context) + " technology";
    }
}
