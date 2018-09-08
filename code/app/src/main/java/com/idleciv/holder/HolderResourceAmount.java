package com.idleciv.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.idleciv.R;
import com.idleciv.activity.ActivityMain;
import com.idleciv.model.ModelResourceAmount;
import com.idleciv.model.ResourceType;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jaapo on 8-1-2018.
 */

public class HolderResourceAmount extends RecyclerView.ViewHolder {


    @BindView(R.id.item_resource_amount_image_icon)
    ImageView mImageIcon;

    @BindView(R.id.item_resource_amount_text_name)
    TextView mTextName;

    @BindView(R.id.item_resource_amount_text_stock)
    TextView mTextStock;

    private View mRootView;
    private ModelResourceAmount mResourceAmount;

    public HolderResourceAmount(View rootView) {
        super(rootView);
        mRootView = rootView;
        ButterKnife.bind(this, itemView);
    }

    public void bind(ModelResourceAmount resourceAmount) {
        mResourceAmount = resourceAmount;
        mTextName.setText(ResourceType.getName(mResourceAmount.mResourceIndex));
        mImageIcon.setImageResource(ResourceType.getIcon(mResourceAmount.mResourceIndex));

        int stock = (int)((ActivityMain)mRootView.getContext()).mGame.mGameState.mIndustryList.get(mResourceAmount.mResourceIndex).mStock;
        mTextStock.setText(Integer.toString(stock) + "/" + Integer.toString(mResourceAmount.mAmount));

    /*    mRootView.setOnClickListener(v -> {
            ((ActivityMain)mRootView.getContext()).showIndustryDetails(industry);

        });*/

    }
}
