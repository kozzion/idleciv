package com.idleciv.fragment;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.idleciv.R;
import com.idleciv.activity.ActivityMain;
import com.idleciv.common.FragmentBase;
import com.idleciv.model.ModelGameState;

import java.util.ArrayList;

import butterknife.BindView;

public class FragmentConfig extends FragmentBase implements ModelGameState.GameStateListener {

    private boolean mIsInitialized = false;

    @BindView(R.id.config_bt_reset)
    Button mButtonReset;


    public ModelGameState mGameState;

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

    @Override
    public void updateGameStateUI() {
        if (mIsInitialized) {
            mButtonReset.setOnClickListener(v -> {
                ((ActivityMain)getContext()).mGame.reset();
            });
        }
    }

    public void bind(ModelGameState gameState) {
        //Unbind previous
        if (mGameState != null) {
            mGameState.removeListener(this);
        }
        //Bind current
        mGameState = gameState;
        gameState.addListener(this);
    }
}