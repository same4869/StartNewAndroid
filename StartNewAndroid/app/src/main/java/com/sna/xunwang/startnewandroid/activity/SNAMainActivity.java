package com.sna.xunwang.startnewandroid.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.jaeger.library.StatusBarUtil;
import com.sna.xunwang.startnewandroid.R;
import com.sna.xunwang.startnewandroid.fragment.BiezhiFragment;
import com.sna.xunwang.startnewandroid.fragment.OtherFragment;
import com.sna.xunwang.startnewandroid.fragment.UserFragment;

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
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("好物推荐", R.mipmap.tab_home,
                parseColor("#33bbff"));
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("暂时待定", R.mipmap.tab_second,
                parseColor("#6e92C6"));
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("个人中心", R.mipmap.tab_three,
                parseColor("#6e92C6"));

        // Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);

        // Set background color
        bottomNavigation.setDefaultBackgroundColor(parseColor("#FEFEFE"));

        // Change colors
        bottomNavigation.setAccentColor(parseColor("#F63D2B"));
        bottomNavigation.setInactiveColor(parseColor("#747474"));

        // Use colored navigation with circle reveal effect
        bottomNavigation.setColored(true);

        // Set listener
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {
                showFragment(fragments.get(position % 3));
//                ToastUtil.showToast(getApplicationContext(), "position --> " + position);
            }
        });
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        fragments.add(new BiezhiFragment());
        fragments.add(new OtherFragment());
        fragments.add(new UserFragment());

        showFragment(fragments.get(0));
        initAHBottomNavigation();
    }

    @Override
    public void initToolBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.bar_theme));
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
