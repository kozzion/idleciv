package com.idleciv.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.idleciv.R;
import com.idleciv.adapter.AdapterPageView;
import com.idleciv.common.ActivityBase;
import com.idleciv.fragment.FragmentConfig;
import com.idleciv.fragment.FragmentPopulation;
import com.idleciv.fragment.FragmentProduction;
import com.idleciv.fragment.FragmentResources;
import com.idleciv.fragment.FragmentTechnology;
import com.idleciv.model.ModelGameState;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ActivityMain extends ActivityBase implements ModelGameState.GameStateListener {

    @BindView(R.id.main_vp)
    ViewPager mViewPager;

    @BindView(R.id.main_tl)
    TabLayout mTabLayout;

    AdapterPageView mAdapter;

    public ModelGameState mGameState;

    FragmentResources mFragmentResources;
    FragmentPopulation mFragmentPopulation;
    FragmentTechnology mFragmentTechnology;
    FragmentProduction mFragmentProduction;
    FragmentConfig mFragmentConfig;

    private Thread mThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAdapter = new AdapterPageView();
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);


    }

    @Override
    public void onResume() {
        super.onResume();

        mFragmentResources = new FragmentResources(getLayoutInflater().inflate(R.layout.layout_fragment_resources, null));
        mFragmentPopulation = new FragmentPopulation(getLayoutInflater().inflate(R.layout.layout_fragment_population, null));
        mFragmentProduction = new FragmentProduction(getLayoutInflater().inflate(R.layout.layout_fragment_production, null));
        mFragmentTechnology = new FragmentTechnology(getLayoutInflater().inflate(R.layout.layout_fragment_technology, null));
        mFragmentConfig = new FragmentConfig(getLayoutInflater().inflate(R.layout.layout_fragment_config, null));
        load();

        //Start update thread
        mThread = new Thread(() -> {
            long time = System.currentTimeMillis();
            while (true)
            {
                try {
                    Thread.sleep(14);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long newTime = System.currentTimeMillis();
                mGameState.updateState((newTime - time)/1000.0);
                time = newTime;

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        mGameState.updateUI();
                    }
                });
            }
        });
        mThread.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        //save();
        mThread.interrupt();
    }

    @Override
    public void onBackPressed() {
        if(mViewPager.getCurrentItem() == 1) {
            super.onBackPressed();
        } else {
            mViewPager.setCurrentItem(1);
        }
    }



    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
    }

    public void showPopup(String message){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ActivityMain.this, message, Toast.LENGTH_LONG).show();
            }
        });

    }

    public void resetGameState() {
        mGameState.dispose();
        mGameState = new ModelGameState(this);
        mGameState.addGameListener(this);
        updateGameStateUI();
    }

    public void save() {
        Gson gson = new Gson();
        Type type = new TypeToken<ModelGameState>() {}.getType();
        String gameStateJson = gson.toJson(mGameState, type);
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.putString("GameState", gameStateJson);
        edit.apply();
    }

    public void load() {
        if(mGameState != null) {
            mGameState.dispose();
        }
        String gameStateJson = getPreferences(Context.MODE_PRIVATE).getString("GameState", "");
        if(gameStateJson.equals("") || gameStateJson.equals("null")) {
            Log.e(TAG, "No Gamestate found, creating new GameState");
            mGameState = new ModelGameState(this);
        } else {
            try {
                Gson gson = new Gson();
                Type type = new TypeToken<ModelGameState>() {
                }.getType();
                Log.e(TAG, "Loading GameState");
                mGameState = gson.fromJson(gameStateJson, type);
            }
            catch (JsonSyntaxException e)
            {
                Log.e(TAG, "JsonSyntaxException creating new GameState as fallback");
                mGameState = new ModelGameState(this);
            }
        }
        mGameState = mGameState.validate(this);
        mGameState.addGameListener(this);
    }

    @Override
    public void updateGameStateUI() {
        Log.e(TAG, "updateEpochStateUI");
        showUnlockedTabs();
        mFragmentResources.bind(mGameState.mEpochState);
        mFragmentPopulation.bind(mGameState.mEpochState);
        mFragmentProduction.bind(mGameState.mEpochState);
        mFragmentTechnology.bind(mGameState.mEpochState);
        mFragmentConfig.bind(mGameState.mEpochState);
    }

    private void showUnlockedTabs() {

        List<View> viewList = new ArrayList<>();
        List<String> tabNameList = new ArrayList<>();

        viewList.add(mFragmentResources.mRootView);
        tabNameList.add("RES");

        if(mGameState.mUnlockedPopulation) {
            viewList.add(mFragmentPopulation.mRootView);
            tabNameList.add("POP");
        }

        if(mGameState.mUnlockedProduction) {
            viewList.add(mFragmentProduction.mRootView);
            tabNameList.add("Prod");
        }
        // TODO add expansion tab here
        //fragmentList.add(mFragmentIndustryDetail);
        //tabNameList.add("Det");

        if(mGameState.mUnlockedTechnology) {
            viewList.add(mFragmentTechnology.mRootView);
            tabNameList.add("tech");
        }

        viewList.add(mFragmentConfig.mRootView);
        tabNameList.add("Config");
        mAdapter.setData(mViewPager, viewList,tabNameList);
    }

}
