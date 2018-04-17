package com.windtalker.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jaapo on 2-11-2017.
 */

public abstract class ServiceBase extends Service {

    Map<String, BroadcastReceiver> mFilterToRecieverMap;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand (Intent intent,int flags,  int startId)    {
        super.onCreate();
        mFilterToRecieverMap = getFilterToRecieverMap();
        registerRecievers();
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
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

    public Map<String, BroadcastReceiver> getFilterToRecieverMap() {
        return new HashMap<>();
    }
    public abstract void localBroadcastRecieve(Context context, String filter, Intent intent);


}
