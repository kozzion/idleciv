package com.idleciv.model;

import android.content.Context;

import java.util.HashSet;

/**
 * Created by jaapo on 23-12-2017.
 */

public class ModelIndustry {

    public String mIndustryType;
    public double mLabor;
    public double mCapital;
    public double mProduction;
    public double mProductionPerUnit;

    public double mStock;
    public int mProgress;
    private HashSet<Listener> mListenerSet;

    public ModelIndustry(String industryType)
    {
        mIndustryType = industryType;
        mListenerSet = new HashSet<>();

        mLabor = 1;
        mCapital = 0.5;
    }

    public void save(Context context)
    {

    }

    public void load(Context context)
    {

    }

    public void updateState(double timeElapsed)
    {

        mProduction = mLabor * mCapital;
        mStock += mProduction * timeElapsed;



        mProgress = (int)((mStock - Math.floor(mStock)) * 100) ;
    }




    public void updateUI() {
        for (Listener listener: mListenerSet) {
            listener.updateIndustry(this);
        }
    }



    public void addListener(Listener listener) {
        mListenerSet.add(listener);

        listener.updateIndustry(this);
    }

    public void removeListener(Listener listener) {
        mListenerSet.remove(listener);
    }

    public void setLabor(double labor) {
        this.mLabor = labor;
    }


    public interface Listener {
        void updateIndustry(ModelIndustry industry);
    }
}
