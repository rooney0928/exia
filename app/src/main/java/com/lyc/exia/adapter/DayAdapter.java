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
import com.lyc.exia.utils.ToastUtil;

import java.util.List;
import java.util.Random;

/**
 * Created by wayne on 2017/1/6.
 */

public class DayAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<String> list;

    public DayAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<String> list) {
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
        if (holder instanceof DayHolder) {
            DayHolder dayHolder = (DayHolder) holder;
            dayHolder.setData();
        }
    }

    class DayHolder extends RecyclerView.ViewHolder implements DayContract.View {
        DayPresenter dayPresenter;
        CardView cardView;
        ImageView iv_header;
        TextView tv_title;


        public DayHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            iv_header = (ImageView) itemView.findViewById(R.id.iv_header);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            dayPresenter = new DayPresenter(this);
        }

        public void setData() {
            /*
            String url = "http://imgsrc.baidu.com/forum/w%3D580/sign=e5a3bb1539292df597c3ac1d8c315ce2/a1258ecc7cd98d1070a4720b223fb80e7aec9094.jpg";
            String date = list.get(getAdapterPosition());

            Glide.with(context).load(url).centerCrop().into(iv_header);

            tv_title.setText(date);
            */
            String date = list.get(getAdapterPosition());
            String[] dates = date.split("-");

            dayPresenter.requestDayData(dates[0], dates[1], dates[2]);
        }

        @Override
        public void getDayData(DayBean dayBean) {
//            Log.e("test",dayBean.getResults().getAndroidList().size()+"------------");
            String benefit = dayBean.getResults().getBenefitList().get(0).getUrl();
            if (!TextUtils.isEmpty(benefit)) {
                Glide.with(context).load(benefit).centerCrop().into(iv_header);
            }
            String iosTitle = dayBean.getResults().getIosList().get(0).getDesc();
            String androidTitle = dayBean.getResults().getAndroidList().get(0).getDesc();

            String title = iosTitle + "\n" + androidTitle;
            tv_title.setText(title);
        }

        @Override
        public void getDayDataFailed(String error) {
            ToastUtil.showSimpleToast(context,error);
        }

        @Override
        public void updateView(DayBean serverData) {

        }

        @Override
        public void updateError(String error) {

        }

        @Override
        public void requestStart() {

        }

        @Override
        public void requestEnd() {

        }
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
}
