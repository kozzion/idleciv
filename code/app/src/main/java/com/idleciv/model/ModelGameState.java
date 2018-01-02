package com.idleciv.model;

import java.util.ArrayList;

/**
 * Created by jaapo on 2-1-2018.
 */

public class ModelGameState {

    public ArrayList<ModelIndustry> mIndustryList;

    public ModelGameState()
    {
        mIndustryList = new ArrayList<>();
    }
    
    public void validate()
    {
        for (ModelIndustry industry : mIndustryList) {
            industry.validate();
        }
    }


}
