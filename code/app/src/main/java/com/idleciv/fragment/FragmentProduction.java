package com.idleciv.fragment;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.idleciv.R;
import com.idleciv.adapter.AdapterIndustry;
import com.idleciv.common.FragmentBase;
import com.idleciv.holder.HolderTime;
import com.idleciv.model.ModelEpochState;

import butterknife.BindView;

/**
 * Created by jaapo on 7-1-2018.
 */

public class FragmentProduction extends FragmentBase implements ModelEpochState.GameStateListener {

    private boolean mIsInitialized = false;

    @BindView(R.id.production_recycler)
    RecyclerView mRecycler;

    @BindView(R.id.production_ll_time)
    View mLayoutTime;

    private AdapterIndustry mAdapter;
    private HolderTime mHolderTime;

    public ModelEpochState mGameState;


    @Override
    protected int getLayoutRes() {
        return R.layout.layout_fragment_production;
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new AdapterIndustry(getContext());
        mRecycler.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setAutoMeasureEnabled(true);
        mRecycler.setLayoutManager(layoutManager);
//        mAdapter.setData(mEpochState.getEnabledIndustryList());

        mHolderTime = new HolderTime(mLayoutTime);

        mIsInitialized = true;
        if(mGameState != null) {
            updateGameStateUI();
        }
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        //Log.e(TAG, "onDestroyView: ");
        mIsInitialized = false;
    }


    @Override
    public void updateGameStateUI() {
        Log.e(TAG, "updateGameStateUI: ");
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
