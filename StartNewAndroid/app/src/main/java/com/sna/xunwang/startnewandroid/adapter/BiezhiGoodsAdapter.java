package com.sna.xunwang.startnewandroid.adapter;

import android.content.Context;
import android.graphics.Color;
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
import com.ldoublem.thumbUplib.ThumbUpView;
import com.sna.xunwang.startnewandroid.R;
import com.sna.xunwang.startnewandroid.activity.BiezhiDetailActivity;
import com.sna.xunwang.startnewandroid.bean.BiezhiGoodsBean;
import com.sna.xunwang.startnewandroid.config.Constants;
import com.sna.xunwang.startnewandroid.utils.ToastUtil;
import com.sna.xunwang.startnewandroid.utils.XLog;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

/**
 * Created by xunwang on 16/11/9.
 */

public class BiezhiGoodsAdapter extends RecyclerView.Adapter<BiezhiGoodsAdapter.BiezhiViewHolder> {
    private Context mContext;
    private List<BiezhiGoodsBean> biezhiGoodBeanlist;
    private boolean showDanmaku;

    private BaseDanmakuParser parser = new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };

    public BiezhiGoodsAdapter(Context mContext, List<BiezhiGoodsBean> biezhiGoodBeanlist) {
        this.mContext = mContext;
        this.biezhiGoodBeanlist = biezhiGoodBeanlist;
    }

    public void setData(List<BiezhiGoodsBean> biezhiGoodBeanlist) {
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
    public void onBindViewHolder(final BiezhiViewHolder holder, final int position) {
        if (biezhiGoodBeanlist != null) {
            holder.biezhiLoadingView.smoothToShow();
            holder.biezhiTitie.setText(biezhiGoodBeanlist.get(position).getTitle());
            Glide.with(mContext).load(biezhiGoodBeanlist.get(position).getPicUrl()).animate(R.anim.item_alpha_in).into
                    (holder.biezhiPic);
            holder.biezhiPrice.setText(biezhiGoodBeanlist.get(position).getPrice());
            holder.biezhiLoadingView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingViewOutAnim(holder.biezhiLoadingView);

                    HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<Integer, Boolean>();
                    overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_LR, true);
                    overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_BOTTOM, true);
                    holder.danmakuContext = DanmakuContext.create();
                    holder.danmakuContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 2)
                            .setScaleTextSize(1.0f).setDuplicateMergingEnabled(true).setScrollSpeedFactor(0.6f)
                            .preventOverlapping(overlappingEnablePair);
                }
            }, 1500);
            holder.biezhiPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BiezhiDetailActivity.lanuch(mContext, biezhiGoodBeanlist.get(position));
                }
            });
            holder.biezhiDanmuIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    XLog.d(Constants.TAG, "holder.isDanmuOpen --> " + holder.isDanmuOpen + " showDanmaku --> " +
                            showDanmaku);
                    holder.danmakuView.toggle();
                    if (!holder.isDanmuOpen) {
                        holder.danmakuView.stop();
                        holder.biezhiDanmuIv.setImageResource(R.mipmap.bz_danmu_open);
                        initDamuview(holder);
                        holder.isDanmuOpen = true;
                    } else {
                        if (holder.danmakuView != null && showDanmaku) {
                            XLog.d(Constants.TAG, "removeAllDanmakus");
                            holder.danmakuView.clear();
                            holder.danmakuView.removeAllDanmakus(true);
                            holder.danmakuView.stop();
                            holder.biezhiDanmuIv.setImageResource(R.mipmap.bz_danmu_close);
                            showDanmaku = false;
                            holder.isDanmuOpen = false;
                        }
                    }
                }
            });
            holder.thumbUpView.setOnThumbUp(new ThumbUpView.OnThumbUp() {
                @Override
                public void like(boolean like) {
                    if(like){
                        ToastUtil.showToast(mContext, "收藏成功");
                    }else{
                        ToastUtil.showToast(mContext, "取消收藏成功");
                    }
                }
            });
        } else {
            holder.biezhiTitie.setText("");
            holder.biezhiPrice.setText("");
        }
    }

    private void initDamuview(final BiezhiViewHolder holder) {
        holder.danmakuView.enableDanmakuDrawingCache(true);
        holder.danmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                showDanmaku = true;
                holder.danmakuView.start();
                generateSomeDanmaku(holder.danmakuContext, holder.danmakuView);
            }

            @Override
            public void updateTimer(DanmakuTimer timer) {
            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {
            }

            @Override
            public void drawingFinished() {
                XLog.d(Constants.TAG, "drawingFinished showDanmaku --> " + showDanmaku);
                if (holder.danmakuView != null && showDanmaku) {
                    holder.biezhiDanmuIv.setImageResource(R.mipmap.bz_danmu_close);
                    showDanmaku = false;
                    holder.isDanmuOpen = false;
                }
            }
        });
        holder.danmakuView.prepare(parser, holder.danmakuContext);
    }

    /**
     * 向弹幕View中添加一条弹幕
     *
     * @param content    弹幕的具体内容
     * @param withBorder 弹幕是否有边框
     */
    private void addDanmaku(DanmakuContext danmakuContext, DanmakuView danmakuView, String content, boolean
            withBorder) {
        BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.text = content;
        danmaku.padding = 5;
        danmaku.textSize = sp2px(20);
        danmaku.textColor = createRandomColor();
        danmaku.setTime(danmakuView.getCurrentTime());
        if (withBorder) {
            danmaku.borderColor = Color.GREEN;
        }
        danmakuView.addDanmaku(danmaku);
    }

    private int createRandomColor() {
        return Color.argb(255, (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
    }

    /**
     * 随机生成一些弹幕内容以供测试
     */
    private void generateSomeDanmaku(final DanmakuContext danmakuContext, final DanmakuView danmakuView) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 30; i++) {
                    if (showDanmaku) {
                        int time = new Random().nextInt(300);
                        String content = "" + time + time + "仿古教室多发几个司法机关";
                        addDanmaku(danmakuContext, danmakuView, content, false);
                        try {
                            Thread.sleep(time);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }


    /**
     * sp转px的方法。
     */
    public int sp2px(float spValue) {
        final float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    private void loadingViewOutAnim(final AVLoadingIndicatorView loadingView) {
        AnimationSet alphaAnimation = (AnimationSet) AnimationUtils.loadAnimation(mContext, R.anim.item_alpha_out);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                loadingView.hide();
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

    public static class BiezhiViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.bz_item_iv)
        ImageView biezhiPic;
        @BindView(R.id.bz_item_title_tv)
        TextView biezhiTitie;
        @BindView(R.id.bz_item_content_tv)
        TextView biezhiPrice;
        @BindView(R.id.bz_cool_wait_view)
        AVLoadingIndicatorView biezhiLoadingView;
        @BindView(R.id.bz_danmaku_view)
        DanmakuView danmakuView;
        @BindView(R.id.bz_danmu_iv)
        ImageView biezhiDanmuIv;
        @BindView(R.id.bz_collect)
        ThumbUpView thumbUpView;
        private DanmakuContext danmakuContext;
        private boolean isDanmuOpen;

        public BiezhiViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
