package com.idleciv.fragment;


import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.idleciv.R;
import com.idleciv.adapter.AdapterIndustry;
import com.idleciv.adapter.AdapterResourceStock;
import com.idleciv.common.FragmentBase;
import com.idleciv.holder.HolderTime;
import com.idleciv.model.ModelGameState;

import butterknife.BindView;

public class FragmentResources extends FragmentBase implements ModelGameState.GameStateListener {

    private boolean mIsInitialized = false;

    @BindView(R.id.resources_ll_time)
    View mLayoutTime;

    @BindView(R.id.resources_rv_recycler)
    RecyclerView mRecycler;

    private AdapterResourceStock mAdapter;
    private HolderTime mHolderTime;

    public ModelGameState mGameState;


    @Override
    protected int getLayoutRes() {
        return R.layout.layout_fragment_resources;
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //Log.e(TAG, "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new AdapterResourceStock(getContext());
        mRecycler.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setAutoMeasureEnabled(true);
        mRecycler.setLayoutManager(layoutManager);
//        mAdapter.setData(mGameState.getEnabledIndustryList());

        mHolderTime = new HolderTime(mLayoutTime);

        mIsInitialized = true;
        if (mGameState != null) {
            updateGameStateUI();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //Log.e(TAG, "onDestroyView: ");
        mIsInitialized = false;
    }


    @Override
    public void updateGameStateUI() {
        Log.e(TAG, "updateGameStateUI: ");
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

    public void bind(ModelGameState gameState) {
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