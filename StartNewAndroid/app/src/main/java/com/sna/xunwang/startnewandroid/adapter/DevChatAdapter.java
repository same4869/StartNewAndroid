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
    private OnItemClickListener onItemClickListener;

    public DevChatAdapter(Context mContext, List<String> lists) {
        this.mContext = mContext;
        this.lists = lists;
    }

    public void setData(List<String> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    @Override
    public DevChatAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DevChatAdapterHolder holder = new DevChatAdapterHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_dev_chat, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(DevChatAdapterHolder holder, final int position) {
        if (lists != null) {
            holder.devChatTv.setText(lists.get(position));
            holder.devChatTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
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
