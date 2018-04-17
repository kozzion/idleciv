package com.idleciv.common;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class FragmentBase extends MvpAppCompatFragment {

    protected String TAG;
    private Map<String,BroadcastReceiver> mFilterToReceiverMap;
    private Unbinder binding;

    @LayoutRes
    protected abstract int getLayoutRes();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFilterToReceiverMap = getFilterToReceiverMap();
        return inflater.inflate(getLayoutRes(), container, false);
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = ButterKnife.bind(this, view);
        TAG = this.getClass().getName();
    }

    @CallSuper
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.unbind();
    }

    public boolean isAlive() {
        return getActivity() != null && isAdded() && !isDetached() && getView() != null && !isRemoving();
    }

    @Override
    public void onResume() {
        super.onResume();
        for (String filter: mFilterToReceiverMap.keySet()) {
            LocalBroadcastManager.getInstance(getContext()).registerReceiver(mFilterToReceiverMap.get(filter), new IntentFilter(filter));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        for (BroadcastReceiver receiver: mFilterToReceiverMap.values()) {
            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
        }
    }
    public Map<String, BroadcastReceiver> getFilterToReceiverMap()
    {
        return new HashMap<>();
    }
}
