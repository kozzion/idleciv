package com.idleciv.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.idleciv.R;
import com.idleciv.model.ModelIndustry;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jaapo on 23-12-2017.
 */

public class HolderIndustry extends RecyclerView.ViewHolder implements ModelIndustry.Listener {


    @BindView(R.id.item_industry_image_icon)
    ImageView mImageIcon;


    @BindView(R.id.item_industry_text_name)
    TextView mTextName;

    @BindView(R.id.item_industry_text_stock)
    TextView mTextStock;

    @BindView(R.id.item_industry_text_progress)
    TextView mTextProgress;

    @BindView(R.id.item_industry_progress_progress)
    ProgressBar mProgressProgress;

    private View mRootView;
    private ModelIndustry mIndustry;
    public HolderIndustry(View rootView) {
        super(rootView);
        mRootView = rootView;
        ButterKnife.bind(this, itemView);
        mProgressProgress.setMax(100);
    }

    public void bind(ModelIndustry modelIndustry) {
        mIndustry = modelIndustry;
        mIndustry.addListener(this);
    }

    public void unbind(ModelIndustry modelIndustry) {
        mIndustry.removeListener(this);
    }

    @Override
    public void updateIndustry(ModelIndustry industry) {
        mTextName.setText(industry.mIndustryType);
        mTextStock.setText(Integer.toString((int)industry.mStock));
        mTextProgress.setText(Double.toString(industry.mProgress) + " / 100");
        mProgressProgress.setProgress(industry.mProgress);
        switch (industry.mIndustryType) {
            case "Food": mImageIcon.setImageResource(R.drawable.wheat); break;
            case "Lumber": mImageIcon.setImageResource(R.drawable.lumber); break;
            default: mImageIcon.setImageResource(R.drawable.wheat); break;
        }

    }
}
