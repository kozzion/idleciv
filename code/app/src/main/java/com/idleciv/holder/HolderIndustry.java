package com.idleciv.holder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.idleciv.R;
import com.idleciv.activity.ActivityMain;
import com.idleciv.model.ModelIndustry;
import com.idleciv.model.ModelResourceStock;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jaapo on 23-12-2017.
 */

public class HolderIndustry extends RecyclerView.ViewHolder implements ModelIndustry.Listener {

    @BindView(R.id.item_industry_image_icon)
    ImageView mImageIcon;

    @BindView(R.id.item_industry_tv_name)
    TextView mTextName;

    @BindView(R.id.item_industry_tv_stock)
    TextView mTextStock;

    @BindView(R.id.item_time_tv_progress)
    TextView mTextProgress;

    @BindView(R.id.item_industry_progress_progress)
    ProgressBar mProgressProgress;

    @BindView(R.id.industry_bt_add_pop)
    Button mAddPop;

    @BindView(R.id.industry_bt_remove_pop)
    Button mRemovePop;

    @BindView(R.id.industry_tv_current_pop)
    TextView mCurrentPop;

    private View mRootView;
    private ModelIndustry mIndustry;

    public HolderIndustry(View rootView) {
        super(rootView);
        mRootView = rootView;
        ButterKnife.bind(this, itemView);
        mProgressProgress.setMax(100);
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
        mTextName.setText(ModelIndustry.getName(mIndustry.mIndustryIndex));
        //mTextStock.setText(Integer.toString((int)industry.mGameState.mResourceStockMap.get(industry.mResourceIndex).mStock));
        //mTextProgress.setText(Double.toString(industry.mProgress) + " / 100");
        //mProgressProgress.setProgress(industry.mProgress);
        mImageIcon.setImageResource(ModelIndustry.getIcon(mIndustry.mIndustryIndex));

        mCurrentPop.setText(Integer.toString(mIndustry.mPopulationIndustry));


    }
}
