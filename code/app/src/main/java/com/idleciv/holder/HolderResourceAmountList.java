package com.idleciv.holder;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.idleciv.R;
import com.idleciv.adapter.AdapterResourceAmountSmall;
import com.idleciv.model.ModelResourceAmount;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HolderResourceAmountList {



    @BindView(R.id.item_resource_amount_small_list_rv)
    RecyclerView mRecycler;


    private AdapterResourceAmountSmall mAdapter;
    private View mRootView;

    public HolderResourceAmountList(View rootView){
        mRootView = rootView;
        ButterKnife.bind(this, rootView);
        mAdapter = new AdapterResourceAmountSmall();
        mRecycler.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mRootView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager.setAutoMeasureEnabled(true);
        mRecycler.setLayoutManager(layoutManager);
    }

    public void bind(ArrayList<ModelResourceAmount> resourceAmountList) {
        mAdapter.setData(resourceAmountList);
    }
}
