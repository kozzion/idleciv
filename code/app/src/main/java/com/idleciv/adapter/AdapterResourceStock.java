package com.idleciv.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idleciv.R;
import com.idleciv.holder.HolderIndustry;
import com.idleciv.holder.HolderResourceStock;
import com.idleciv.model.ModelIndustry;
import com.idleciv.model.ModelResourceStock;

import java.util.ArrayList;
import java.util.List;

public class AdapterResourceStock extends RecyclerView.Adapter<HolderResourceStock> {
    private static final int TYPE_ITEM = 0;

    private List<ModelResourceStock> data;

    public AdapterResourceStock(@NonNull Context context) {
        this.data = new ArrayList<>();
    }

    @Override
    public HolderResourceStock onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View item = inflater.inflate(R.layout.layout_item_resources_stock, parent, false);
        return new HolderResourceStock(item);
    }

    @Override
    public void onBindViewHolder(HolderResourceStock holder, int position) {
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

    public void setData(@NonNull ArrayList<ModelResourceStock> newData) {
        data.clear();

        if (!newData.isEmpty()) {
            data.addAll(newData);
        }
        notifyDataSetChanged();
    }
}
