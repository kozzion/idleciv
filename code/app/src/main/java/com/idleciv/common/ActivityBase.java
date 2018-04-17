package com.idleciv.common;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jaapo on 25-10-2017.
 */

public abstract class ActivityBase extends FragmentActivity {

    protected String TAG;
    Map<String, BroadcastReceiver> mFilterToRecieverMap;

    @Override
    public void onResume() {
        super.onResume();
        TAG = this.getClass().getName();
        mFilterToRecieverMap = getFilterToRecieverMap();
        registerRecievers();
    }


    @Override
    public void onPause() {
        super.onPause();
        unRegisterRecievers();

    }

    public void registerRecievers() {
        for (String filter : mFilterToRecieverMap.keySet()) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mFilterToRecieverMap.get(filter), new IntentFilter(filter));
        }
    }

    public void unRegisterRecievers() {
        for (BroadcastReceiver receiver : mFilterToRecieverMap.values()) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        }
    }

    public void localBroadcastSend(String filter, Intent intent) {
        intent.setAction(filter);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public  Map<String, BroadcastReceiver> getFilterToRecieverMap() {
        return new HashMap<>();
    }
}
