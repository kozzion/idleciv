package com.idleciv.holder;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.idleciv.R;
import com.idleciv.model.ModelTime;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HolderTime implements ModelTime.TimeListener{

    @BindView(R.id.item_time_tv_progress)
    TextView mTextProgress;

    @BindView(R.id.item_time_progress_progress)
    ProgressBar mProgressProgress;


    View mRootView;
    public ModelTime mTime;
    public HolderTime(View rootView){
        mRootView = rootView;
        ButterKnife.bind(this, mRootView);
    }

    public void bind(ModelTime time) {
        //Log.e("HolderTime", "bind: ");
        if (mTime != null) {
            mTime.removeListener(this);
        }
        mTime = time;
        mTime.addListener(this);
    }

    @Override
    public void updateTimeUI() {
        //Log.e("HolderTime", "updateTime: " + mTime.mYearProgress);
        mProgressProgress.setProgress((int)mTime.mYearProgress);
    }
}
