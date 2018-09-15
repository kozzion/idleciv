package com.idleciv.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.idleciv.R;
import com.idleciv.adapter.AdapterTechnology;
import com.idleciv.holder.HolderTechnologyDetails;
import com.idleciv.holder.HolderTechnologyItem;
import com.idleciv.model.ModelEpochState;
import com.idleciv.model.ModelTechnology;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jaapo on 7-1-2018.
 */

public class FragmentTechnology implements ModelEpochState.EpochStateListener, HolderTechnologyItem.TechnologyListener {

    public static final String TAG = FragmentTechnology.class.getName();
    private boolean mIsInitialized = false;

    @BindView(R.id.technology_rv_availeble)
    RecyclerView mRecycler;

    @BindView(R.id.technology_ll_details)
    View mLayoutDetails;

    AdapterTechnology mAdapter;

    ModelEpochState mGameState;

    private HolderTechnologyDetails mDetailsHolder;


    public View mRootView;

    public FragmentTechnology(View view) {
        mRootView = view;
        ButterKnife.bind(this, mRootView);
        mAdapter = new AdapterTechnology(mRootView.getContext(), this);

        mRecycler.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mRootView.getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setAutoMeasureEnabled(true);
        mRecycler.setLayoutManager(layoutManager);

        mDetailsHolder = new HolderTechnologyDetails(mLayoutDetails);
        mIsInitialized = true;
        if(mGameState != null) {
            updateEpochStateUI();
        }
    }

    public void setTechnology(ModelTechnology technology) {
        mDetailsHolder.bind(technology);
    }

    @Override
    public void updateEpochStateUI() {
        Log.e(TAG, "updateEpochStateUI: ");
        if(mIsInitialized) {
            mAdapter.setData(mGameState.getAvailebleTechnologyList());
        }
    }

    public void bind(ModelEpochState gameState) {
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
