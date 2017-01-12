package com.lyc.exia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyc.exia.R;
import com.lyc.exia.bean.Gank;
import com.lyc.exia.ui.WebActivity;
import com.lyc.exia.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wayne on 2017/1/11.
 */

public class GankChildAdapter extends RecyclerView.Adapter {
    private Context context;
    List<Gank> list;

    public GankChildAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<Gank> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_tech_child, parent, false);
        GankChildHolder holder = new GankChildHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof GankChildHolder){
            final GankChildHolder gankChildHolder = (GankChildHolder) holder;
            gankChildHolder.setData();
            gankChildHolder.ll_tech_child_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Gank g = list.get(gankChildHolder.getAdapterPosition());
                    WebActivity.actionStart(context,g.getUrl(),g.getDesc());
                }
            });
        }
    }

    class GankChildHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_tech_child_layout)
        LinearLayout ll_tech_child_layout;
        @BindView(R.id.tv_tech_child_content)
        TextView tv_tech_child_content;

        public GankChildHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData(){
            Gank g = list.get(getAdapterPosition());
//            SpannableStringBuilder builder = new SpannableStringBuilder(g.getDesc()).append(
//                    format(tv_tech_child_content.getContext(), " (via. " +
//                            g.getWho() +
//                            ")", R.style.ViaTextAppearance));
//            CharSequence gankText = builder.subSequence(0, builder.length());
            tv_tech_child_content.setText(g.getDesc());
        }

    }
    public static SpannableString format(Context context, String text, int style) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new TextAppearanceSpan(context, style), 0, text.length(),
                0);
        return spannableString;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
}
