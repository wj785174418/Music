package com.example.wswj.music.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wswj.music.R;

/**
 * Created by Administrator on 2017/3/23.
 */

public class CategorySongMenu extends Fragment {

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (recyclerView == null) {
            recyclerView = new RecyclerView(getContext());
            recyclerView.setBackgroundColor(Color.YELLOW);
        }

        return recyclerView;
    }
}
