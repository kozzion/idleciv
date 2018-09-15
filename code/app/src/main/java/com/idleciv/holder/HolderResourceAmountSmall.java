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

public class HolderResourceAmountSmall extends RecyclerView.ViewHolder implements ModelResourceStock.Listener{

    @BindView(R.id.item_resource_amount_small_iv_icon)
    ImageView mImageIcon;

    @BindView(R.id.item_resource_amount_small_tv_amount)
    TextView mTextAmount;


    private View mRootView;
    private ModelResourceAmount mResourceAmount;
    private ModelResourceStock mResourceStock;

    public HolderResourceAmountSmall(View rootView) {
        super(rootView);
        mRootView = rootView;
        ButterKnife.bind(this, itemView);
    }

    public void bind(ModelResourceAmount resourceAmount) {
        if(mResourceStock != null) {
            mResourceStock.removeListener(this);
        }
        mResourceAmount = resourceAmount;
        //Dirty hack
        mResourceStock = ((ActivityMain)mRootView.getContext()).mGameState.mEpochState.mResourceStockMap.get(mResourceAmount.mResourceIndex);

        mImageIcon.setImageResource(mResourceStock.getIcon());
        mTextAmount.setText(Integer.toString(mResourceAmount.mAmount));

        mResourceStock.addListener(this);
    }

    public void unbind(){
        mResourceStock.removeListener(this);
    }

    @Override
    public void updateResourceStockUI() {
        //TODO change color
        mTextAmount.setText(Integer.toString(mResourceAmount.mAmount));
    }
}

