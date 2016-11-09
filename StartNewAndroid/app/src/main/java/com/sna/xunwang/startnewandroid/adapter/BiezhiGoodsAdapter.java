package com.sna.xunwang.startnewandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sna.xunwang.startnewandroid.R;

import java.util.List;

/**
 * Created by xunwang on 16/11/9.
 */

public class BiezhiGoodsAdapter extends RecyclerView.Adapter<BiezhiGoodsAdapter.BiezhiViewHolder> {
    private Context mContext;
    private List<String> mDatas;

    public BiezhiGoodsAdapter(Context mContext, List<String> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    public BiezhiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BiezhiViewHolder holder = new BiezhiViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_biezhi_goods_list, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(BiezhiViewHolder holder, int position) {
        holder.tv.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class BiezhiViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public BiezhiViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.item_title);
        }
    }
}
