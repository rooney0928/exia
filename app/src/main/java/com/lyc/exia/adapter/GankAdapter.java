package com.lyc.exia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.lyc.exia.bean.Gank;

import java.util.List;

/**
 * Created by wayne on 2017/1/10.
 */

public class GankAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Gank> list;

    public GankAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<Gank> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
}
