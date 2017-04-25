package com.example.wswj.music.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wswj.music.R;
import com.example.wswj.music.Util.MyApplication;

/**
 * Created by Administrator on 2017/3/29.
 */

public class SongMenuImage extends FrameLayout {

    private ImageView imageView;
    private TextView listenNum;
    private ImageView icon;

    public SongMenuImage(Context context) {
        super(context);
        initViews();
    }

    public SongMenuImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public SongMenuImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        View view = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.song_menu_image, this);
        icon = (ImageView) view.findViewById(R.id.icon);
        imageView = (ImageView) view.findViewById(R.id.image_view);
        listenNum = (TextView) view.findViewById(R.id.listen_num);
        setForeground(MyApplication.getContext().getResources().getDrawable(R.drawable.touch_fg));
    }

    public ImageView getImageView() {
        return imageView;
    }

    public TextView getListenNum() {
        icon.setVisibility(VISIBLE);
        return listenNum;
    }
}
