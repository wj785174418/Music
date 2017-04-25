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
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.BlurTransformation;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SongMenuContent extends BaseActivity {
    //handler msg
    public static final int SONG_MENU_IMAGE_GOT = 1000;

    private Toolbar toolbar;

    private RecyclerView recyclerView;

    private SongMenuContentAdapter adapter;

    private List<Song> songList = new ArrayList<>();

    private SongMenu mSongMenu;

    private int toolBarHeight;

    private int scrollY;

    private int toolBarBgAlpha;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SONG_MENU_IMAGE_GOT:
                    setToolBarBg(mSongMenu.getPicUrl());
                    adapter.setSongMenu(mSongMenu);
                    break;
                case HttpUtil.UPDATE:
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
            return true;
        }
    });

    public static void actionStart(Context context, SongMenu songMenu) {
        Intent intent = new Intent(context, SongMenuContent.class);
        intent.putExtra("songMenu", songMenu);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_menu_content);

        scrollY = 0;

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
                        toolBarHeight = bottom - top;
                        adapter.setHeadPaddingTop(toolBarHeight);
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

        recyclerViewAddListener();

        //解析intent,获取歌单详细信息
        Intent intent = getIntent();
        mSongMenu = (SongMenu) intent.getSerializableExtra("songMenu");
        HttpUtil.songList(mSongMenu, songList, handler);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    private void setToolBarBg(String imageUrl) {
        final SimpleTarget<GlideDrawable> blurDrawable = new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                toolbar.setBackgroundDrawable(resource);
                toolbar.getBackground().mutate().setAlpha(0);
                toolBarBgAlpha = 0;
            }
        };

        Glide.with(this).load(imageUrl)
                .bitmapTransform(new BlurTransformation(this, 16, 50))
                .into(blurDrawable);
    }

    private void recyclerViewAddListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollY += dy;
                setToolBarBgAlpha(scrollY);
            }
        });
    }

    private void setToolBarBgAlpha(float offset) {
        int alpha = 255;
        float totalDistance = adapter.getHeadHeight() - toolBarHeight;
        if (offset < totalDistance) {
            alpha = (int) (offset / totalDistance * 255);
        }

        Drawable toolBarBg = toolbar.getBackground();
        if (toolBarBg != null && toolBarBgAlpha != alpha) {
            toolBarBg.mutate().setAlpha(alpha);
            toolBarBgAlpha = alpha;
        }
    }
}
