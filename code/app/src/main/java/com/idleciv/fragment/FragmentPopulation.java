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
import com.idleciv.activity.ActivityMain;
import com.idleciv.adapter.AdapterResourceAmount;
import com.idleciv.common.FragmentBase;
import com.idleciv.model.ModelGame;
import com.idleciv.model.ModelGameState;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by jaapo on 7-1-2018.
 */

public class FragmentPopulation extends FragmentBase implements ModelGameState.GameStateListener {

    private boolean mIsInitialized = false;

    @BindView(R.id.population_text_count)
    TextView mCount;

    @BindView(R.id.population_button_buy)
    Button mBuy;

    @BindView(R.id.population_recycler_cost)
    RecyclerView mRecycler;

    private AdapterResourceAmount mAdapter;


    public ModelGameState mGameState;

    public FragmentPopulation() {
        super();
        mAdapter = new AdapterResourceAmount(getContext());
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.layout_fragment_population;
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecycler.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setAutoMeasureEnabled(true);
        mRecycler.setLayoutManager(layoutManager);

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
        if (mIsInitialized) {
            if (0 < mGameState.mPopulationCostList.size()) {
                mAdapter.setData(mGameState.mPopulationCostList.get(0).mResourceCostList);
            } else {
                mAdapter.setData(new ArrayList<>());
            }

            mCount.setText(Integer.toString(mGameState.mPopulationTotal));

            mBuy.setOnClickListener(v -> {
                mGameState.buyPopulation();
                mGameState.updateUI();
            });
        }
    }

    public void bind(ModelGameState gameState) {
        //Unbind previous
        if (mGameState != null) {
            mGameState.removeListener(this);
        }
        //Bind current
        mGameState = gameState;
        gameState.addListener(this);
    }
}
