package com.idleciv.holder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.idleciv.R;
import com.idleciv.model.ModelIndustry;
import com.idleciv.model.ModelResourceAmount;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jaapo on 23-12-2017.
 */

public class HolderIndustry extends RecyclerView.ViewHolder implements ModelIndustry.Listener {

    @BindView(R.id.item_industry_iv_icon)
    ImageView mImageIcon;

    @BindView(R.id.item_industry_tv_name)
    TextView mTextName;

    @BindView(R.id.item_industry_ll_consumption)
    View mConsumption;

    @BindView(R.id.item_industry_ll_production)
    View mProduction;

    @BindView(R.id.item_industry_bt_add_pop)
    Button mAddPop;

    @BindView(R.id.item_industry_bt_remove_pop)
    Button mRemovePop;

    @BindView(R.id.item_industry_tv_current_pop)
    TextView mCurrentPop;

    private View mRootView;
    private ModelIndustry mIndustry;

    HolderResourceAmountList mHolderConsumption;
    HolderResourceAmountList mHolderProduction;

    public HolderIndustry(View rootView) {
        super(rootView);
        mRootView = rootView;
        ButterKnife.bind(this, itemView);
        mHolderConsumption = new HolderResourceAmountList(mConsumption);
        mHolderProduction = new HolderResourceAmountList(mProduction);
    }

    public void bind(ModelIndustry modelIndustry) {
        //Log.e("ModelIndustry", "bind: ");
        mIndustry = modelIndustry;
        mIndustry.addListener(this);
/*

        mRootView.setOnClickListener(v -> {
            ((ActivityMain)mRootView.getContext()).showIndustryDetails(mIndustry);
        });
*/

        mAddPop.setOnClickListener(v -> {
            Log.e("ADD", "click: ");
            mIndustry.addPop();
        });

        mRemovePop.setOnClickListener(v -> {
            mIndustry.removePop();
        });
    }



    public void unbind(ModelIndustry modelIndustry) {
        mIndustry.removeListener(this);
    }

    @Override
    public void updateIndustryUI() {
        mTextName.setText(mIndustry.getName(mRootView.getContext()));
        //mTextStock.setText(Integer.toString((int)industry.mEpochState.mResourceStockMap.get(industry.mResourceIndex).mStock));
        //mTextProgress.setText(Double.toString(industry.mProgress) + " / 100");
        //mProgressProgress.setProgress(industry.mProgress);
        mImageIcon.setImageResource(mIndustry.getIcon());

        mCurrentPop.setText(Integer.toString(mIndustry.mPopulationCurrent));


        mHolderConsumption.bind(ModelResourceAmount.multiply(mIndustry.mConsumptionList, mIndustry.mPopulationCurrent));
        mHolderProduction.bind(ModelResourceAmount.multiply(mIndustry.mProductionList, mIndustry.mPopulationCurrent));


    }
}
