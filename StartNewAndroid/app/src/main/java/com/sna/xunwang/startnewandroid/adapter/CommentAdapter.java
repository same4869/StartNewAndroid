package com.sna.xunwang.startnewandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sna.xunwang.startnewandroid.R;
import com.sna.xunwang.startnewandroid.bean.CommentBean;
import com.sna.xunwang.startnewandroid.utils.StringUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xunwang on 16/12/1.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private Context mContext;
    private List<CommentBean> commentBeanlist;

    public CommentAdapter(Context mContext, List<CommentBean> commentBeanlist) {
        this.mContext = mContext;
        this.commentBeanlist = commentBeanlist;
    }

    public void setData(List<CommentBean> commentBeanlist) {
        this.commentBeanlist = commentBeanlist;
        notifyDataSetChanged();
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommentViewHolder holder = new CommentViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_comment, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(CommentViewHolder viewHolder, int position) {
        if (commentBeanlist != null) {
            final CommentBean comment = commentBeanlist.get(position);
            if (comment.getUser() != null) {
                if (!StringUtil.isStringNullorBlank(comment.getUser().getName())) {
                    viewHolder.username.setText(comment.getUser().getName());
                } else {
                    viewHolder.username.setText(comment.getUser().getUsername());
                }
            }
            switch (position) {
                case 0:
                    viewHolder.index.setText("沙发");
                    break;
                case 1:
                    viewHolder.index.setText("板凳");
                    break;
                case 2:
                    viewHolder.index.setText("地板");
                    break;
                default:
                    viewHolder.index.setText((position + 1) + "楼");
                    break;
            }

            viewHolder.content.setText(comment.getCommentContent());
        }
    }

    @Override
    public int getItemCount() {
        return commentBeanlist.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.userName_comment)
        TextView username;
        @BindView(R.id.index_comment)
        TextView index;
        @BindView(R.id.content_comment)
        TextView content;

        public CommentViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
