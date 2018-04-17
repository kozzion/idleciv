package com.idleciv.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashSet;

/**
 * Created by jaapo on 21-1-2018.
 */

public class ModelGame {

    public ModelGameState mGameState;
    HashSet<GameStateListener> mListenerSet;

    public ModelGame() {
        mGameState = new ModelGameState();
        mListenerSet = new HashSet<>();
    }

    public void reset() {
        mGameState.dispose();
        mGameState = new ModelGameState();
        updateListeners();

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
            mGameState = new ModelGameState();
        } else {
            try {
                Gson gson = new Gson();
                Type type = new TypeToken<ModelGameState>() {
                }.getType();
                mGameState = gson.fromJson(gameStateJson, type);
            }
            catch (JsonSyntaxException e)
            {
                mGameState = new ModelGameState();
            }
        }
        mGameState.validate();
        updateListeners();

    }

    private void updateListeners() {
        for (GameStateListener listener: mListenerSet) {
            listener.updateGameState(mGameState);
        }
    }

    public void addGameStateListener(GameStateListener listener) {
        mListenerSet.add(listener);
        listener.updateGameState(mGameState);
    }

    public interface GameStateListener {
        void updateGameState(ModelGameState gameState);
    }
}
