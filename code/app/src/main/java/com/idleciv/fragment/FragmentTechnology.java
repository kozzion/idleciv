package com.idleciv.fragment;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.idleciv.R;
import com.idleciv.adapter.AdapterTechnology;
import com.idleciv.common.FragmentBase;
import com.idleciv.model.ModelGame;
import com.idleciv.model.ModelGameState;

import butterknife.BindView;

/**
 * Created by jaapo on 7-1-2018.
 */

public class FragmentTechnology extends FragmentBase implements ModelGameState.GameStateListener {

    @BindView(R.id.technology_rv_availeble)
    RecyclerView mRecycler;


    AdapterTechnology mAdapter;


    ModelGame mGame;
    ModelGameState mGameState;

    public FragmentTechnology(ModelGame game) {
        super();
        mGame = game;
        mGameState = game.mGameState;
        mGameState.addListener(this);
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.layout_fragment_technology;
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new AdapterTechnology(getContext());
        mRecycler.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setAutoMeasureEnabled(true);
        mRecycler.setLayoutManager(layoutManager);
        mAdapter.setData(mGameState.mTechnologyAvailableList);


    }

    @Override
    public void updateGameState(ModelGameState gameState) {
        mGameState = gameState;
        if(mAdapter != null) {
            bind(gameState);
        }
    }

    public void bind(ModelGameState gameState) {
        mAdapter.setData(mGameState.mTechnologyAvailableList);
    }
}
