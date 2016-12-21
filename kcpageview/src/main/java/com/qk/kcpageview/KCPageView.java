package com.qk.kcpageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by qk on 2016/12/19.
 */

public class KCPageView extends LinearLayout {
    private Context myContext;
    /**
     * 字体大小
     */
    private int mTextSize;
    /**
     * 左边字体
     */
    private String mLeftText;
    private TextView tvLeft;
    /**
     * 右边字体
     */
    private String mRightText;
    private TextView tvRight;
    /**
     * 选中的字体颜色
     */
    private int mSelectedTextColor;
    /**
     * 未选中的字体颜色
     */
    private int mTextColor;
    /**
     * 选中的背景
     */
    private Drawable mSelectedLeftDrawable;
    private Drawable mSelectedRightDrawable;
    /**
     * 未选中的背景
     */
    private Drawable mLeftDrawable;
    private Drawable mRightDrawable;
    /**
     * 默认选中
     */
    private boolean mDefaultChoose;
    /**
     * 内边距
     */
    private int mPadding;
    /**
     * 左边点击事件
     */
    private OnLeftClickListener mLeftClickListener;
    /**
     * 右边点击事件
     */
    private OnRightClickListener mRightClickListener;
    /**
     * 关联viewPager
     */
    private ViewPager mViewPager;

    public void setUpViewPager(ViewPager viewPager){
        this.mViewPager = viewPager;
        if(mViewPager != null){
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    Log.i("KK","onPageSelected:" + position);
                    if(position == 0){
                        tvLeft.setBackground(mSelectedLeftDrawable);
                        tvLeft.setTextColor(mSelectedTextColor);
                        tvRight.setBackground(mRightDrawable);
                        tvRight.setTextColor(mTextColor);
                    }else{
                        tvLeft.setBackground(mLeftDrawable);
                        tvLeft.setTextColor(mTextColor);
                        tvRight.setBackground(mSelectedRightDrawable);
                        tvRight.setTextColor(mSelectedTextColor);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    public void setLeftClickListener(OnLeftClickListener mLeftClickListener) {
        this.mLeftClickListener = mLeftClickListener;
    }

    public void setRightClickListener(OnRightClickListener mRightClickListener) {
        this.mRightClickListener = mRightClickListener;
    }

    public KCPageView(Context context) {
        this(context, null);
    }

    public KCPageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KCPageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.KCPageView,defStyleAttr, 0);
        mTextSize = a.getDimensionPixelSize(R.styleable.KCPageView_kTextSize, 14);
        mLeftText = a.getString(R.styleable.KCPageView_kLeftText);
        mRightText = a.getString(R.styleable.KCPageView_kRightText);
        mSelectedTextColor = a.getColor(R.styleable.KCPageView_kSelectTextColor, Color.BLUE);
        mTextColor = a.getColor(R.styleable.KCPageView_kTextColor, Color.WHITE);
        mSelectedLeftDrawable = a.getDrawable(R.styleable.KCPageView_kSelectLeftDrawable);
        mSelectedRightDrawable = a.getDrawable(R.styleable.KCPageView_kSelectRightDrawable);
        mRightDrawable = a.getDrawable(R.styleable.KCPageView_kRightDrawable);
        mLeftDrawable = a.getDrawable(R.styleable.KCPageView_kLeftDrawable);
        mDefaultChoose = a.getBoolean(R.styleable.KCPageView_kDefaultChoose, false);
        mPadding = a.getDimensionPixelSize(R.styleable.KCPageView_kPadding, 10);
        myContext = context;
        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        if(mSelectedLeftDrawable == null){
            mSelectedLeftDrawable = getResources().getDrawable(R.drawable.radius_left_friend_title);
        }

        if(mLeftDrawable == null){
            mLeftDrawable = getResources().getDrawable(R.drawable.radius_left_friend_titlefalse);
        }

        if(mRightDrawable == null){
            mRightDrawable = getResources().getDrawable(R.drawable.radius_right_friend_titlefalse);
        }

        if(mSelectedRightDrawable == null){
            mSelectedRightDrawable = getResources().getDrawable(R.drawable.radius_right_friend_title);
        }

        this.setOrientation(HORIZONTAL);
        tvLeft = new TextView(myContext);
        tvRight = new TextView(myContext);
        LayoutParams layoutParams = new LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1;
        tvLeft.setLayoutParams(layoutParams);
        tvRight.setLayoutParams(layoutParams);
        this.addView(tvLeft);
        this.addView(tvRight);
        tvLeft.setText(mLeftText);
        tvLeft.setGravity(Gravity.CENTER);
        tvRight.setText(mRightText);
        tvRight.setGravity(Gravity.CENTER);
        tvLeft.setTextSize(mTextSize);
        tvRight.setTextSize(mTextSize);
        Log.i("KK","textSize = " + mTextSize + "padding = " + mPadding);
        if(!mDefaultChoose){//为false表示选中第一个
            tvLeft.setBackground(mSelectedLeftDrawable);
            tvRight.setBackground(mRightDrawable);
            tvLeft.setTextColor(mSelectedTextColor);
            tvRight.setTextColor(mTextColor);
        }else{
            tvLeft.setBackground(mLeftDrawable);
            tvRight.setBackground(mSelectedRightDrawable);
            tvLeft.setTextColor(mTextColor);
            tvRight.setTextColor(mSelectedTextColor);
        }

        tvLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tvLeft.setBackground(mSelectedLeftDrawable);
                tvLeft.setTextColor(mSelectedTextColor);
                tvRight.setBackground(mRightDrawable);
                tvRight.setTextColor(mTextColor);
                if(mLeftClickListener != null){
                    mLeftClickListener.onClick(v);
                }

                if(mViewPager != null){
                    mViewPager.setCurrentItem(0);
                }
            }
        });

        tvRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tvLeft.setBackground(mLeftDrawable);
                tvLeft.setTextColor(mTextColor);
                tvRight.setBackground(mSelectedRightDrawable);
                tvRight.setTextColor(mSelectedTextColor);
                if(mRightClickListener != null){
                    mRightClickListener.onClick(v);
                }

                if(mViewPager != null){
                    mViewPager.setCurrentItem(1);
                }
            }
        });

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

    }

    public interface OnLeftClickListener{
        void onClick(View view);
    }

    public interface OnRightClickListener{
        void onClick(View view);
    }
}
