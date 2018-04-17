package com.idleciv.fragment;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.idleciv.R;
import com.idleciv.adapter.AdapterIndustry;
import com.idleciv.common.FragmentBase;
import com.idleciv.model.ModelGame;
import com.idleciv.model.ModelGameState;

import butterknife.BindView;

/**
 * Created by jaapo on 7-1-2018.
 */

public class FragmentProduction extends FragmentBase implements ModelGame.GameStateListener {

    @BindView(R.id.production_recycler)
    RecyclerView mRecycler;

    @BindView(R.id.production_button_reset)
    Button mButtonReset;


    AdapterIndustry mAdapter;


    ModelGame mGame;
    ModelGameState mGameState;

    public FragmentProduction(ModelGame game) {
        super();
        mGame = game;
        mGame.addGameStateListener(this);
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_production;
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new AdapterIndustry(getContext());
        mRecycler.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setAutoMeasureEnabled(true);
        mRecycler.setLayoutManager(layoutManager);
        mAdapter.setData(mGameState.mIndustryList);

        mButtonReset.setOnClickListener(v -> mGame.reset());
    }

    @Override
    public void updateGameState(ModelGameState gameState) {
        mGameState = gameState;
        if(mAdapter != null) {
            mAdapter.setData(mGameState.mIndustryList);
        }
    }
}
