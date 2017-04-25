package com.example.wswj.music.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wswj.music.Adapter.HotSongMenuAdapter;
import com.example.wswj.music.Model.RollPicture;
import com.example.wswj.music.Model.SongMenu;
import com.example.wswj.music.Util.DensityUtil;
import com.example.wswj.music.Util.HttpUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/23.
 */

public class HotSongMenu extends Fragment {


    private RecyclerView recyclerView;

    private HotSongMenuAdapter adapter;

    //轮播图实例List
    private List<RollPicture> rollPicList = new ArrayList<>();
    //推荐歌单List
    private List<SongMenu> songMenuRecList = new ArrayList<>();
    //热门歌单List
    private List<SongMenu> songMenuHotList = new ArrayList<>();
    //当前热门歌单数量
    private int hotSongMenuNum;

    private boolean isLoadOver = false;

    private boolean isLoading = true;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case HttpUtil.UPDATE_ROLLBANNER:
                    adapter.updateRollBanner();
                    break;
                case HttpUtil.UPDATE:
//                    LogUtil.d("test","" + songMenuRecList.size());
                    adapter.notifyDataSetChanged();
                    hotSongMenuNum = songMenuHotList.size();
                    isLoading = false;
                    break;
                case HttpUtil.INSERT:
                    insertHotSongMenu();
                    adapter.loadingOver();
                    isLoading = false;
                    break;
                case HttpUtil.LOAD_OVER:
                    insertHotSongMenu();
                    isLoadOver = true;
                    adapter.loadOver();
                    break;
                default:
                    break;
            }
            return true;
        }
    });

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (recyclerView == null) {
            recyclerView = new RecyclerView(getContext());
            final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new HotSongMenuAdapter(rollPicList, songMenuRecList, songMenuHotList);
            recyclerView.setAdapter(adapter);


            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (isLoadOver || isLoading) {
                        return;
                    }
                    int bottomDistance = recyclerView.computeVerticalScrollRange() -
                            (recyclerView.computeVerticalScrollOffset() + recyclerView.computeVerticalScrollExtent());
                    if (bottomDistance < DensityUtil.dipTopx(60)) {
                        adapter.loadingMore();
                        isLoading = true;
                        HttpUtil.hotSongMenu(songMenuHotList, songMenuHotList.size() + 10, handler, HttpUtil.INSERT);
                    }
                }
            });

            HttpUtil.hotPicture(rollPicList, handler);
            HttpUtil.recommendSongMenu(songMenuRecList, 9, handler, HttpUtil.UPDATE);
            HttpUtil.hotSongMenu(songMenuHotList, 7, handler, HttpUtil.UPDATE);
        }
        return recyclerView;
    }

    private void insertHotSongMenu() {
        if (hotSongMenuNum < songMenuHotList.size()) {
            int startPosition = hotSongMenuNum + 12;
            int num = songMenuHotList.size() - hotSongMenuNum;
            adapter.notifyItemRangeInserted(startPosition, num);
            hotSongMenuNum = songMenuHotList.size();
        }
    }

}
