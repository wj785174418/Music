package com.example.wswj.music.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wswj.music.Activity.SongMenuContent;
import com.example.wswj.music.CustomView.LongSongMenu;
import com.example.wswj.music.CustomView.RollBanner;
import com.example.wswj.music.CustomView.SquareSongMenu;
import com.example.wswj.music.Model.RollPicture;
import com.example.wswj.music.Model.SongMenu;
import com.example.wswj.music.R;
import com.example.wswj.music.Util.DensityUtil;
import com.example.wswj.music.Util.LogUtil;
import com.example.wswj.music.Util.MyApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/23.
 */

public class HotSongMenuAdapter extends SectionedRecyclerViewAdapter<ViewHolder, ViewHolder, RecyclerView.ViewHolder> {

    /**
    * HeaderType
    * */
    private static final int ROLL_BANNER = 1;


    /**
    * ItemType
    * */
    //推荐歌单item
    private static final int RECOMMEND_SONGMENU = 2;
    //热门歌单item
    private static final int HOT_SONGMENU = 3;

    //轮播图 W=H*2.5
    private static final float ROLL_BANNER_SCALE = 2.5f;

    //轮播图数据
    private List<RollPicture> mRollPicList;

    //推荐歌单数据
    private List<SongMenu> mSongMenuRecList;

    //热门歌单数据
    private List<SongMenu> mSongMenuHotList;

    //轮播图ViewHolder
    private ViewHolder rollBannerHolder;


    public HotSongMenuAdapter(List<RollPicture> rollPicList, List<SongMenu> songMenuRecList, List<SongMenu> songMenuHotList) {
        super();
        this.mRollPicList = rollPicList;
        this.mSongMenuRecList = songMenuRecList;
        this.mSongMenuHotList = songMenuHotList;
    }


    @Override
    protected int getSectionCount() {
        return 4;
    }

    @Override
    protected int getItemCountForSection(int section) {
        int count = 0;
        switch (section) {
            case 0:
                count = 0;
                break;
            case 1:
                count = mSongMenuRecList.size()/3;
                break;
            case 2:
                count = mSongMenuHotList.size();
                break;
            case 3:
                count = 0;
                break;
            default:
                break;
        }
        return count;
    }

    @Override
    protected boolean hasFooterInSection(int section) {
        return false;
    }

    @Override
    protected int getSectionHeaderViewType(int section) {
        int viewType = 0;
        switch (section) {
            case 0:
                viewType = ROLL_BANNER;
                break;
            case 1:
                viewType = super.getSectionHeaderViewType(section);
                break;
            case 2:
                viewType = super.getSectionHeaderViewType(section);
                break;
            case 3:
                viewType = super.getSectionHeaderViewType(section);
                break;
            default:
                break;
        }
        return viewType;
    }

    @Override
    protected int getSectionItemViewType(int section, int position) {
        switch (section) {
            case 1:
                return RECOMMEND_SONGMENU;
            case 2:
                return HOT_SONGMENU;
            default:
                break;
        }
        return super.getSectionItemViewType(section, position);
    }

