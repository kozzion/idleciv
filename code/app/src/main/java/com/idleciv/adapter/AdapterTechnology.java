package com.idleciv.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idleciv.R;
import com.idleciv.holder.HolderTechnology;
import com.idleciv.model.ModelTechnology;

import java.util.ArrayList;
import java.util.List;

public class AdapterTechnology extends RecyclerView.Adapter<HolderTechnology> {
    private static final int TYPE_ITEM = 0;

    private List<ModelTechnology> data;

    public AdapterTechnology(@NonNull Context context) {
        this.data = new ArrayList<>();
    }

    @Override
    public HolderTechnology onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View item = inflater.inflate(R.layout.layout_item_technology, parent, false);
        return new HolderTechnology(item);
    }

    @Override
    public void onBindViewHolder(HolderTechnology holder, int position) {
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

    public void setData(@NonNull ArrayList<ModelTechnology> newData) {
        data.clear();

        if (!newData.isEmpty()) {
            data.addAll(newData);
        }
        notifyDataSetChanged();
    }
}
