package com.idleciv.fragment;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.idleciv.R;
import com.idleciv.activity.ActivityMain;
import com.idleciv.adapter.AdapterTechnology;
import com.idleciv.common.FragmentBase;
import com.idleciv.holder.HolderTechnologyDetails;
import com.idleciv.holder.HolderTechnologyItem;
import com.idleciv.model.ModelGame;
import com.idleciv.model.ModelGameState;
import com.idleciv.model.ModelTechnology;

import butterknife.BindView;

/**
 * Created by jaapo on 7-1-2018.
 */

public class FragmentTechnology extends FragmentBase implements ModelGameState.GameStateListener, HolderTechnologyItem.TechnologyListener {

    private boolean mIsInitialized = false;

    @BindView(R.id.technology_rv_availeble)
    RecyclerView mRecycler;

    @BindView(R.id.technology_ll_details)
    View mLayoutDetails;

    AdapterTechnology mAdapter;
    ModelGame mGame;
    ModelGameState mGameState;

    private HolderTechnologyDetails mDetailsHolder;


    @Override
    protected int getLayoutRes() {
        return R.layout.layout_fragment_technology;
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new AdapterTechnology(getContext(), this);

        mRecycler.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setAutoMeasureEnabled(true);
        mRecycler.setLayoutManager(layoutManager);

        mDetailsHolder = new HolderTechnologyDetails(mLayoutDetails);
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

    public void setTechnology(ModelTechnology technology) {
        mDetailsHolder.bind(technology);
    }

    @Override
    public void updateGameStateUI() {
        Log.e(TAG, "updateGameStateUI: ");
        if(mIsInitialized) {
            mAdapter.setData(mGameState.getAvailebleTechnologyList());
        }
    }

    public void bind(ModelGameState gameState) {
        Log.e(TAG, "bind: ");
        //Unbind previous
        if (mGameState != null) {
            mGameState.removeListener(this);
        }
        //Bind current
        mGameState = gameState;
        gameState.addListener(this);
    }
}
