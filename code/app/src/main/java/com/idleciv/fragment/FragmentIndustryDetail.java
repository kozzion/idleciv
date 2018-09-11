package com.idleciv.fragment;

import android.widget.TextView;

import com.idleciv.R;
import com.idleciv.common.FragmentBase;
import com.idleciv.model.ModelGame;
import com.idleciv.model.ModelGameState;
import com.idleciv.model.ModelIndustry;
import com.idleciv.model.ModelResourceStock;

import butterknife.BindView;

/**
 * Created by jaapo on 7-1-2018.
 */

public class FragmentIndustryDetail extends FragmentBase implements ModelGameState.GameStateListener {

    private boolean mIsInitialized = false;

    @BindView(R.id.industry_detail_text_title)
    TextView mTitle;


    ModelGameState mGameState;
    ModelIndustry mIndustry;


    @Override
    protected int getLayoutRes() {
        return R.layout.layout_fragment_industry;
    }

    public void setIndustry(ModelIndustry industry) {
        this.mIndustry = industry;
        mTitle.setText(ModelIndustry.getName(mIndustry.mIndustryIndex));
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        //Log.e(TAG, "onDestroyView: ");
        mIsInitialized = false;
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

    @Override
    public void updateGameStateUI() {

    }
}
