package com.example.wswj.music.CustomView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.example.wswj.music.R;
import com.example.wswj.music.Util.DensityUtil;
import com.example.wswj.music.Util.LogUtil;

/**
 * Created by Administrator on 2017/3/24.
 */


public class RollBanner extends FrameLayout {

    //创建page接口
    public interface RollBannerDataSource {

        int getCount();

        View onCreateView(int position);
    }

    //默认小圆点选中颜色
    private static final int POINT_SELECTED_COLOR = Color.rgb(0,122,255);

    //handler Msg What
    private static final int NEXT_PAGE = 1;

    private static final int POINT_NUM = 20;

    //默认nextPage时间
    private static final int NEXT_PAGE_INTERVAL = 4000;

    private Context mContext;

    private ViewPager mViewPager;
    private LinearLayout mPointLayout;

    private PagerAdapter mAdapter;

    private ViewPager.OnPageChangeListener pageChangeListener;

    //滚动视图数量
    private int viewCount;

    //小圆点选中颜色
    private int pointSelectedColor;


    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case NEXT_PAGE:
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);

                    break;
                default:
                    break;
            }
            return true;
        }
    });

    //数据源
    private RollBannerDataSource dataSource;


    public RollBanner(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public RollBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public void setPointSelectedColor(int pointSelectedColor) {
        this.pointSelectedColor = pointSelectedColor;
    }

    public void setDataSource(RollBannerDataSource dataSource) {
        if (dataSource != null) {
            this.dataSource = dataSource;
            setViewCount();
            initAdapter();
            changeVisibilityOfPoint();
        }
    }

    public RollBannerDataSource getDataSource() {
        return dataSource;
    }

    private void setViewCount() {
        int itemNum = dataSource.getCount();
        if (itemNum <= 0) {
            viewCount = 0;
        } else {
            viewCount = itemNum;
        }
    }

    /**
     * 通知Banner需要更新
     */
    public void update() {
        if (dataSource == null) {
            return;
        }
        removeRollNextMsg();

        setViewCount();

        changeVisibilityOfPoint();

        updateSelectedPoint();

        mAdapter.notifyDataSetChanged();

        sendRollNextMsg();

        if (viewCount == 0) {
            mViewPager.removeAllViews();
        }
    }

    /**
     * 滚动至下一页
     */
    private void sendRollNextMsg() {
        if (!handler.hasMessages(NEXT_PAGE) && viewCount > 0) {
            handler.sendEmptyMessageDelayed(NEXT_PAGE, NEXT_PAGE_INTERVAL);
        }
    }

    private void removeRollNextMsg() {
        handler.removeMessages(NEXT_PAGE);
    }

    //初始化相关数据
    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.roll_banner, this);

        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);

        mPointLayout = (LinearLayout) view.findViewById(R.id.point_layout);
        pointSelectedColor = POINT_SELECTED_COLOR;

        initPoints();
        initPageChangeListener();
    }

    //初始化小圆点
    private void initPoints() {
        if (mPointLayout == null) {
            return;
        }
        for (int i = 0; i < POINT_NUM; i++) {
            View view = new View(mContext);
            view.setBackgroundResource(R.drawable.point_bg);
            view.setVisibility(View.GONE);
            int width = DensityUtil.dipTopx(5);
            int margin = DensityUtil.dipTopx(4);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
            params.setMargins(margin, 0, margin, 0);
            view.setLayoutParams(params);
            mPointLayout.addView(view);
        }
    }

    //更新选中的小圆点
    private void updateSelectedPoint() {
        if (viewCount == 0) {
            return;
        }

        int index = mViewPager.getCurrentItem() % viewCount;
        for (int i = 0; i < viewCount; i++) {
            if (i == index) {
                mPointLayout.getChildAt(i).getBackground().setColorFilter(pointSelectedColor, PorterDuff.Mode.DARKEN);
            } else {
                mPointLayout.getChildAt(i).getBackground().setColorFilter(Color.argb(0,0,0,0), PorterDuff.Mode.DARKEN);
            }
        }
    }

    //改变小圆点可见性
    private void changeVisibilityOfPoint() {
        for (int i = 0; i < POINT_NUM; i++) {
            if (i < viewCount) {
                mPointLayout.getChildAt(i).setVisibility(View.VISIBLE);
            } else {
                mPointLayout.getChildAt(i).setVisibility(View.GONE);
            }
        }
    }

    //viewPager设置adapter
    private void initAdapter() {

        mAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                int index = position % viewCount;
                View view = dataSource.onCreateView(index);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        };

        mViewPager.setAdapter(mAdapter);

        int initPosition = 100000 - 100000 % viewCount;

        mViewPager.setCurrentItem(initPosition, false);

    }

    private void initPageChangeListener() {

        pageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                sendRollNextMsg();
                updateSelectedPoint();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    removeRollNextMsg();
                } else if (state == ViewPager.SCROLL_STATE_IDLE) {
                    sendRollNextMsg();
                }
            }
        };
        mViewPager.addOnPageChangeListener(pageChangeListener);
    }



    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        sendRollNextMsg();
        LogUtil.d("test", "onAttachedToWindow");
    }

    @Override
    protected void onDetachedFromWindow() {
        removeRollNextMsg();
        LogUtil.d("test", "onDetachedFromWindow");
        super.onDetachedFromWindow();
    }
}
