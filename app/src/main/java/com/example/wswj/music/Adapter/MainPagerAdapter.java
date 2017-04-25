package com.example.wswj.music.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.wswj.music.Fragment.Dynamic;
import com.example.wswj.music.Fragment.Mine;
import com.example.wswj.music.Fragment.SongMenuFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/22.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    private List<String> fragmentList = new ArrayList<>();

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentList.add("我的");
        fragmentList.add("歌单");
        fragmentList.add("动态");
    }

    @Override
    public Fragment getItem(int position) {
        Fragment ft = null;
        switch (position) {
            case 0:
                ft = new Mine();
                break;
            case 1:
                ft = new SongMenuFragment();
                break;
            case 2:
                ft = new Dynamic();
                break;
            default:
                break;
        }
        return ft;
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentList.get(position);
    }
}
