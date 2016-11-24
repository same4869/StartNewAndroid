package com.sna.xunwang.startnewandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sna.xunwang.startnewandroid.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xunwang on 16/11/24.
 */

public class DevChatAdapter extends RecyclerView.Adapter<DevChatAdapter.DevChatAdapterHolder> {
    private Context mContext;
    private List<String> lists;

    public DevChatAdapter(Context mContext, List<String> lists) {
        this.mContext = mContext;
        this.lists = lists;
    }

    @Override
    public DevChatAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DevChatAdapterHolder holder = new DevChatAdapterHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_dev_chat, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(DevChatAdapterHolder holder, int position) {
        if (lists != null) {
            holder.devChatTv.setText(lists.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public static class DevChatAdapterHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.dev_chat_tv)
        TextView devChatTv;

        public DevChatAdapterHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
