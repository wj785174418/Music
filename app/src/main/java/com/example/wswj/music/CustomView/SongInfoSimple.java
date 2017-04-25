package com.example.wswj.music.CustomView;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.wswj.music.R;
import com.example.wswj.music.Util.MyApplication;

import org.w3c.dom.Text;

/**
 * Created by Administrator on 2017/4/25.
 */

public class SongInfoSimple extends FrameLayout {

    private TextView num;

    private TextView title;

    private TextView info;

    private FrameLayout more;

    public SongInfoSimple(@NonNull Context context) {
        super(context);
        initViews();
    }

    public SongInfoSimple(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public SongInfoSimple(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        View view = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.song_info_simple, this);
        num = (TextView) view.findViewById(R.id.num);
        title = (TextView) view.findViewById(R.id.title);
        info = (TextView) view.findViewById(R.id.song_info);
        more = (FrameLayout) view.findViewById(R.id.more);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParams);
    }

    public TextView getNum() {
        return num;
    }

    public TextView getTitle() {
        return title;
    }

    public TextView getInfo() {
        return info;
    }

    public FrameLayout getMore() {
        return more;
    }
}
