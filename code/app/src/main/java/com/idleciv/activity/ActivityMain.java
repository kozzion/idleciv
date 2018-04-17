package com.idleciv.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.idleciv.R;
import com.idleciv.adapter.AdapterPage;
import com.idleciv.common.ActivityBase;
import com.idleciv.fragment.FragmentIndustryDetail;
import com.idleciv.fragment.FragmentPopulation;
import com.idleciv.fragment.FragmentProduction;
import com.idleciv.model.ModelGame;
import com.idleciv.model.ModelIndustry;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ActivityMain extends ActivityBase {

    @BindView(R.id.main_viewpager)
    ViewPager mViewPager;

    AdapterPage mAdapter;

    public ModelGame mGame;

    FragmentPopulation mFragmentPopulation;
    FragmentProduction mFragmentProduction;
    FragmentIndustryDetail mFragmentIndustryDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGame = new ModelGame();

        mFragmentPopulation = new FragmentPopulation(mGame);
        mFragmentProduction = new FragmentProduction(mGame);
        mFragmentIndustryDetail = new FragmentIndustryDetail(mGame);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(mFragmentPopulation);
        fragments.add(mFragmentProduction);
        fragments.add(mFragmentIndustryDetail);

        mAdapter = new AdapterPage(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(1);

        mGame.load(this);

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



    public void updateIndustry(double elapsedSeconds)
    {
        for (ModelIndustry industry: mGame.mGameState.mIndustryList) {
            industry.updateState(elapsedSeconds);
        }
    }

    public void updateIndustryUI()
    {
        for (ModelIndustry industry: mGame.mGameState.mIndustryList) {
            industry.updateUI();
        }

        if(mFragmentPopulation != null)
        {
            mFragmentPopulation.updateUI();
        }
    }


    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);

        //FragmentManager manager = getSupportFragmentManager();
        //FragmentTransaction transaction = manager.beginTransaction();
        //transaction.add(R.id.fragment_container, fragment, fragmentKey);



    }

    public void showIndustryDetails(ModelIndustry industry) {
        mFragmentIndustryDetail.setIndustry(industry);
        mViewPager.setCurrentItem(2);
    }
}
