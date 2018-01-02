package com.idleciv.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.idleciv.R;
import com.idleciv.adapter.AdapterIndustry;
import com.idleciv.model.ModelGameState;
import com.idleciv.model.ModelIndustry;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ActivityMain extends AppCompatActivity {

    @BindView(R.id.main_recycler)
    RecyclerView mRecycler;

    AdapterIndustry mAdapter;

    ModelGameState mGameState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        load();



        new Thread(() -> {
            long time = System.currentTimeMillis();
            while (true)
            {
                try {
                    Thread.sleep(14);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long newTime = System.currentTimeMillis();
                updateIndustry((newTime - time)/1000.0);
                time = newTime;

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        updateIndustryUI();
                    }
                });
            }
        }).start();
    }

    private void save() {
        Gson gson = new Gson();
        Type type = new TypeToken<ModelGameState>() {}.getType();
        String gameStateJson = gson.toJson(mGameState, type);
        SharedPreferences  sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.putString("GameState", gameStateJson);
        edit.apply();
    }

    private void load() {
        String gameStateJson = getPreferences(Context.MODE_PRIVATE).getString("GameState", "");
        if(gameStateJson.equals("") || gameStateJson.equals("null")) {
            mGameState = new ModelGameState();
            mGameState.mIndustryList = new ArrayList<>();
            mGameState.mIndustryList.add(new ModelIndustry("Food"));
            mGameState.mIndustryList.add(new ModelIndustry("Lumber"));
            ModelIndustry ward = new ModelIndustry("Ward");
            ward.setLabor(0.6);
            mGameState.mIndustryList.add(ward);
        } else {
            Gson gson = new Gson();
            Type type = new TypeToken<ModelGameState>() {
            }.getType();
            mGameState = gson.fromJson(gameStateJson, type);
        }
        mGameState.validate();
        updateAdapter();
    }


    @Override
    protected void onPause() {
        super.onPause();
        save();
    }


    @Override
    protected void onResume() {
        super.onResume();
        load();
    }



    public void updateIndustry(double elapsedSeconds)
    {
       for (ModelIndustry industry: mGameState.mIndustryList) {
            industry.updateState(elapsedSeconds);
        }
    }

    public void updateIndustryUI()
    {
        for (ModelIndustry industry: mGameState.mIndustryList) {
            industry.updateUI();
        }
    }

    public void updateAdapter()
    {
        mAdapter.setData(mGameState.mIndustryList);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        mAdapter = new AdapterIndustry(this);
        mRecycler.setAdapter(mAdapter);
        RecyclerView.LayoutManager lManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        lManager.setAutoMeasureEnabled(true);
        mRecycler.setLayoutManager(lManager);

    }
}
