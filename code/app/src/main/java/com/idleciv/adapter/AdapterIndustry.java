package com.idleciv.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idleciv.R;
import com.idleciv.model.ModelIndustry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaapo on 23-12-2017.
 */

public class AdapterIndustry extends RecyclerView.Adapter<HolderIndustry> {

    private static final int TYPE_ITEM = 0;

    private List<ModelIndustry> data;

    public AdapterIndustry(@NonNull Context context) {
        this.data = new ArrayList<>();
    }

    @Override
    public HolderIndustry onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View item = inflater.inflate(R.layout.item_industry, parent, false);
        return new HolderIndustry(item);
    }

    @Override
    public void onBindViewHolder(HolderIndustry holder, int position) {
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

    public void setData(@NonNull List<ModelIndustry> newData) {
        data.clear();

        if (!newData.isEmpty()) {
            data.addAll(newData);
        }

        notifyDataSetChanged();
    }
}
