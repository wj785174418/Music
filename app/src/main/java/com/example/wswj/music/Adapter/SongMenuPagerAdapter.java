package com.example.wswj.music.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.wswj.music.Fragment.CategorySongMenu;
import com.example.wswj.music.Fragment.HotSongMenu;
import com.example.wswj.music.Fragment.RankingList;
import com.example.wswj.music.Fragment.StarSongMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/23.
 */

public class SongMenuPagerAdapter extends FragmentPagerAdapter {

    private List<String> fragmentList = new ArrayList<>();

    public SongMenuPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentList.add("听觉盛宴");
        fragmentList.add("分类享听");
        fragmentList.add("偶像天团");
        fragmentList.add("热门排行");
    }

    @Override
    public Fragment getItem(int position) {
        Fragment ft = null;
        switch (position) {
            case 0:
                ft = new HotSongMenu();
                break;
            case 1:
                ft = new CategorySongMenu();
                break;
            case 2:
                ft = new StarSongMenu();
                break;
            case 3:
                ft = new RankingList();
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
