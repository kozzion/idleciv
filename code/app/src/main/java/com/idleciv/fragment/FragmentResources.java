package com.idleciv.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.idleciv.R;
import com.idleciv.adapter.AdapterResourceStock;
import com.idleciv.holder.HolderTime;
import com.idleciv.model.ModelEpochState;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentResources implements ModelEpochState.EpochStateListener {

    public static final String TAG = FragmentResources.class.getName();
    private boolean mIsInitialized = false;

    @BindView(R.id.resources_ll_time)
    View mLayoutTime;

    @BindView(R.id.resources_rv_recycler)
    RecyclerView mRecycler;

    private AdapterResourceStock mAdapter;
    private HolderTime mHolderTime;

    public ModelEpochState mGameState;


    public View mRootView;

    public FragmentResources(View view) {
        mRootView = view;
        ButterKnife.bind(this, mRootView);
        mAdapter = new AdapterResourceStock(mRootView.getContext());
        mRecycler.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mRootView.getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setAutoMeasureEnabled(true);
        mRecycler.setLayoutManager(layoutManager);
//        mAdapter.setData(mEpochState.getEnabledIndustryList());

        mHolderTime = new HolderTime(mLayoutTime);

        mIsInitialized = true;
        if (mGameState != null) {
            updateEpochStateUI();
        }
    }

    /*@Override
    public void onDestroyView() {
        super.onDestroyView();
        //Log.e(TAG, "onDestroyView: ");
        mIsInitialized = false;
    }*/


    @Override
    public void updateEpochStateUI() {
        Log.e(TAG, "updateEpochStateUI: ");
        if (mIsInitialized) {
            mHolderTime.bind(mGameState.mTime);
            mAdapter.setData(mGameState.getEnabledResourceList());
        }
    }

    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            mIsInitialized = false;
        }
    }

    public void bind(ModelEpochState gameState) {
        Log.e(TAG, "bind");
        //Unbind previous
        if (mGameState != null) {
            mGameState.removeListener(this);
        }
        //Bind current
        mGameState = gameState;
        gameState.addListener(this);
    }
}