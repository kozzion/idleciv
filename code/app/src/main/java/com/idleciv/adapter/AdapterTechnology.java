package com.idleciv.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idleciv.R;
import com.idleciv.holder.HolderTechnologyItem;
import com.idleciv.model.ModelTechnology;

import java.util.ArrayList;
import java.util.List;

public class AdapterTechnology extends RecyclerView.Adapter<HolderTechnologyItem> {
    private static final int TYPE_ITEM = 0;

    private List<ModelTechnology> mData;
    private ModelTechnology.TechnologyListener mListener;

    public AdapterTechnology(@NonNull Context context, ModelTechnology.TechnologyListener listener) {
        this.mData = new ArrayList<>();
        mListener = listener;
    }

    @Override
    public HolderTechnologyItem onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View item = inflater.inflate(R.layout.layout_item_technology, parent, false);
        return new HolderTechnologyItem(item, mListener);
    }

    @Override
    public void onBindViewHolder(HolderTechnologyItem holder, int position) {
        holder.bind(mData.get(position));
    }


    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(@NonNull ArrayList<ModelTechnology> newData) {
        mData.clear();

        if (!newData.isEmpty()) {
            mData.addAll(newData);
        }
        notifyDataSetChanged();
    }
}
