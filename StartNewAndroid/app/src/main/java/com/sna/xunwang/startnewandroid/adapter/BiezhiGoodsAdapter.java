package com.sna.xunwang.startnewandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sna.xunwang.startnewandroid.R;
import com.sna.xunwang.startnewandroid.bean.BiezhiGoodBean;
import com.sna.xunwang.startnewandroid.config.Constants;
import com.sna.xunwang.startnewandroid.view.LoadingView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xunwang on 16/11/9.
 */

public class BiezhiGoodsAdapter extends RecyclerView.Adapter<BiezhiGoodsAdapter.BiezhiViewHolder> {
    private Context mContext;
    private List<BiezhiGoodBean> biezhiGoodBeanlist;

    public BiezhiGoodsAdapter(Context mContext, List<BiezhiGoodBean> biezhiGoodBeanlist) {
        this.mContext = mContext;
        this.biezhiGoodBeanlist = biezhiGoodBeanlist;
    }

    public void setData(List<BiezhiGoodBean> biezhiGoodBeanlist) {
        this.biezhiGoodBeanlist = biezhiGoodBeanlist;
        notifyDataSetChanged();
    }

    @Override
    public BiezhiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BiezhiViewHolder holder = new BiezhiViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_biezhi_goods_list, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final BiezhiViewHolder holder, int position) {
        if (biezhiGoodBeanlist != null) {
            holder.biezhiTitie.setText(biezhiGoodBeanlist.get(position).getTitle());
            Glide.with(mContext).load(biezhiGoodBeanlist.get(position).getPicUrl()).animate(R.anim.item_alpha_in).into
                    (holder.biezhiPic);
            holder.biezhiPrice.setText(biezhiGoodBeanlist.get(position).getPrice());
            holder.biezhiLoadingView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingViewOutAnim(holder.biezhiLoadingView);
                }
            }, 1500);
        } else {
            holder.biezhiTitie.setText("");
            holder.biezhiPrice.setText("");
        }
    }

    private void loadingViewOutAnim(final LoadingView loadingView) {
        AnimationSet alphaAnimation = (AnimationSet) AnimationUtils.loadAnimation(mContext, R.anim.item_alpha_out);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                loadingView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        loadingView.startAnimation(alphaAnimation);
    }

    @Override
    public int getItemCount() {
        if (biezhiGoodBeanlist != null) {
            return biezhiGoodBeanlist.size();
        }
        return Constants.EVER_TIME_SHOW;
    }

    class BiezhiViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.bz_item_iv)
        ImageView biezhiPic;
        @BindView(R.id.bz_item_title_tv)
        TextView biezhiTitie;
        @BindView(R.id.bz_item_content_tv)
        TextView biezhiPrice;
        @BindView(R.id.bz_cool_wait_view)
        LoadingView biezhiLoadingView;

        public BiezhiViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