    @Override
    protected ViewHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder;
        if (viewType == ROLL_BANNER) {
            RollBanner rollBanner = initRollBanner();
            rollBannerHolder = new ViewHolder(rollBanner);
            return rollBannerHolder;
        } else {
            View view = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.hot_song_menu_header, parent, false);
            holder = new ViewHolder(view);
        }
        return holder;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        if (viewType == RECOMMEND_SONGMENU) {
            View view = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.recommend_song_menu, parent, false);
            return new ViewHolder(view);
        } else if (viewType == HOT_SONGMENU) {
            LongSongMenu longSongMenu = new LongSongMenu(MyApplication.getContext());
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            longSongMenu.setLayoutParams(layoutParams);
            return new ViewHolder(longSongMenu);
        }
        return null;
    }

    @Override
    protected void onBindSectionHeaderViewHolder(ViewHolder holder, int section) {
        TextView title;
        switch (section) {
            case 1:
                title = (TextView) holder.findSubViewById(R.id.title);
                title.setText("推荐歌单");
                break;
            case 2:
                title = (TextView) holder.findSubViewById(R.id.title);
                title.setText("热门歌单");
                break;
            case 3:
                title = (TextView) holder.findSubViewById(R.id.title);
                title.setText("上拉加载");
                break;
            default:
                break;
        }
    }

    @Override
    protected void onBindSectionFooterViewHolder(RecyclerView.ViewHolder holder, int section) {

    }

    @Override
    protected void onBindItemViewHolder(ViewHolder holder, int section, int position) {
        if (section == 1) {
            int[] resources = {R.id.first, R.id.second, R.id.third};
            for (int i = 0; i < resources.length; i++) {
                //方形歌单view
                SquareSongMenu squareSongMenu = (SquareSongMenu) holder.findSubViewById(resources[i]);
                //推荐歌单model
                final SongMenu songMenu = mSongMenuRecList.get(position * 3 + i);
                squareSongMenu.getDesc().setText(songMenu.getTitle());
                String listenNum = songMenu.getListenNum();
                squareSongMenu.getSongMenuImage().getListenNum().setText(listenNum);
                Glide.with(MyApplication.getContext())
                        .load(songMenu.getPicUrl())
                        .into(squareSongMenu.getSongMenuImage().getImageView());
                //绑定点击事件
                squareSongMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SongMenuContent.actionStart(MyApplication.getContext(),
                                songMenu, SongMenuContent.TYPE_RECOMMEND);
                    }
                });
            }
        } else if (section == 2) {
            final SongMenu songMenuHot = mSongMenuHotList.get(position);

            LongSongMenu longSongMenu = (LongSongMenu) holder.itemView;
            longSongMenu.getTitle().setText(songMenuHot.getTitle());
            longSongMenu.getDesc().setText(songMenuHot.getDesc());
            longSongMenu.getSongMenuImage().getListenNum().setText(songMenuHot.getListenNum());
            Glide.with(MyApplication.getContext())
                    .load(songMenuHot.getPicUrl())
                    .into(longSongMenu.getSongMenuImage().getImageView());

            longSongMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SongMenuContent.actionStart(MyApplication.getContext(),
                            songMenuHot, SongMenuContent.TYPE_HOT);
                }
            });
        }
    }

    /**
     * @return 初始化轮播图
     */
    private RollBanner initRollBanner() {
        RollBanner rollBanner = new RollBanner(MyApplication.getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, (int) (DensityUtil.getScreenWidthPx() / ROLL_BANNER_SCALE));
        rollBanner.setLayoutParams(layoutParams);
        return rollBanner;
    }

    public void updateRollBanner() {
        RollBanner rollBanner = (RollBanner) rollBannerHolder.itemView;
        if (rollBanner.getDataSource() == null) {
            rollBanner.setDataSource(new RollBanner.RollBannerDataSource() {
                @Override
                public int getCount() {
                    return mRollPicList.size();
                }

                @Override
                public View onCreateView(int position) {
                    final RollPicture rollPic = mRollPicList.get(position);
                    FrameLayout container = new FrameLayout(MyApplication.getContext());
                    container.setForeground(MyApplication.getContext().getResources().getDrawable(R.drawable.touch_fg));
                    container.setClickable(true);

                    ImageView imageView = new ImageView(MyApplication.getContext());
                    FrameLayout.LayoutParams layoutImage = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    imageView.setLayoutParams(layoutImage);
                    container.addView(imageView);

                    Glide.with(MyApplication.getContext())
                            .load(rollPic.getPicUrl())
                            .into(imageView);

                    container.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SongMenu songMenu = new SongMenu(rollPic.getAlbumID(), null, null, null, null);
                            SongMenuContent.actionStart(MyApplication.getContext(),
                                    songMenu, SongMenuContent.TYPE_ROLL_BANNER);
                        }
                    });

                    return container;
                }
            });
        } else {
            rollBanner.update();
        }
    }

    public void loadingMore() {
        LogUtil.d("test", "正在加载");
    }

    public void loadingOver() {
        LogUtil.d("test", "上拉加载更多");
    }

    public void loadOver() {
        LogUtil.d("test", "没有更多数据");
    }
}
