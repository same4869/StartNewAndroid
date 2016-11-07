package com.sna.xunwang.startnewandroid;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.graphics.Color.parseColor;

public class SNAMainActivity extends AppCompatActivity {
    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snamain);

        ButterKnife.bind(this);

// Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Label One", R.mipmap.tab_home,
                parseColor("#455C65"));
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Label Two", R.mipmap.tab_second, Color
                .parseColor("#00886A"));
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Label Three", R.mipmap.tab_three,
                Color.parseColor("#8B6B62"));

// Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);

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

            }
        });
    }

    @OnClick(R.id.test_btn) void sdfasdf(){
        Toast.makeText(getApplicationContext(), "afdsfsd", Toast.LENGTH_SHORT).show();
    }
}
