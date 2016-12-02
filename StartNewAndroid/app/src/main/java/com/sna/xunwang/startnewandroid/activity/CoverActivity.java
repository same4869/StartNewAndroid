package com.sna.xunwang.startnewandroid.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sna.xunwang.startnewandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoverActivity extends Activity {

    @BindView(R.id.iv_luanch)
    ImageView mLuanchImage;

    private static final int ANIMATION_DURATION = 2000;

    private static final float SCALE_END = 1.13F;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            if (msg.what == 0) {
                animateImage();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_cover);
        ButterKnife.bind(this);

        Glide.with(this).load(R.drawable.splash).into(mLuanchImage);
        mHandler.sendEmptyMessageDelayed(0, 1000);
    }

    private void animateImage() {

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mLuanchImage, "scaleX", 1f, SCALE_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mLuanchImage, "scaleY", 1f, SCALE_END);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIMATION_DURATION).play(animatorX).with(animatorY);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {

                startActivity(new Intent(CoverActivity.this, SNAMainActivity.class));
                CoverActivity.this.finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
