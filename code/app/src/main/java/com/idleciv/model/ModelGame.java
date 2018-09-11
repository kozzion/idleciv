package com.idleciv.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashSet;

/**
 * Created by jaapo on 21-1-2018.
 */

public class ModelGame {

    private boolean mHasChanges;
    private HashSet<GameListener> mListenerSet;
    public ModelGameState mGameState;


    public ModelGame() {
        mGameState = new ModelGameState();
        mListenerSet = new HashSet<>();
        mHasChanges = true;
    }

    public void reset() {
        mGameState.dispose();
        mGameState = new ModelGameState();
        mHasChanges = true;
    }


    public void save(Activity activity) {
        Gson gson = new Gson();
        Type type = new TypeToken<ModelGameState>() {}.getType();
        String gameStateJson = gson.toJson(mGameState, type);
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.putString("GameState", gameStateJson);
        edit.apply();
    }

    public void load(Activity activity) {
        mGameState.dispose();
        String gameStateJson = activity.getPreferences(Context.MODE_PRIVATE).getString("GameState", "");
        if(gameStateJson.equals("") || gameStateJson.equals("null")) {
            Log.e("load", "creating new ");
            mGameState = new ModelGameState();
        } else {
            try {
                Gson gson = new Gson();
                Type type = new TypeToken<ModelGameState>() {
                }.getType();
                Log.e("load", "loading");
                mGameState = gson.fromJson(gameStateJson, type);
            }
            catch (JsonSyntaxException e)
            {
                Log.e("load", "creating new fallback");
                mGameState = new ModelGameState();
            }
        }
        mGameState = mGameState.validate();
        mHasChanges = true;
    }


    public void updateUI() {
        if(mHasChanges) {
            updateListeners();
        }

        mGameState.updateUI();
        mHasChanges = false;
    }

    private void updateListeners() {
        for (GameListener listener: mListenerSet) {
            listener.updateGameUI();
        }
    }

    public void addGameListener(GameListener listener) {
        mListenerSet.add(listener);
        listener.updateGameUI();
    }

    public void updateState(double elapsedSeconds) {
        mGameState.updateState(elapsedSeconds);
    }


    public interface GameListener {
        void updateGameUI();
    }
}
