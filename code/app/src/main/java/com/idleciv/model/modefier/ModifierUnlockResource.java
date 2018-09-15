package com.idleciv.model.modefier;

import android.content.Context;

import com.idleciv.model.ModelModifier;
import com.idleciv.model.ModelEpochState;
import com.idleciv.model.ModelResourceStock;

public class ModifierUnlockResource extends ModelModifier {

    public int mResourceIndex;

    public ModifierUnlockResource(int resourceIndex){
        mResourceIndex= resourceIndex;
    }

    @Override
    public void apply(ModelEpochState gameState) {
        ModelResourceStock resourceStock = gameState.mResourceStockMap.get(mResourceIndex);
        resourceStock.mIsEnabled = true;
        gameState.updateUI();
    }

    @Override
    public void remove(ModelEpochState gameState) {

    }

    public String getDescription(Context context, ModelEpochState gameState) {
        return "Unlock the " + gameState.mResourceStockMap.get(mResourceIndex).getName(context) + " resource";
    }
}
