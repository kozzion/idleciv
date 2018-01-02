package com.idleciv.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.idleciv.R;
import com.idleciv.adapter.AdapterIndustry;
import com.idleciv.model.ModelIndustry;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ActivityMain extends AppCompatActivity {

    @BindView(R.id.main_recycler)
    RecyclerView mRecycler;

    AdapterIndustry mAdapter;

    List<ModelIndustry> mIndustryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIndustryList = new ArrayList<>();
        mIndustryList.add(new ModelIndustry("Food"));
        mIndustryList.add(new ModelIndustry("Lumber"));
        ModelIndustry ward = new ModelIndustry("Ward");
        ward.setLabor(0.5);
        mIndustryList.add(ward);

        updateAdapter();

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

    public void updateIndustry(double elapsedSeconds)
    {
        Log.e("updateIndustry", "updateIndustry: " + elapsedSeconds);
        for (ModelIndustry industry: mIndustryList) {
            industry.updateState(elapsedSeconds);
        }
    }

    public void updateIndustryUI()
    {
        for (ModelIndustry industry: mIndustryList) {
            industry.updateUI();
        }
    }

    public void updateAdapter()
    {
        mAdapter.setData(mIndustryList);
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
