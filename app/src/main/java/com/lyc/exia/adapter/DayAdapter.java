package com.lyc.exia.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lyc.exia.R;
import com.lyc.exia.bean.DayBean;
import com.lyc.exia.contract.DayContract;
import com.lyc.exia.presenter.DayPresenter;
import com.lyc.exia.utils.LogU;
import com.lyc.exia.utils.ToastUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Random;

/**
 * Created by wayne on 2017/1/6.
 */

public class DayAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<DayBean.ResultsBean> list;

    public DayAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<DayBean.ResultsBean> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_day, parent, false);
        final DayHolder holder = new DayHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showSimpleToast(context, "this is " + holder.getAdapterPosition());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof DayHolder && position >= 0) {
            DayHolder dayHolder = (DayHolder) holder;
            dayHolder.setData();
        }
    }

    class DayHolder extends RecyclerView.ViewHolder {
        DayPresenter dayPresenter;
        CardView cardView;
        ImageView iv_header;
        TextView tv_title;


        public DayHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            iv_header = (ImageView) itemView.findViewById(R.id.iv_header);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        }

        public void setData() {
            DayBean.ResultsBean bean = list.get(getAdapterPosition());
            String url = getUrlFromHtml(bean.getContent());
            LogU.t("url--"+url);
            if(!TextUtils.isEmpty(url)){
                Glide.with(context).load(url).centerCrop().into(iv_header);
            }
            String publish = bean.getPublishedAt();
            String[] dates = publish.split("T");
            tv_title.setText(dates[0] + " " + bean.getTitle());


//            if (getAdapterPosition() ==11) {
//                dayPresenter.requestDayData(dates[0], dates[1], dates[2]);
//            }
        }

    }

    private String getUrlFromHtml(String content) {
//        LogU.t(content);
        Document doc = Jsoup.parse(content);
        Element pic = doc.getElementsByTag("img").first();
        return pic.attr("src");
//        Elements pEles = doc.getElementsByTag("p");
//        for (int i = 0; i < pEles.size(); i++) {
//            Element p = pEles.get(i);
//
//        }
    }


    @Override
    public int getItemCount() {

        return list == null ? 0 : list.size();
    }
}
