package com.idleciv.model.modefier;

import com.idleciv.model.IModelModifier;
import com.idleciv.model.ModelGameState;
import com.idleciv.model.ModelIndustry;
import com.idleciv.model.ModelResourceStock;

public class ModifierUnlockResource extends IModelModifier {

    public int mResourceIndex;

    public ModifierUnlockResource(int resourceIndex){
        mResourceIndex= resourceIndex;
    }

    @Override
    public void apply(ModelGameState gameState) {
        ModelResourceStock resourceStock = gameState.mResourceStockMap.get(mResourceIndex);
        resourceStock.mIsEnabled = true;
        gameState.updateUI();
    }

    @Override
    public void remove(ModelGameState gameState) {

    }

    public String getDescription() {
        return "Unlock the " + ModelResourceStock.getName(mResourceIndex) + " resource";
    }
}
