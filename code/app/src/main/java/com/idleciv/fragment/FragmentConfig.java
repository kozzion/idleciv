package com.idleciv.fragment;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.idleciv.R;
import com.idleciv.activity.ActivityMain;
import com.idleciv.common.FragmentBase;
import com.idleciv.model.ModelEpochState;

import butterknife.BindView;

public class FragmentConfig extends FragmentBase implements ModelEpochState.GameStateListener {

    private boolean mIsInitialized = false;

    @BindView(R.id.config_bt_reset_game)
    Button mButtonResetGame;

    @BindView(R.id.config_bt_reset_epoch)
    Button mButtonResetEpoch;

    public ModelEpochState mGameState;

    @Override
    protected int getLayoutRes() {
        return R.layout.layout_fragment_config;
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIsInitialized = true;
        if (mGameState != null) {
            updateGameStateUI();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //Log.e(TAG, "onDestroyView: ");
        mIsInitialized = false;
    }

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
    public void updateGameStateUI() {
        if (mIsInitialized) {
            mButtonResetGame.setOnClickListener(v -> {
                ((ActivityMain)getContext()).mGameState.mActivityMain.resetGameState();
            });

            mButtonResetEpoch.setOnClickListener(v -> {
                ((ActivityMain)getContext()).mGameState.resetEpoch();
            });
        }
    }
}