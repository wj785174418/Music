package com.example.wswj.music.Activity;

import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.example.wswj.music.Adapter.MainPagerAdapter;
import com.example.wswj.music.R;
import com.example.wswj.music.Util.DensityUtil;

public class MainActivity extends BaseActivity {

    private DrawerLayout drawerLayout;
    private ViewPager mainViewPager;
    private TabLayout toolbarTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        //设置toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= 21) {
            int statusBarHeight = DensityUtil.getStatusBarHeight();
            if (statusBarHeight >= 0) {
                toolbar.setPadding(0, statusBarHeight, 0, 0);
            }
        }

        setSupportActionBar(toolbar);

        //设置toolbar相关属性
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        //mainViewPager相关设置
        mainViewPager = (ViewPager) findViewById(R.id.main_viewpager);
        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mainViewPager.setAdapter(mainPagerAdapter);
        mainViewPager.setCurrentItem(1);

        //关联mainViewPager和toolbarTab
        toolbarTab = (TabLayout) findViewById(R.id.toolbar_tab);
        toolbarTab.setupWithViewPager(mainViewPager);
        toolbarTab.setSelectedTabIndicatorHeight(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
