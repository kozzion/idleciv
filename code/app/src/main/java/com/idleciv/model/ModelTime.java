package com.idleciv.model;

import android.animation.TimeAnimator;
import android.util.Log;

import com.idleciv.holder.HolderTime;

import java.util.HashSet;

public class ModelTime {

    public transient ModelGameState mGameState;
    public transient HashSet<TimeListener> mTimeListenerSet;
    public double mYearProgress;
    public double mYearProgressMax;
    public double mYearProgressPerSecond;


    public ModelTime(ModelGameState gameState){
        mGameState = gameState;
        mTimeListenerSet = new HashSet<>();
        mYearProgress = 0;
        mYearProgressMax = 100;
        mYearProgressPerSecond = 5;
    }

    public void validate(ModelGameState gameState) {
        mGameState = gameState;
        mTimeListenerSet = new HashSet<>();
    }

    public void updateState(double elapsedSeconds) {
        //Log.e("updateState", "updateState: ");
        mYearProgress = mYearProgress + (elapsedSeconds * mYearProgressPerSecond);
        if(mYearProgressMax < mYearProgress) {
            mYearProgress -= mYearProgressMax;
            //TODO yearTick
            mGameState.tickYear();
        }
    }

    public void updateUI() {
        //Log.e("updateUI", "updateUI: 0");
        for (TimeListener listener: mTimeListenerSet) {
            //Log.e("updateUI", "updateUI: 1");
            listener.updateTimeUI();
        }
    }

    public void addListener(TimeListener listener) {
        mTimeListenerSet.add(listener);
    }

    public void removeListener(TimeListener listener) {
        mTimeListenerSet.remove(listener);
    }

    public interface TimeListener{
        void updateTimeUI();
    }
}
