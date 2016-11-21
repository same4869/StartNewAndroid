package com.sna.xunwang.startnewandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sna.xunwang.startnewandroid.R;
import com.sna.xunwang.startnewandroid.bean.BiezhiGoodsBean;
import com.sna.xunwang.startnewandroid.manager.ShareManager;

import butterknife.BindView;
import butterknife.OnClick;

public class BiezhiDetailActivity extends BaseActivity {
    private static final String EXTRA_DETAIL = "extra_detail";
    @BindView(R.id.bzd_detail_image)
    ImageView bzdDetailImage;
    @BindView(R.id.bzd_detail_title)
    TextView detailTitle;
    @BindView(R.id.bzd_detail_source)
    TextView detailSource;

    private String url;
    private BiezhiGoodsBean dailyBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_biezhi_detail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            dailyBean = (BiezhiGoodsBean) intent.getSerializableExtra(EXTRA_DETAIL);
            if (dailyBean != null) {
                Glide.with(this).load(dailyBean.getPicUrl()).animate(R.anim.item_alpha_in).into
                        (bzdDetailImage);
                detailTitle.setText(dailyBean.getTitle());
                detailSource.setText(dailyBean.getPrice());
                url = dailyBean.getUrl();
            }
        }
    }

    @OnClick(R.id.next_btn)
    void nextPage() {
        Intent intent = new Intent(this, CommWebviewActivity.class);
        intent.putExtra(CommWebviewActivity.COMMON_WEB_URL, url);
        startActivity(intent);
    }

    @OnClick(R.id.share_btn)
    void startShare() {
        ShareManager.getInstance().showShare(getApplicationContext(), dailyBean);
    }

    public static void lanuch(Context context, BiezhiGoodsBean dailyBean) {
        Intent mIntent = new Intent(context, BiezhiDetailActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra(EXTRA_DETAIL, dailyBean);
        context.startActivity(mIntent);
    }
}
