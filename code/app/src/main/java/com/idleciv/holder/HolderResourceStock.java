package com.idleciv.holder;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.idleciv.R;
import com.idleciv.model.ModelIndustry;
import com.idleciv.model.ModelResourceStock;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HolderResourceStock extends RecyclerView.ViewHolder implements ModelResourceStock.Listener {

    @BindView(R.id.item_resource_stock_iv_icon)
    ImageView mImageIcon;

    @BindView(R.id.item_resource_stock_tv_name)
    TextView mTextName;

    @BindView(R.id.item_resource_stock_tv_production)
    TextView mTextProduction;

    @BindView(R.id.item_resource_stock_tv_demand)
    TextView mTextConsumption;

    @BindView(R.id.item_resource_stock_tv_stock)
    TextView mTextStock;

    @BindView(R.id.item_resource_stock_pb_capacity)
    ProgressBar mProgressCapacity;

    private View mRootView;
    private ModelResourceStock mResourceStock;

    public HolderResourceStock(View rootView) {
        super(rootView);
        mRootView = rootView;
        ButterKnife.bind(this, itemView);
        mProgressCapacity.setMax(100);
    }

    public void bind(ModelResourceStock resourceStock) {
        //Log.e("ModelIndustry", "bind: ");
        mResourceStock = resourceStock;
        mResourceStock.addListener(this);
    }


    public void unbind(ModelIndustry modelIndustry) {
        mResourceStock.removeListener(this);
    }

    @Override
    public void updateResourceStockUI() {
        mImageIcon.setImageResource(mResourceStock.getIcon());
        mTextName.setText(mResourceStock.getName(mRootView.getContext()));
        mTextProduction.setText("+" + Integer.toString(mResourceStock.mProduction));
        mTextConsumption.setText("-" + Integer.toString(mResourceStock.mDemand));
        mProgressCapacity.setProgress(mResourceStock.mStock);
        mProgressCapacity.setMax(mResourceStock.mCapacity);
        mTextStock.setText(Integer.toString(mResourceStock.mStock) + " / " + Integer.toString(mResourceStock.mCapacity));
    }
}
