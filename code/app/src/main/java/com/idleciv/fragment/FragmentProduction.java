package com.idleciv.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.idleciv.R;
import com.idleciv.adapter.AdapterIndustry;
import com.idleciv.holder.HolderTime;
import com.idleciv.model.ModelEpochState;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jaapo on 7-1-2018.
 */

public class FragmentProduction implements ModelEpochState.EpochStateListener {

    public static final String TAG = FragmentProduction.class.getName();

    private boolean mIsInitialized = false;

    @BindView(R.id.production_recycler)
    RecyclerView mRecycler;

    @BindView(R.id.production_ll_time)
    View mLayoutTime;

    private AdapterIndustry mAdapter;
    private HolderTime mHolderTime;

    public ModelEpochState mGameState;


    public View mRootView;

    public FragmentProduction(View view) {
        mRootView = view;
        ButterKnife.bind(this, mRootView);
        mAdapter = new AdapterIndustry(mRootView.getContext());
        mRecycler.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mRootView.getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setAutoMeasureEnabled(true);
        mRecycler.setLayoutManager(layoutManager);
//        mAdapter.setData(mEpochState.getEnabledIndustryList());

        mHolderTime = new HolderTime(mLayoutTime);

        mIsInitialized = true;
        if(mGameState != null) {
            updateEpochStateUI();
        }
    }

    @Override
    public void updateEpochStateUI() {
        Log.e(TAG, "updateEpochStateUI: ");
        if(mIsInitialized) {
            mHolderTime.bind(mGameState.mTime);
            mAdapter.setData(mGameState.getEnabledIndustryList());
        }
    }

    public void onHiddenChanged(boolean hidden) {
        if(hidden){
            mIsInitialized = false;
        }
    }

    public void bind(ModelEpochState gameState) {
        Log.e(TAG, "bind: 0");
        //Unbind previous
        if (mGameState != null) {
            mGameState.removeListener(this);
        }
        //Bind current
        mGameState = gameState;
        gameState.addListener(this);

        Log.e(TAG, "bind: 1");
    }
}
