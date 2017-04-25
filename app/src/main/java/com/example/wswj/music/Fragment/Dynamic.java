package com.example.wswj.music.Fragment;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.wswj.music.CustomView.RollBanner;
import com.example.wswj.music.Model.RollPicture;
import com.example.wswj.music.R;
import com.example.wswj.music.Util.DensityUtil;
import com.example.wswj.music.Util.HttpUtil;
import com.example.wswj.music.Util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/22.
 */

public class Dynamic extends Fragment {

    private View view;
    private Button request;

    public static final int UPDATE_ROLLPIC = 10;

    private List<RollPicture> rollPicList = new ArrayList<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_ROLLPIC:
                    LogUtil.d("test", rollPicList.toString());
                    break;
                default:
                    break;
            }
        }
    };



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.dynamic_layout, container, false);

            request = (Button) view.findViewById(R.id.button);

            request.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtil.d("test", Thread.currentThread().toString());
                    rollPicList.clear();
                    HttpUtil.hotPicture(rollPicList, handler);
                }
            });

            Button reGe = (Button) view.findViewById(R.id.re_ge);
            reGe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            Button hotGe = (Button) view.findViewById(R.id.hot_ge);
            hotGe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("main", "fragment Dynamic Stop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("main", "fragment Dynamic DestroyView");
    }

    @Override
    public void onDestroy() {
        Log.d("main", "fragment Dynamic Destroy");
        super.onDestroy();
    }
}
