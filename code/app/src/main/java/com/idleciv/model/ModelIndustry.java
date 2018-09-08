package com.idleciv.model;

import java.util.HashSet;

/**
 * Created by jaapo on 23-12-2017.
 */

public class ModelIndustry {

    public int mResourceIndex;
    public int mPopulationIndustry;
    public double mCapital;
    public double mProduction;
    public double mProductionPerUnit;

    public double mStock;
    public int mProgress;
    public transient ModelGameState mGameState;

    private transient  HashSet<Listener> mListenerSet;


    public ModelIndustry(ModelGameState gameState, int resourceIndex)
    {
        mGameState = gameState;
        mListenerSet = new HashSet<>();

        mResourceIndex = resourceIndex;
        mPopulationIndustry = 0;
        mCapital = 1;
    }

    public void validate(ModelGameState gameState) {
        mGameState = gameState;
        mListenerSet = new HashSet<>();
    }

    public void updateState(double timeElapsed)
    {

        mProduction = mPopulationIndustry * mCapital;

        mProgress += 1;
        if(100 < mProgress)
        {
            mProgress -= 100;
            mStock += mProduction;
        }

    }

    public void updateUI() {
        for (Listener listener: mListenerSet) {
            listener.updateIndustry(this);
        }
    }

    public void addListener(Listener listener) {
        mListenerSet.add(listener);

        listener.updateIndustry(this);
    }

    public void removeListener(Listener listener) {
        mListenerSet.remove(listener);
    }



    public void clear() {
        mListenerSet = new HashSet<>();
    }


    public interface Listener {
        void updateIndustry(ModelIndustry industry);
    }
}
