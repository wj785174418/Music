package com.example.wswj.music.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.wswj.music.R;
import com.example.wswj.music.Util.MyApplication;

/**
 * Created by Administrator on 2017/3/29.
 */

public class LongSongMenu extends FrameLayout {

    private SongMenuImage songMenuImage;

    private TextView title;

    private TextView desc;

    public LongSongMenu(Context context) {
        super(context);
        initViews();
    }

    public LongSongMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public LongSongMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        View view = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.long_song_menu, this);
        songMenuImage = (SongMenuImage) view.findViewById(R.id.song_menu_image);
        title = (TextView) view.findViewById(R.id.title);
        desc = (TextView) view.findViewById(R.id.desc);
        this.setClickable(true);
    }

    public TextView getDesc() {
        return desc;
    }

    public SongMenuImage getSongMenuImage() {
        return songMenuImage;
    }

    public TextView getTitle() {
        return title;
    }
}
