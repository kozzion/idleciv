package com.idleciv.fragment;

import android.view.View;
import android.widget.Button;

import com.idleciv.R;
import com.idleciv.activity.ActivityMain;
import com.idleciv.model.ModelEpochState;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentConfig  implements ModelEpochState.EpochStateListener {

    private boolean mIsInitialized = false;

    @BindView(R.id.config_bt_reset_game)
    Button mButtonResetGame;

    @BindView(R.id.config_bt_reset_epoch)
    Button mButtonResetEpoch;

    public ModelEpochState mGameState;

    public View mRootView;
    public FragmentConfig(View view) {
        mRootView = view;
        ButterKnife.bind(this, mRootView);
        mIsInitialized = true;
        if (mGameState != null) {
            updateEpochStateUI();
        }
    }
/*

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //Log.e(TAG, "onDestroyView: ");
        mIsInitialized = false;
    }
*/

    public void bind(ModelEpochState gameState) {
        //Unbind previous
        if (mGameState != null) {
            mGameState.removeListener(this);
        }
        //Bind current
        mGameState = gameState;
        gameState.addListener(this);
    }

    @Override
    public void updateEpochStateUI() {
        if (mIsInitialized) {
            mButtonResetGame.setOnClickListener(v -> {
                ((ActivityMain)mRootView.getContext()).mGameState.mActivityMain.resetGameState();
            });

            mButtonResetEpoch.setOnClickListener(v -> {
                ((ActivityMain)mRootView.getContext()).mGameState.resetEpoch();
            });
        }
    }
}