package com.example.wswj.music.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wswj.music.Adapter.SongMenuPagerAdapter;
import com.example.wswj.music.R;
import com.example.wswj.music.Util.LogUtil;

/**
 * Created by Administrator on 2017/3/22.
 */

public class SongMenuFragment extends Fragment {

    private View view;
    private TabLayout songMenuTab;
    private ViewPager songMenuViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.song_menu, container, false);
            //TabLayout
            songMenuTab = (TabLayout) view.findViewById(R.id.songMenu_tab);
            songMenuTab.setTabTextColors(R.color.colorAccent, Color.rgb(255, 64, 129));

            songMenuViewPager = (ViewPager) view.findViewById(R.id.songMenu_viewPager);
            SongMenuPagerAdapter songMenuPagerAdapter = new SongMenuPagerAdapter(getFragmentManager());
            songMenuViewPager.setAdapter(songMenuPagerAdapter);

            songMenuTab.setupWithViewPager(songMenuViewPager);
        }


        return view;
    }
}
