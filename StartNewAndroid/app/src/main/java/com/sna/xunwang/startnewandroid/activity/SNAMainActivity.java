package com.sna.xunwang.startnewandroid.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.sna.xunwang.startnewandroid.R;
import com.sna.xunwang.startnewandroid.fragment.BiezhiFragment;
import com.sna.xunwang.startnewandroid.fragment.OtherFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.graphics.Color.parseColor;

public class SNAMainActivity extends BaseActivity {
    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private List<Fragment> fragments = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_snamain;
    }

    private void initAHBottomNavigation() {
        // Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Label One", R.mipmap.tab_home,
                parseColor("#455C65"));
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Label Two", R.mipmap.tab_second, Color
                .parseColor("#00886A"));
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Label Three", R.mipmap.tab_three,
                Color.parseColor("#8B6B62"));
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("Label Four", R.mipmap.tab_four,
                Color.parseColor("#008800"));

        // Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);

        // Set background color
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));

        // Change colors
        bottomNavigation.setAccentColor(Color.parseColor("#F63D2B"));
        bottomNavigation.setInactiveColor(Color.parseColor("#747474"));

        // Use colored navigation with circle reveal effect
        bottomNavigation.setColored(true);

        // Set listener
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {
                showFragment(fragments.get(position % 2));
//                ToastUtil.showToast(getApplicationContext(), "position --> " + position);
            }
        });
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        fragments.add(new BiezhiFragment());
        fragments.add(new OtherFragment());

        showFragment(fragments.get(0));
        initAHBottomNavigation();
    }

    @Override
    public void initToolBar() {
        toolbar.setTitle("呵呵呵呵");
        toolbar.setSubtitle("哈哈哈哈");
    }

    @Override
    public void initData() {

    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragment).commitAllowingStateLoss();
    }
}
