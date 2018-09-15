package com.idleciv.model;

import com.idleciv.activity.ActivityMain;

import java.util.HashSet;

/**
 * Created by jaapo on 21-1-2018.
 */

public class ModelGameState {

    public static final int mCurrentVersionNumber = 12;

    public transient ActivityMain mActivityMain;
    public transient HashSet<GameStateListener> mListenerSet;

    public ModelEpochState mEpochState;

    public int mVersionNumber;

    public boolean mUnlockedPopulation;
    public boolean mUnlockedProduction;
    public boolean mUnlockedExpansion; //Also war
    public boolean mUnlockedTechnology;
    public boolean mUnlockedOrganisation; //Religion and government
    public boolean mUnlockedProjects;  //Also wonders
    public boolean mUnlockedCulture; //Also Achievements


    //TODO Achievements should also be here
    public boolean mHasChanges;

    public ModelGameState(ActivityMain activityMain) {
        mActivityMain = activityMain;
        mListenerSet = new HashSet<>();
        mVersionNumber = mCurrentVersionNumber;



        mUnlockedPopulation = false;
        mUnlockedProduction = false;
        mUnlockedExpansion = false;
        mUnlockedTechnology = false;

        mEpochState = new ModelEpochState(this);

        mHasChanges = true;
    }


    public ModelGameState validate(ActivityMain activityMain) {
        if(mVersionNumber != mCurrentVersionNumber) {
            //Log.e("validate", "creating new ");
            return new ModelGameState(activityMain);
        } else {
            mActivityMain = activityMain;
            mListenerSet = new HashSet<>();

            //Log.e("validate", "validating");
            mEpochState.validate(this);

            mHasChanges = true;
            return this;
        }
    }

    public void resetEpoch() {
        mEpochState.dispose();
        mEpochState = new ModelEpochState(this);
        mHasChanges = true;
    }

    public void updateUI() {
        if(mHasChanges) {
            for (GameStateListener listener: mListenerSet) {
                listener.updateGameStateUI();
            }
        }

        mEpochState.updateUI();
        mHasChanges = false;
    }

    public void addGameListener(GameStateListener listener) {
        mListenerSet.add(listener);
        listener.updateGameStateUI();
    }

    public void updateState(double elapsedSeconds) {
        mEpochState.updateState(elapsedSeconds);
    }

    public void dispose() {
        mEpochState.dispose();
    }

    public interface GameStateListener {
        void updateGameStateUI();
    }
}
