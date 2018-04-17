package com.idleciv.fragment;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.idleciv.R;
import com.idleciv.adapter.AdapterResourceAmount;
import com.idleciv.common.FragmentBase;
import com.idleciv.model.ModelGame;
import com.idleciv.model.ModelGameState;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by jaapo on 7-1-2018.
 */

public class FragmentPopulation extends FragmentBase implements ModelGame.GameStateListener {

    @BindView(R.id.population_text_count)
    TextView mCount;

    @BindView(R.id.population_button_buy)
    Button mBuy;

    @BindView(R.id.population_recycler_cost)
    RecyclerView mRecycler;

    AdapterResourceAmount mAdapter;

    public ModelGame mGame;
    public ModelGameState mGameState;

    public FragmentPopulation(ModelGame game) {
        super();
        mGame = game;
        mGameState = mGame.mGameState;
        mGame.addGameStateListener(this);
        mAdapter = new AdapterResourceAmount(getContext());
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_population;
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecycler.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setAutoMeasureEnabled(true);
        mRecycler.setLayoutManager(layoutManager);
    }

    public void updateUI() {
        if(0 <mGameState.mPopulationCostList.size()) {
            mAdapter.setData(mGameState.mPopulationCostList.get(0).mResourceCostList);
        } else {
            mAdapter.setData(new ArrayList<>());
        }
        if(mCount != null) {
            mCount.setText(Integer.toString(mGameState.mPopulation));
            mBuy.setOnClickListener(v -> {
                mGameState.buyPopulation();

                if(0 <mGameState.mPopulationCostList.size()) {
                    mAdapter.setData(mGameState.mPopulationCostList.get(0).mResourceCostList);
                } else {
                    mAdapter.setData(new ArrayList<>());
                }
                mCount.setText(Integer.toString(mGameState.mPopulation));
            });
        }
    }

    @Override
    public void updateGameState(ModelGameState gameState) {
        mGameState = gameState;
    }
}
