package com.sna.xunwang.startnewandroid.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.moxun.tagcloudlib.view.TagCloudView;
import com.sna.xunwang.startnewandroid.R;
import com.sna.xunwang.startnewandroid.adapter.TextTagsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CloudTagActivity extends Activity {
    @BindView(R.id.tag_cloud)
    TagCloudView tagCloudView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_cloud_tag);
        ButterKnife.bind(this);

        TextTagsAdapter tagsAdapter = new TextTagsAdapter(new String[13]);
        tagCloudView.setAdapter(tagsAdapter);
    }

}
