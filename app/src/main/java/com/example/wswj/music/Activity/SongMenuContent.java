package com.example.wswj.music.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.wswj.music.Adapter.SongMenuContentAdapter;
import com.example.wswj.music.Model.Song;
import com.example.wswj.music.Model.SongMenu;
import com.example.wswj.music.R;
import com.example.wswj.music.Util.DensityUtil;
import com.example.wswj.music.Util.HttpUtil;
import com.example.wswj.music.Util.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class SongMenuContent extends BaseActivity {
    //handler msg
    private static final int SONG_MENU_GOT = 100;


    //轮播图传入歌单
    public static final int TYPE_ROLL_BANNER = 1;
    //推荐歌单传入
    public static final int TYPE_RECOMMEND = 2;
    //热门歌单传入
    public static final int TYPE_HOT = 3;
    //分类传入
    public static final int TYPE_CATEGORY = 4;

    private Toolbar toolbar;

    private RecyclerView recyclerView;

    private SongMenuContentAdapter adapter;

    private List<Song> songList = new ArrayList<>();

    private SongMenu mSongMenu;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SONG_MENU_GOT:
                    setToolBarBg(mSongMenu.getPicUrl());
                    adapter.setSongMenu(mSongMenu);
                    break;
                default:
                    break;
            }
            return true;
        }
    });

    public static void actionStart(Context context, SongMenu songMenu, int songMenuType) {
        Intent intent = new Intent(context, SongMenuContent.class);
        intent.putExtra("songMenu", songMenu);
        intent.putExtra("songMenuType", songMenuType);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_menu_content);

        int statusBarHeight = DensityUtil.getStatusBarHeight();

        //初始化toolBar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= 21) {
            if (statusBarHeight >= 0) {
                toolbar.setPadding(0, statusBarHeight, 0, 0);
            }
            toolbar.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    if (bottom != oldBottom) {
                        adapter.setHeadPaddingTop(bottom - top);
                    }
                }
            });
        }

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setTitle("歌单");
        }

        //初始化recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        int padding = toolbar.getLayoutParams().height + statusBarHeight;
        adapter = new SongMenuContentAdapter(songList);

        recyclerView.setAdapter(adapter);

        //解析intent,获取歌单详细信息
        Intent intent = getIntent();
        int type = intent.getIntExtra("songMenuType", 0);
        if (type == TYPE_RECOMMEND || type == TYPE_HOT) {
            mSongMenu = (SongMenu) intent.getSerializableExtra("songMenu");
            handler.sendEmptyMessage(SONG_MENU_GOT);
        } else if (type == TYPE_ROLL_BANNER) {

        } else if (type == TYPE_CATEGORY) {

        }
    }

    private void setToolBarBg(String imageUrl) {
        final SimpleTarget<GlideDrawable> blurDrawable = new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                resource.setAlpha(0);
                if (Build.VERSION.SDK_INT >= 16) {
                    toolbar.setBackground(resource);
                } else {
                    toolbar.setBackgroundDrawable(resource);
                }
            }
        };

        Glide.with(this).load(imageUrl)
                .bitmapTransform(new BlurTransformation(this, 16, 50))
                .into(blurDrawable);
    }
}
