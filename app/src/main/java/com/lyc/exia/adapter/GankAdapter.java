package com.lyc.exia.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lyc.exia.R;
import com.lyc.exia.bean.Gank;
import com.lyc.exia.utils.LogU;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wayne on 2017/1/10.
 */

public class GankAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<List<Gank>> list;


    public GankAdapter(Context context) {
        this.context = context;

    }

    public void setList(List<List<Gank>> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_tech, parent, false);
        GankHolder holder = new GankHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GankHolder) {
            GankHolder gankHolder = (GankHolder) holder;
            gankHolder.setData();
        }
    }

    class GankHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_tech_title)
        TextView tv_tech_title;
        @BindView(R.id.rv_techs)
        RecyclerView rv_techs;

        GankChildAdapter adapter;
        LinearLayoutManager linearLayoutManager;

        public GankHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            adapter = new GankChildAdapter(context);
            rv_techs.setLayoutManager(linearLayoutManager);
            rv_techs.setAdapter(adapter);
        }

        public void setData() {
            List<Gank> gankList = list.get(getAdapterPosition());

            if (gankList != null && gankList.size() > 0 && TextUtils.isEmpty(tv_tech_title.getText())) {
                tv_tech_title.setText(gankList.get(0).getType());
            }
            adapter.setList(gankList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
}
