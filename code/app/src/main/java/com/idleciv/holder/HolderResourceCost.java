package com.idleciv.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.idleciv.R;
import com.idleciv.activity.ActivityMain;
import com.idleciv.model.ModelResourceAmount;
import com.idleciv.model.ModelResourceStock;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jaapo on 8-1-2018.
 */

public class HolderResourceCost extends RecyclerView.ViewHolder implements ModelResourceStock.Listener{

    @BindView(R.id.item_resource_amount_iv_icon)
    ImageView mImageIcon;

    @BindView(R.id.item_resource_amount_text_name)
    TextView mTextName;

    @BindView(R.id.item_resource_amount_text_stock)
    TextView mTextStock;

    private View mRootView;
    private ModelResourceAmount mResourceAmount;
    private ModelResourceStock mResourceStock;

    public HolderResourceCost(View rootView) {
        super(rootView);
        mRootView = rootView;
        ButterKnife.bind(this, itemView);
    }

    public void bind(ModelResourceAmount resourceAmount) {
        mResourceAmount = resourceAmount;
        //Dirty hack
        mResourceStock = ((ActivityMain)mRootView.getContext()).mGameState.mEpochState.mResourceStockMap.get(mResourceAmount.mResourceIndex);

        mTextName.setText(mResourceStock.getName(mRootView.getContext()));
        mImageIcon.setImageResource(mResourceStock.getIcon());
        mResourceStock.addListener(this);
    }

    public void unbind(){
        mResourceStock.removeListener(this);
    }

    @Override
    public void updateResourceStockUI() {
        mTextStock.setText(Integer.toString(mResourceStock.mStock) + "/" + Integer.toString(mResourceAmount.mAmount));

    }
}
