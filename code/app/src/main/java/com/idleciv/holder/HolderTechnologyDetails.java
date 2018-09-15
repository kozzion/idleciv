package com.idleciv.holder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.idleciv.R;
import com.idleciv.model.ModelModifier;
import com.idleciv.model.ModelTechnology;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HolderTechnologyDetails {
    @BindView(R.id.details_technology_iv_icon)
    ImageView mImageIcon;

    @BindView(R.id.details_technology_tv_name)
    TextView mTextName;

    @BindView(R.id.details_technology_tv_description)
    TextView mDescription;

    @BindView(R.id.details_technology_bt_research)
    Button mResearch;


    private View mRootView;
    private ModelTechnology mTechnology;

    public HolderTechnologyDetails(View rootView) {
        mRootView = rootView;
        ButterKnife.bind(this, rootView);
    }

    public void bind(ModelTechnology modelIndustry) {
        mTechnology = modelIndustry;
        mImageIcon.setImageResource(mTechnology.getIcon());
        mTextName.setText(mTechnology.getName(mRootView.getContext()));
        StringBuilder builder = new StringBuilder();
        builder.append(mTechnology.getDescription(mRootView.getContext()));
        for (ModelModifier modifier: mTechnology.mModifierList) {
            builder.append("\n");
            builder.append(modifier.getDescription(mRootView.getContext(), mTechnology.mEpochState));
        }
        mDescription.setText(builder.toString());
        mResearch.setOnClickListener(v -> {
            mTechnology.research();
        });
    }
}
