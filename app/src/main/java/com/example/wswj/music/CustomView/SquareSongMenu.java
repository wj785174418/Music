package com.example.wswj.music.CustomView;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wswj.music.R;
import com.example.wswj.music.Util.MyApplication;

/**
 * Created by Administrator on 2017/3/28.
 */

public class SquareSongMenu extends FrameLayout {

    private SongMenuImage songMenuImage;
    private TextView desc;

    public SquareSongMenu(Context context) {
        super(context);
        initViews();
    }

    public SquareSongMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public SquareSongMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        View view = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.square_song_menu, this);
        songMenuImage = (SongMenuImage) view.findViewById(R.id.song_menu_image);
        desc = (TextView) view.findViewById(R.id.desc);
        this.setClickable(true);
    }

    public SongMenuImage getSongMenuImage() {
        return songMenuImage;
    }

    public TextView getDesc() {
        return desc;
    }
}
