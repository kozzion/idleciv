package com.idleciv.model;

import com.idleciv.R;
import com.idleciv.holder.HolderResourceStock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by jaapo on 21-1-2018.
 */

public class ModelResourceStock {

    public static final int Food = 0;
    public static final int Lumber = 1;
    public static final int Stone = 2;

    public static final int Planks = 3;
    public static final int Charcoal = 4;
    public static final int Ore = 5;
    public static final int Iron = 6;

    public static final int Fibre = 7;
    public static final int Cloth = 8;

    private transient ModelGameState mGameState;
    private transient HashSet<Listener> mListenerSet;

    public int mResourceIndex;

    public int mStock;
    public int mCapacity;

    public boolean mIsEnabled;
    public boolean mHasChanges;

    public ModelResourceStock(ModelGameState gameState, int resourceIndex, int stock, boolean isEnabled)
    {
        mGameState = gameState;
        mListenerSet = new HashSet<>();
        mResourceIndex = resourceIndex;
        mStock = stock;
        mIsEnabled = isEnabled;
    }

    public void validate(ModelGameState gameState) {
        mGameState = gameState;
        mListenerSet = new HashSet<>();
        //Log.e("validate", "updateUI: " + mIndustryIndex);
    }

    public static ArrayList<ModelResourceStock> getResourceList(ModelGameState gameState) {
        ArrayList<ModelResourceStock> resourceList = new ArrayList<>();
        resourceList.add(new ModelResourceStock(gameState, Food, 0, true));
        resourceList.add(new ModelResourceStock(gameState, Lumber, 0, false));
        resourceList.add(new ModelResourceStock(gameState, Stone, 0, false));
        return resourceList;
    }

    public static String getName(int resourceIndex)
    {
        switch (resourceIndex){
            case  Food:
                return "Food";
            case Lumber:
                return "Lumber";
            case Stone:
                return "Stone";
            default:
                return "unknown resource index: " + resourceIndex;
        }
    }

    public static int getIcon(int resourceIndex) {
        switch (resourceIndex){
            case  Food:
                return R.drawable.wheat;
            case Lumber:
                return R.drawable.lumber;
            case Stone:
                return R.drawable.stone;
            default:
                return R.drawable.placeholder;
        }
    }


    public void updateUI() {
        if(mHasChanges) {
            for (Listener listener : mListenerSet) {
                listener.updateResourceStockUI();
            }
        }
    }

    public void addListener(Listener listener) {
        mListenerSet.add(listener);
        listener.updateResourceStockUI();
    }

    public void removeListener(Listener listener) {
        mListenerSet.remove(listener);
    }

    public void clear() {
        mListenerSet = new HashSet<>();
    }


    public interface Listener {
        void updateResourceStockUI();
    }
}
