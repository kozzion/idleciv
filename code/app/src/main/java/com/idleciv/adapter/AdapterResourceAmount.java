package com.idleciv.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idleciv.R;
import com.idleciv.holder.HolderResourceAmount;
import com.idleciv.model.ModelResourceAmount;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaapo on 23-12-2017.
 */

public class AdapterResourceAmount extends RecyclerView.Adapter<HolderResourceAmount> {

    private static final int TYPE_ITEM = 0;

    private List<ModelResourceAmount> data;

    public AdapterResourceAmount(@NonNull Context context) {
        this.data = new ArrayList<>();
    }

    @Override
    public HolderResourceAmount onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View item = inflater.inflate(R.layout.layout_resource_amount_cost, parent, false);
        return new HolderResourceAmount(item);
    }

    @Override
    public void onBindViewHolder(HolderResourceAmount holder, int position) {
        holder.bind(data.get(position));
    }


    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(@NonNull List<ModelResourceAmount> newData) {
        data.clear();

        if (!newData.isEmpty()) {
            data.addAll(newData);
        }
        notifyDataSetChanged();
    }
}
