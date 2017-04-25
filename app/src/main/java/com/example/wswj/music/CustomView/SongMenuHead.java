package com.example.wswj.music.CustomView;

import android.content.Context;
import android.support.percent.PercentRelativeLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wswj.music.R;
import com.example.wswj.music.Util.MyApplication;

import java.util.zip.Inflater;

/**
 * Created by Administrator on 2017/4/24.
 */

public class SongMenuHead extends FrameLayout {

    private SongMenuImage songMenuImage;

    private TextView textView;

    public SongMenuHead(Context context) {
        super(context);
        initViews();
    }

    public SongMenuHead(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews() {
        View view = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.song_menu_content_head, this);
        songMenuImage = (SongMenuImage) view.findViewById(R.id.image);
        textView = (TextView) view.findViewById(R.id.title);
    }

    public TextView getTextView() {
        return textView;
    }

    public SongMenuImage getSongMenuImage() {
        return songMenuImage;
    }
}
