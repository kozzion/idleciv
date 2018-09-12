package com.idleciv.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.idleciv.R;
import com.idleciv.model.ModelTechnology;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HolderTechnologyItem extends RecyclerView.ViewHolder {

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
    private TechnologyListener mListener;

    public HolderTechnologyItem(View rootView, TechnologyListener listener) {
        super(rootView);
        mRootView = rootView;
        mListener = listener;
        ButterKnife.bind(this, itemView);
    }

    public void bind(ModelTechnology modelIndustry) {
        mTechnology = modelIndustry;
        mImageIcon.setImageResource(ModelTechnology.getIcon(mTechnology.mTechnologyIndex));
        mTextName.setText(ModelTechnology.getName(mTechnology.mTechnologyIndex));
        mDescription.setText(ModelTechnology.getDescription(mTechnology.mTechnologyIndex));
        mRootView.setOnClickListener(v -> {
            mListener.setTechnology(mTechnology);
        });
        mResearch.setOnClickListener(v -> {
            mTechnology.research();
        });
    }

    public interface TechnologyListener{
        void setTechnology(ModelTechnology technology);
    }
}
