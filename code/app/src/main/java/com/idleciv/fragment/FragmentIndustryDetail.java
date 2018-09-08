package com.idleciv.fragment;

import android.widget.TextView;

import com.idleciv.R;
import com.idleciv.common.FragmentBase;
import com.idleciv.model.ModelGame;
import com.idleciv.model.ModelGameState;
import com.idleciv.model.ModelIndustry;
import com.idleciv.model.ResourceType;

import butterknife.BindView;

/**
 * Created by jaapo on 7-1-2018.
 */

public class FragmentIndustryDetail extends FragmentBase implements ModelGame.GameListener {





    @BindView(R.id.industry_detail_text_title)
    TextView mTitle;


    ModelGame mGame;
    ModelGameState mGameState;
    ModelIndustry mIndustry;

    public FragmentIndustryDetail(ModelGame game) {
        super();
        mGame = game;
        mGame.addGameListener(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_industry_detail;
    }

    public void setIndustry(ModelIndustry industry) {
        this.mIndustry = industry;
        mTitle.setText(ResourceType.getName(mIndustry.mResourceIndex));
    }

    @Override
    public void updateGame(ModelGameState gameState) {
        mGameState = gameState;
    }
}
