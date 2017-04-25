package com.example.wswj.music.Adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.wswj.music.CustomView.SongInfoSimple;
import com.example.wswj.music.CustomView.SongMenuHead;
import com.example.wswj.music.Model.Song;
import com.example.wswj.music.Model.SongMenu;
import com.example.wswj.music.R;
import com.example.wswj.music.Util.LogUtil;
import com.example.wswj.music.Util.MyApplication;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by Administrator on 2017/4/24.
 */

public class SongMenuContentAdapter extends RecyclerView.Adapter<ViewHolder> {

    //itemType

    //头部图片
    private static final int TYPE_IMAGE = 1;

    //歌曲
    private static final int TYPE_SONG = 2;

    private List<Song> mSongList;

    private ViewHolder headHolder;

    private int headPaddingTop;

    private SongMenu mSongMenu;


    private int headHeight;

    /*
    * head背景layerDrawable
    * */
    private LayerDrawable headBgDrawable;

    private static final int HEAD_BG_BACKGROUND = 0;

    private static final int HEAD_BG_FOREGROUND = 1;


    public SongMenuContentAdapter(List<Song> songList) {
        super();
        mSongList = songList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_IMAGE;
        } else {
            return TYPE_SONG;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_IMAGE) {
            SongMenuHead songMenuHead = new SongMenuHead(MyApplication.getContext());
            songMenuHead.setPadding(0, headPaddingTop, 0, 0);
            headHolder = new ViewHolder(songMenuHead);
            songMenuHead.setBackgroundResource(R.drawable.song_menu_head_bg);

            songMenuHead.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    int height = bottom - top;
                    if (height != headHeight) {
                        headHeight = height;
                    }
                }
            });

            if (mSongMenu != null) {
                setHead();
            }
            return headHolder;
        } else if (viewType == TYPE_SONG) {
            SongInfoSimple songInfoSimple = new SongInfoSimple(MyApplication.getContext());

            songInfoSimple.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            return new ViewHolder(songInfoSimple);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position > 0) {
            Song song = mSongList.get(position - 1);

            SongInfoSimple songInfoSimple = (SongInfoSimple) holder.itemView;
            songInfoSimple.getNum().setText("" + position);
            songInfoSimple.getTitle().setText(song.getTitle());

            StringBuilder songInfo = new StringBuilder(song.getAuthor());
            if (song.getAlbumTitle().length() != 0) {
                songInfo.append(" - " + song.getAlbumTitle());
            }
            songInfoSimple.getInfo().setText(songInfo.toString());
        }
    }

    @Override
    public int getItemCount() {
        return mSongList.size() + 1;
    }

    public void setHeadPaddingTop(int headPaddingTop) {
        this.headPaddingTop = headPaddingTop;
        if (headHolder != null) {
            headHolder.itemView.setPadding(0, headPaddingTop, 0, 0);
        }
    }

    public void setSongMenu(SongMenu mSongMenu) {
        this.mSongMenu = mSongMenu;
        setHead();
    }

    public void setHead() {
        if (headHolder == null || mSongMenu == null) {
            return;
        }

        String imageUrl = mSongMenu.getPicUrl();

        String title = mSongMenu.getTitle();

        final SongMenuHead songMenuHead = (SongMenuHead) headHolder.itemView;

        //head title
        songMenuHead.getTextView().setText(title);

        //听众人数
        if (mSongMenu.getListenNum() != null) {
            songMenuHead.getSongMenuImage().getListenNum().setText(mSongMenu.getListenNum());
        }


        //歌单图片
        Glide.with(MyApplication.getContext())
                .load(imageUrl)
                .into(songMenuHead.getSongMenuImage().getImageView());

        //head背景
        SimpleTarget<GlideDrawable> blurDrawable = new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                //添加灰色遮罩,避免白色背景字体不清晰
                resource.setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
                resource.setAlpha(0);

                initHeadBgDrawable(headHolder.itemView.getBackground(), resource);

                songMenuHead.setBackgroundDrawable(headBgDrawable);

                initObjectAnimator();
            }
        };

        Glide.with(MyApplication.getContext()).load(imageUrl)
                .bitmapTransform(new BlurTransformation(MyApplication.getContext(), 16, 50))
                .into(blurDrawable);
    }

    //初始化LayerDrawable对象
    private void initHeadBgDrawable(Drawable bg, Drawable fg) {
        Drawable[] drawables = new Drawable[2];

        /*初始化时先将前景与背景颜色设为一致*/
        drawables[HEAD_BG_BACKGROUND] = bg;
        drawables[HEAD_BG_FOREGROUND] = fg;

        headBgDrawable = new LayerDrawable(drawables);
    }

    private void initObjectAnimator() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt
                (headBgDrawable.getDrawable(HEAD_BG_FOREGROUND), "alpha", 0, 255);

        objectAnimator.setDuration(1000);
//        objectAnimator.setInterpolator(new AccelerateInterpolator());
//        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                int alpha = (int) ((float) animation.getAnimatedValue() * 255);
//                /*动态设置Drawable的透明度*/
//                headHolder.itemView.getBackground().setAlpha(alpha);
////                BackgourndAnimationRelativeLayout.this.setBackground(layerDrawable);
//            }
//        });

        objectAnimator.start();
//        objectAnimator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//            /*动画结束后，记得将原来的背景图及时更新*/
//                layerDrawable.setDrawable(INDE_BACKGROUND, layerDrawable.getDrawable(
//                        INDE_FOREGROUND));
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });
    }

    public int getHeadHeight() {
        return headHeight;
    }
}


