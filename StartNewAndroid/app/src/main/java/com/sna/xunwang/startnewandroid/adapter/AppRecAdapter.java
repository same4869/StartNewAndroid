package com.sna.xunwang.startnewandroid.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sna.xunwang.startnewandroid.R;
import com.sna.xunwang.startnewandroid.bean.AppRecBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xunwang on 16/11/28.
 */

public class AppRecAdapter extends RecyclerView.Adapter<AppRecAdapter.AppRecHolder> {
    private Context mContext;
    private List<AppRecBean> list;
    private ItemClickInterface itemClickInterface;

    public AppRecAdapter(Context mContext, List<AppRecBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public AppRecHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AppRecAdapter.AppRecHolder holder = new AppRecAdapter.AppRecHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_app_rec, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(AppRecHolder holder, final int position) {
        if (list != null) {
            holder.appPic.setImageResource(list.get(position).getAppImgSrc());
            holder.appTitie.setText(list.get(position).getAppTitle());
            holder.appContent.setText(list.get(position).getAppContent());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickInterface != null) {
                        itemClickInterface.onItemClickInterface(view, position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ItemClickInterface {
        void onItemClickInterface(View view, int position);
    }

    public void setOnItemClickInterface(ItemClickInterface itemClickInterface) {
        this.itemClickInterface = itemClickInterface;
    }

    public static class AppRecHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.app_item_iv)
        ImageView appPic;
        @BindView(R.id.app_item_title_tv)
        TextView appTitie;
        @BindView(R.id.app_item_content_tv)
        TextView appContent;
        @BindView(R.id.app_card_layout)
        CardView cardView;

        public AppRecHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
