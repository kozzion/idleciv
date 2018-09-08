package com.idleciv.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.idleciv.R;
import com.idleciv.activity.ActivityMain;
import com.idleciv.model.ModelIndustry;
import com.idleciv.model.ModelTechnology;
import com.idleciv.model.ResourceType;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HolderTechnology extends RecyclerView.ViewHolder {

    @BindView(R.id.item_technology_iv_icon)
    ImageView mImageIcon;

    @BindView(R.id.item_technology_tv_name)
    TextView mTextName;

    @BindView(R.id.item_technology_tv_description)
    TextView mDescription;

    @BindView(R.id.item_technology_bt_acquire)
    Button mResearch;


    private View mRootView;
    private ModelTechnology mTechnology;

    public HolderTechnology(View rootView) {
        super(rootView);
        mRootView = rootView;
        ButterKnife.bind(this, itemView);
    }

    public void bind(ModelTechnology modelIndustry) {
        mTechnology = modelIndustry;
        mImageIcon.setImageResource(ModelTechnology.getIcon(mTechnology.mTechnologyIndex));
        mTextName.setText(mTechnology.mName);
        mDescription.setText(mTechnology.mDescription);
        mResearch.setOnClickListener(v -> {
            mTechnology.research();
        });
    }
}
