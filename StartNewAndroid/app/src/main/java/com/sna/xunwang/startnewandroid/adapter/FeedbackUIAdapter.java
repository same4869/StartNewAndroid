package com.sna.xunwang.startnewandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sna.xunwang.startnewandroid.R;
import com.sna.xunwang.startnewandroid.bean.FeedbackUIBean;
import com.sna.xunwang.startnewandroid.bean.UserBean;
import com.sna.xunwang.startnewandroid.config.Constants;
import com.sna.xunwang.startnewandroid.utils.TimeUtil;
import com.sna.xunwang.startnewandroid.utils.UserUtil;
import com.sna.xunwang.startnewandroid.utils.XLog;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.datatype.BmobFile;

import static android.R.attr.type;

/**
 * Created by xunwang on 16/11/23.
 */

public class FeedbackUIAdapter extends RecyclerView.Adapter<FeedbackUIAdapter.FeedbackUIHolder> {
    private final int TYPE_0 = 0, TYPE_1 = 1;
    private Context context;
    private List<FeedbackUIBean> feedbackUIList;

    public FeedbackUIAdapter(Context context, List<FeedbackUIBean> feedbackUIList) {
        this.context = context;
        this.feedbackUIList = feedbackUIList;
    }

    @Override
    public FeedbackUIHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FeedbackUIHolder feedbackUIHolder = null;
        if (viewType == TYPE_0) {
            feedbackUIHolder = new FeedbackUIHolder(LayoutInflater.from(
                    context).inflate(R.layout.listview_item_teacher, parent,
                    false));
        } else if (viewType == TYPE_1) {
            feedbackUIHolder = new FeedbackUIHolder(LayoutInflater.from(
                    context).inflate(R.layout.listview_item_student, parent,
                    false));
        }
        return feedbackUIHolder;
    }

    @Override
    public void onBindViewHolder(FeedbackUIHolder holder, int position) {
        if (feedbackUIList != null) {
            FeedbackUIBean bean = feedbackUIList.get(position);
            UserBean userBean = UserUtil.getUserInfo();
            String avaterUrl = null;
            if(userBean != null) {
                BmobFile avatarFile = userBean.getAvatar();
                int type = getItemViewType(position);
                if (avatarFile != null) {
                    avaterUrl = avatarFile.getFileUrl();
                }
                XLog.d(Constants.TAG, "avaterUrl --> " + avaterUrl + " type --> " + type);
            }
            holder.time.setText(TimeUtil.getCurrentTime(new Date().getTime()));
            holder.message.setText(bean.gettMessage());
            if (type == TYPE_0) {
                if (avaterUrl != null) {
                    Glide.with(context).load(avaterUrl).asBitmap().into(holder.portrait);
                } else {
                    holder.portrait.setImageResource(R.drawable.user_icon_default);
                }
            } else {
                holder.portrait.setImageResource(R.drawable.about_icon);
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        int tp = feedbackUIList.get(position).getId();
        if (TYPE_0 == tp) {
            return TYPE_0;
        } else if (TYPE_1 == tp) {
            return TYPE_1;
        }
        return TYPE_0;
    }

    @Override
    public int getItemCount() {
        return feedbackUIList.size();
    }

    public void addItemNotifiChange(FeedbackUIBean bean) {
        feedbackUIList.add(bean);
        notifyDataSetChanged();
    }

    public static class FeedbackUIHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.Time)
        TextView time;
        @BindView(R.id.Msg)
        TextView message;
        @BindView(R.id.Img)
        ImageView portrait;

        public FeedbackUIHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
