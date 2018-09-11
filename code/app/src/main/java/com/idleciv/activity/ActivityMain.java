package com.idleciv.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.idleciv.R;
import com.idleciv.adapter.AdapterPage;
import com.idleciv.common.ActivityBase;
import com.idleciv.fragment.FragmentConfig;
import com.idleciv.fragment.FragmentIndustryDetail;
import com.idleciv.fragment.FragmentPopulation;
import com.idleciv.fragment.FragmentProduction;
import com.idleciv.fragment.FragmentResources;
import com.idleciv.fragment.FragmentTechnology;
import com.idleciv.model.ModelGame;
import com.idleciv.model.ModelGameState;
import com.idleciv.model.ModelIndustry;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ActivityMain extends ActivityBase implements ModelGame.GameListener{

    @BindView(R.id.main_viewpager)
    ViewPager mViewPager;

    AdapterPage mAdapter;

    public ModelGame mGame;

    FragmentResources mFragmentResources;
    FragmentPopulation mFragmentPopulation;
    FragmentTechnology mFragmentTechnology;
    FragmentProduction mFragmentProduction;
    FragmentIndustryDetail mFragmentIndustryDetail;
    FragmentConfig mFragmentConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGame = new ModelGame();
        mGame.load(this);

        mFragmentResources = new FragmentResources();
        mFragmentPopulation = new FragmentPopulation();
        mFragmentTechnology = new FragmentTechnology();
        mFragmentProduction = new FragmentProduction();
        mFragmentIndustryDetail = new FragmentIndustryDetail();
        mFragmentConfig = new FragmentConfig();

        //fragement politics
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(mFragmentResources);
        fragments.add(mFragmentPopulation);
        fragments.add(mFragmentProduction);
        fragments.add(mFragmentTechnology);
        fragments.add(mFragmentIndustryDetail);
        fragments.add(mFragmentConfig);

        mAdapter = new AdapterPage(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);

        mGame.addGameListener(this);

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
                mGame.updateState((newTime - time)/1000.0);
                time = newTime;

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        mGame.updateUI();
                    }
                });
            }
        }).start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mGame.save(this);
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
    public void onResume() {
        super.onResume();
        mGame.load(this);
    }





    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);

        //FragmentManager manager = getSupportFragmentManager();
        //FragmentTransaction transaction = manager.beginTransaction();
        //transaction.apply(R.id.fragment_container, fragment, fragmentKey);
    }

    public void showIndustryDetails(ModelIndustry industry) {
        mFragmentIndustryDetail.setIndustry(industry);
        mViewPager.setCurrentItem(2);
    }

    @Override
    public void updateGameUI() {
        Log.e(TAG, "updateGameUI");
        mFragmentResources.bind(mGame.mGameState);
        mFragmentPopulation.bind(mGame.mGameState);
        mFragmentProduction.bind(mGame.mGameState);
        mFragmentTechnology.bind(mGame.mGameState);
        mFragmentIndustryDetail.bind(mGame.mGameState);
        mFragmentConfig.bind(mGame.mGameState);
    }
}
