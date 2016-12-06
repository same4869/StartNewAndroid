package com.sna.xunwang.startnewandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sna.xunwang.startnewandroid.R;
import com.sna.xunwang.startnewandroid.activity.CommWebviewActivity;
import com.sna.xunwang.startnewandroid.bean.DevelopItemBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by xunwang on 16/11/28.
 */

public class DevelopItemAdapter extends RecyclerView.Adapter<DevelopItemAdapter.DevelopItemHolder> {
    private Context mContext;
    private List<DevelopItemBean> list;

    public DevelopItemAdapter(Context mContext, List<DevelopItemBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public void setData(List<DevelopItemBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public DevelopItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DevelopItemAdapter.DevelopItemHolder holder = new DevelopItemAdapter.DevelopItemHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_develop, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(DevelopItemHolder holder, final int position) {
        if (list != null) {
            Glide.with(mContext).load(list.get(position).getItemImgUrl()).asBitmap().centerCrop().into(holder
                    .developImg);
            holder.developTitle.setText(list.get(position).getItemTitle());
            holder.developContent.setText(list.get(position).getItemContent());
            holder.developImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.get(position).increment("clickCounts");
                    list.get(position).update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Intent intent = new Intent(mContext, CommWebviewActivity.class);
                                intent.putExtra(CommWebviewActivity.COMMON_WEB_URL, list.get(position).getItemUrl());
                                mContext.startActivity(intent);
                            }
                        }
                    });

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class DevelopItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.develop_item_iv)
        ImageView developImg;
        @BindView(R.id.develop_item_title_tv)
        TextView developTitle;
        @BindView(R.id.develop_item_content_tv)
        TextView developContent;

        public DevelopItemHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
