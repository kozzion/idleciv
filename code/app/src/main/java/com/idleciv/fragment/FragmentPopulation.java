package com.idleciv.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.idleciv.R;
import com.idleciv.adapter.AdapterResourceCost;
import com.idleciv.model.ModelEpochState;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jaapo on 7-1-2018.
 */

public class FragmentPopulation implements ModelEpochState.EpochStateListener {

    private boolean mIsInitialized = false;

    @BindView(R.id.population_text_count)
    TextView mCount;

    @BindView(R.id.population_button_buy)
    Button mBuy;

    @BindView(R.id.population_recycler_cost)
    RecyclerView mRecycler;

    private AdapterResourceCost mAdapter;

    public ModelEpochState mGameState;


    public View mRootView;

    public FragmentPopulation(View view) {
        mRootView = view;
        ButterKnife.bind(this, mRootView);
        mAdapter = new AdapterResourceCost(mRootView.getContext());
        mRecycler.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mRootView.getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setAutoMeasureEnabled(true);
        mRecycler.setLayoutManager(layoutManager);

        mIsInitialized = true;
        if(mGameState != null) {
            updateEpochStateUI();
        }
    }

    @Override
    public void updateEpochStateUI() {
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

    public void bind(ModelEpochState gameState) {
        //Unbind previous
        if (mGameState != null) {
            mGameState.removeListener(this);
        }
        //Bind current
        mGameState = gameState;
        gameState.addListener(this);
    }
}
